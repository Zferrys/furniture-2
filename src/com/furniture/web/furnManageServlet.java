package com.furniture.web;

import com.furniture.entity.Furn;
import com.furniture.entity.Page;
import com.furniture.service.FurnService;
import com.furniture.service.impl.FurnServiceImpl;
import com.furniture.utils.DataUtils;
import com.furniture.utils.DealFileUtils;
import com.furniture.utils.WebUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class furnManageServlet extends Basic_Servlet {
    FurnService furnService = new FurnServiceImpl();

    protected void close(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String pageNo = req.getParameter("pageNo");
        if (id != null && !id.isEmpty()) {
            int furnId = DataUtils.parseInt(id, -1);
            if (furnId > 0) {
                // 删除前先获取图片路径，用于清理磁盘文件
                Furn furn = furnService.getFurnById(furnId);
                if (furn != null && furn.getImgPath() != null
                        && !furn.getImgPath().contains("default.jpg")) {
                    // 删除磁盘上的图片文件
                    String realPath = req.getServletContext().getRealPath(furn.getImgPath());
                    File imgFile = new File(realPath);
                    if (imgFile.exists()) {
                        imgFile.delete();
                    }
                }
                int i = furnService.closeFurnById(furnId);
                System.out.println(i > 0 ? "删除成功" : "删除失败");
            }
        }
        resp.sendRedirect(req.getContextPath() + "/manage/furnManage?action=page&pageNo=" + (pageNo != null ? pageNo : "1"));
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Furn furn = new Furn();

        if (ServletFileUpload.isMultipartContent(req)) {
            // 处理文件上传 + 表单字段
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            servletFileUpload.setHeaderEncoding("utf-8");
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        String fieldName = fileItem.getFieldName();
                        String fieldValue = fileItem.getString("utf-8");
                        if ("name".equals(fieldName)) {
                            furn.setName(fieldValue);
                        } else if ("market".equals(fieldName)) {
                            furn.setMarket(fieldValue);
                        } else if ("price".equals(fieldName)) {
                            furn.setPrice(new BigDecimal(fieldValue));
                        } else if ("sales".equals(fieldName)) {
                            furn.setSales(DataUtils.parseInt(fieldValue, 0));
                        } else if ("store".equals(fieldName)) {
                            furn.setStore(DataUtils.parseInt(fieldValue, 0));
                        }
                    } else {
                        // 处理上传的图片文件
                        DealFileUtils.DealFormFile(fileItem, req, furn);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // 如果未上传图片，使用默认图片
            if (furn.getImgPath() == null || furn.getImgPath().isEmpty()) {
                furn.setImgPath("assets/images/product-image/default.jpg");
            }

            // 输入校验：名称和价格不能为空
            if (furn.getName() == null || furn.getName().trim().isEmpty()) {
                resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().write("<script>alert('家居名称不能为空！');history.back();</script>");
                return;
            }
            if (furn.getPrice() == null || furn.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().write("<script>alert('价格必须大于0！');history.back();</script>");
                return;
            }

            furnService.addFurn(furn);
            String pageNo = req.getParameter("pageNo");
            resp.sendRedirect(req.getContextPath() + "/manage/furnManage?action=page&pageNo=" + (pageNo != null ? pageNo : "1"));
        } else {
            // 兼容非 multipart 请求（通过隐藏域传参）
            Map<String, String[]> map = req.getParameterMap();
            furn = DataUtils.copyParamToBean(map, new Furn());
            if (furn.getImgPath() == null || furn.getImgPath().isEmpty()) {
                furn.setImgPath("assets/images/product-image/default.jpg");
            }
            furnService.addFurn(furn);
            String pageNo = req.getParameter("pageNo");
            resp.sendRedirect(req.getContextPath() + "/manage/furnManage?action=page&pageNo=" + (pageNo != null ? pageNo : "1"));
        }
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        Furn furn = furnService.getFurnById(id);
        if (furn == null) {
            resp.sendRedirect(req.getContextPath() + "/manage/furnManage?action=page&pageNo=" + req.getParameter("pageNo"));
            return;
        }

        if (ServletFileUpload.isMultipartContent(req)) {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            servletFileUpload.setHeaderEncoding("utf-8");
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        if ("name".equals(fileItem.getFieldName())) {
                            furn.setName(fileItem.getString("utf-8"));
                        } else if ("market".equals(fileItem.getFieldName())) {
                            furn.setMarket(fileItem.getString("utf-8"));
                        } else if ("price".equals(fileItem.getFieldName())) {
                            furn.setPrice(new BigDecimal(fileItem.getString()));
                        } else if ("sales".equals(fileItem.getFieldName())) {
                            furn.setSales(DataUtils.parseInt(fileItem.getString(), 0));
                        } else if ("store".equals(fileItem.getFieldName())) {
                            furn.setStore(DataUtils.parseInt(fileItem.getString(), 0));
                        }
                    } else {
                        // 处理上传的图片文件
                        DealFileUtils.DealFormFile(fileItem, req, furn);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            // 兼容非 multipart 请求
            Map<String, String[]> map = req.getParameterMap();
            furn = DataUtils.copyParamToBean(map, furn);
        }

        // 输入校验
        if (furn.getName() == null || furn.getName().trim().isEmpty()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("<script>alert('家居名称不能为空！');history.back();</script>");
            return;
        }
        if (furn.getPrice() == null || furn.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("<script>alert('价格必须大于0！');history.back();</script>");
            return;
        }

        furnService.updateFurn(furn);
        req.getRequestDispatcher("/views/manage/update_ok.jsp").forward(req, resp);
    }


    protected void queryFurnById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            int furnId = DataUtils.parseInt(id, -1);
            Furn furn = furnService.getFurnById(furnId);
            req.setAttribute("furn", furn);
            req.getRequestDispatcher("/views/manage/furn_update.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/manage/furnManage?action=page&pageNo=" + req.getParameter("pageNo"));
        }
    }


    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int begin = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        Page<Furn> page = furnService.getPageItems(begin, pageSize);
        // 设置分页 URL，以便前端使用
        page.setUrl("manage/furnManage?action=page");
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(req, resp);
    }


}

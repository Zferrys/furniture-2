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
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class furnManageServlet extends Basic_Servlet {
    FurnService furnService = new FurnServiceImpl();

    protected void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Furn> furns = furnService.showFurns();
        req.setAttribute("furns", furns);
        req.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(req, resp);
    }

    protected void close(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            int furn_id = DataUtils.parseInt(id, -1);
            int i = furnService.closeFurnById(furn_id);
            System.out.println(i > 0 ? "删除成功" : "删除失败");
        }
        resp.sendRedirect(req.getContextPath() + "/manage/furnManage?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> map = req.getParameterMap();
        Furn furn = DataUtils.copyParamToBean(map, new Furn());
        furnService.addFurn(furn);
        resp.sendRedirect(req.getContextPath() + "/manage/furnManage?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    //    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Map<String, String[]> map = req.getParameterMap();
//        Furn furn = DataUtils.copyParamToBean(map, new Furn());
//        furnService.updateFurn(furn);
//        resp.sendRedirect(req.getContextPath() + "/manage/furnManage?action=page&pageNo=" + req.getParameter("pageNo") );
//    }
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        Furn furn = furnService.getFurnById(id);
        if (furn == null) {
            resp.sendRedirect(req.getContextPath() + "/manage/furnManage?action=page&pageNo=" + req.getParameter("pageNo"));
            return;
        }
        if (ServletFileUpload.isMultipartContent(req)) {

            // 处理上传文件
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            servletFileUpload.setHeaderEncoding("utf-8");
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                //System.out.println(list);
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        if ("name".equals(fileItem.getFieldName())) {//家居名
                            furn.setName(fileItem.getString("utf-8"));
                        } else if ("market".equals(fileItem.getFieldName())) {//制造商
                            furn.setMarket(fileItem.getString("utf-8"));
                        } else if ("price".equals(fileItem.getFieldName())) {//价格
                            furn.setPrice(new BigDecimal(fileItem.getString()));
                        } else if ("sales".equals(fileItem.getFieldName())) {//销量
                            furn.setSales(new Integer(fileItem.getString()));
                        } else if ("store".equals(fileItem.getFieldName())) {//库存
                            furn.setStore(new Integer(fileItem.getString()));
                        }
                    } else {

                        DealFileUtils.DealFormFile(fileItem, req, furn);
                        resp.setContentType("text/html;charset=utf-8");

                        resp.getWriter().write("文件上传成功");
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("不是上传表单文件");
        }
        furnService.updateFurn(furn);
        req.getRequestDispatcher("/views/manage/update_ok.jsp").forward(req, resp);
    }


    protected void queryFurnById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            int furn_id = DataUtils.parseInt(id, -1);
            Furn furn = furnService.getFurnById(furn_id);
            req.setAttribute("furn", furn);
//            req.setAttribute("pageNo", req.getParameter("pageNo"));
            req.getRequestDispatcher("/views/manage/furn_update.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/manage/furnManage?action=page&pageNo=" + req.getParameter("pageNo"));
        }
    }


    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int begin = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        Page<Furn> page = furnService.getPageItems(begin, pageSize);
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(req, resp);
    }


}

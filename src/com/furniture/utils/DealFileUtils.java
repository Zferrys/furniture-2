package com.furniture.utils;

import com.furniture.entity.Furn;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * @author zph
 * @version 1.0
 */
public class DealFileUtils {
    public static void DealFormFile(FileItem fileItem, HttpServletRequest req, Furn furn) {
        try {
            String name = fileItem.getName();
            System.out.println("文件名: " + name);
            if (!"".equals(name)) {

                String path = WebUtils.FURN_IMG_DIRECTORY;
                String realPath = req.getServletContext().getRealPath(path + "/" + WebUtils.YearMonthDay());
                System.out.println(realPath);
                File file = new File(realPath);
                if (!file.exists()) {
                    file.mkdirs();
                } else {
                    System.out.println("目录已存在: " + realPath);
                }
                name = UUID.randomUUID().toString() + System.currentTimeMillis() + "_" + name;
                fileItem.write(new File(realPath, name));
                fileItem.getOutputStream().close();//关闭流
                System.out.println(name);
                furn.setImgPath(WebUtils.FURN_IMG_DIRECTORY + "/" + WebUtils.YearMonthDay() + "/" + name);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

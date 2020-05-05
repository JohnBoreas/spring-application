package com.springweb.web;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author boreas
 * @create 2020-02-03 下午 11:12
 */
@RestController
@RequestMapping("/upload")
public class UploadFileController {

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            System.out.println("multipartFile"+multipartFile);
            String filePath = "/Users/soaic/"+multipartFile.getOriginalFilename();
            FileUtils.writeByteArrayToFile(new File(filePath), multipartFile.getBytes());
            return "upload success";
        } catch (Exception e) {
            e.printStackTrace();
            return "upload failure";
        }
    }

    @RequestMapping(value = "/batchFile", method = RequestMethod.POST)
    public String uploadFiles(HttpServletRequest request) {
        try {
            String content = request.getParameter("content");
            System.out.println("content="+content);
            //获取上传的文件数组
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            for (MultipartFile file:files) {
                FileUtils.writeByteArrayToFile(new File("/Users/soaic/"+file.getOriginalFilename()), file.getBytes());
            }
            return "upload success";
        } catch (Exception e) {
            e.printStackTrace();
            return "upload failure";
        }
    }
}

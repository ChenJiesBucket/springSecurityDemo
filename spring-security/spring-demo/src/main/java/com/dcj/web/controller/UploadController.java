package com.dcj.web.controller;

import com.dcj.web.dao.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/upload")
public class UploadController {
    private String localPath = "F:\\gitRepository\\springSecurityDemo\\spring-security\\spring-demo\\src\\main\\java\\com\\dcj\\securityDemo\\web";
    @PostMapping("files")
    public FileInfo uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getName();
        Long filesize = file.getSize();
        String originalName = file.getOriginalFilename();
        System.out.println(fileName+"----"+filesize+"----"+originalName);

        File locFile = new File(localPath,"123.txt");
        file.transferTo(locFile);
        return new FileInfo(locFile.getPath());
    }

    @GetMapping("/download")
    public void downLoadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //JDK1.7以后括号里自动关闭流
        try{
            InputStream is  = new FileInputStream(localPath+"123.txt");
            is.read();
            System.out.println();
            OutputStream os = response.getOutputStream();
            IOUtils.copy(is,os);
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attchement;filename=123.txt");

            os.flush();
            os.close();
        }catch (Exception e){

        }
    }
}

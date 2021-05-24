package org.pzz.modules.controller;

import org.pzz.common.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;


@RestController
@RequestMapping("/faz")
public class FileDownLoad {

    @RequestMapping("/download")
    public Result<?> uploadStart(HttpServletResponse response) {
        System.out.println(this.getClass().getResource("/").getPath());
        //获取项需要下载的文件路径
        String path = this.getClass().getResource("/").getPath() + "123.mp4";
        //通过路径，得到文件
        File file = new File(path);
        //判断文件是否存在
//        if(!file.exists()){
//            return Result.error("文件不存在！");
//        }
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            //设置Http响应头
            response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode("123.mp4", "UTF-8"));
            response.setContentType("application/octet-stream; charset=UTF-8");

            outputStream = response.getOutputStream();
            byte[] bytes = new byte[2048];
            int len;
            while ((len = fileInputStream.read(bytes))>0){
                outputStream.write(bytes,0,len);
            }
        }catch(Exception e) {
            e.printStackTrace();
            return Result.error("系统异常！");
        }finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return Result.OK();
    }

}

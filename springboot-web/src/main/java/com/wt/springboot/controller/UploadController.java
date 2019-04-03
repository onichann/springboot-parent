package com.wt.springboot.controller;


import com.wt.springboot.exception.SpringWebException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
public class UploadController {

//    @Value("#{config.test.url}")
//    @Value("#{T(java.io.File).separator}")
    @Value("${uploadDir}")
    private String filePath;


    /**
     * 上传
     * @param files
     * @param params
     * @return
     */
    @PostMapping(value="/upload")
    @ResponseBody
    public Map updateItems(@RequestParam(value = "file") MultipartFile[] files, @RequestParam(value = "map",required = false) Map params) {
        Map map=new HashMap<>();
        try {
           long  startTime=System.currentTimeMillis();
           for(MultipartFile file:files){
               String picName = UUID.randomUUID().toString();
               // 截取文件的扩展名(如.jpg)
               String oriName = file.getOriginalFilename();
               String extName = oriName.substring(oriName.lastIndexOf("."));
               // 保存文件
               File folder=new File(filePath);
               if(!folder.exists()) folder.mkdirs();
               file.transferTo(new File(folder +File.separator+ picName + extName));
           }
            long  endTime=System.currentTimeMillis();
            System.out.println("运行时间："+String.valueOf(endTime-startTime)+"ms");
            map.put("success",true);
            map.put("info","上传成功:传入的文件共"+files.length+"个,耗时:"+String.valueOf(endTime-startTime)+"ms");
        } catch (IOException e) {
            e.printStackTrace();
            map.put("success",false);
            map.put("info","上传失败.");
        }
        return map;
    }

    /**
     * 下载
     * @param request
     * @param fileName
     * @param userAgent
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request,
                                           @RequestParam("fileName") String fileName,
                                           @RequestHeader("User-Agent") String userAgent,
                                           Model model) throws IOException {
//        构建File
        File file=new File(filePath+File.separator+fileName);
        if(!file.exists()) throw new SpringWebException("文件不存在");
//        ok表示HTTP中的状态 200
        ResponseEntity.BodyBuilder bodyBuilder= ResponseEntity.ok();
//        内容长度
        bodyBuilder.contentLength(file.length());
//        application/oct-stream :二进制流数据（最常见的文件下载）
        bodyBuilder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        fileName= URLEncoder.encode(fileName,"UTF-8");
//        设置实际的响应文件名，告诉浏览器文件要用于“下载”和“保存”
        if(userAgent.indexOf("MSIE")>-1){
//            如果是IE，只需要使用UTF-8字符集进行URL编码即可
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setContentDispositionFormData("attachment", fileName);
//            bodyBuilder.headers(httpHeaders);
            bodyBuilder.header("Content-Disposition","attachment;filename="+fileName);
        }else{
//            其他浏览器，需要说明编码的字符集  filename后面有个*号，在UTF-8后面有两个单引号
            bodyBuilder.header("Content-Disposition","attachment;filename*=UTF-8''"+fileName);
        }
        return bodyBuilder.body(FileUtils.readFileToByteArray(file));

    }


    /**
     * 文件下载到磁盘里
     * @throws Exception
     */
    @RequestMapping("/download2")
    public void download2() throws Exception{
        Resource resource= new UrlResource("https://img.zcool.cn/community/01f950571b8b156ac7253812e10163.jpg");
        if(resource.exists()){
            String filename = resource.getFilename();
            System.out.println(filename);
            System.out.println(resource.isOpen());
//            File file=resource.getFile();
            InputStream inputStream = resource.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream("e:\\" + filename);
            int length = 0;
            byte[] bytes = new byte[1024];
            while ((length = inputStream.read(bytes)) != -1) {
                //System.out.println(bytesum);
                fileOutputStream.write(bytes, 0, length);
            }
            inputStream.close();
            fileOutputStream.close();
        }
    }


    /**
     * 显示/下载
     * @param response
     */
    @RequestMapping(value = "/displayOne")
    public void displayFile(HttpServletResponse response){
        File file = new File("C:" + File.separator + "1106-YW1401-2008-0001-001.pdf");
        response.reset();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=1106-YW1401-2008-0001-001.pdf");
//        response.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);//下载用
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        ServletOutputStream outputStream=null;
        FileInputStream inputStream=null;
        try {
            outputStream = response.getOutputStream();
            inputStream = new FileInputStream(file);
            IOUtils.write(IOUtils.toByteArray(inputStream),outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }


    /**
     * 文件显示 contentType变成application/pdf
     * @param response
     */
    @RequestMapping("/display")
    public void display(HttpServletResponse response){
        FileInputStream inputStream=null;
        OutputStream outputStream=null;
        try {
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=1106-YW1401-2008-0001-001.pdf");
            File file = new File("C:" + File.separator + "1106-YW1401-2008-0001-001.pdf");
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            int i=0;
            byte[] bytes = new byte[1024];
            while((i=inputStream.read(bytes))>0){
                outputStream.write(bytes,0,i);
            }
            outputStream.flush();//将不足1024的写出来
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }


//    当前controller有异常处理规则后ControllerAdvice不会进入
    @ExceptionHandler(SpringWebException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @ResponseBody
    public String errorHandler(Exception e){
        System.out.println(e.getMessage());
        return e.getMessage();
    }
}

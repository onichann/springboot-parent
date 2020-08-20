package com.wt.springboot.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Administrator
 * @date 2019-11-30 下午 3:16
 * PROJECT_NAME gtyzt-sand
 */
@Controller
@RequestMapping("/index/export")
public class FileExportController {

    private static Logger logger = Logger.getLogger(FileExportController.class);



    @RequestMapping(value="/fsd",method = {RequestMethod.POST,RequestMethod.GET})

    public void queryFsdIndexs(@RequestParam("container") String containerId,
                                    @RequestParam("district")  String district,
                                    @RequestParam(value = "isCurrentDistrict",required = false,defaultValue = "true")  boolean isCurrentDistrict,
                                    @RequestParam("date") String date,HttpServletResponse response) throws Exception {
//        List<Map> maps = indexDisplayService.queryFsdIndex(containerId, district, isCurrentDistrict, date);
        List<Map> maps = new ArrayList<>();
        FileInputStream inputStream=null;
        OutputStream outputStream=null;
        try {
            String pdfName = IdUtil.simpleUUID() + ".pdf";
            String pdfUrl = buildPdf(maps,pdfName);
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+pdfName);
            inputStream = new FileInputStream(new File(pdfUrl));
            outputStream = response.getOutputStream();
            int i=0;
            byte[] bytes = new byte[1024];
            while((i=inputStream.read(bytes))>0){
                outputStream.write(bytes,0,i);
            }
            outputStream.flush();
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }finally{
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

    private String buildPdf(List<Map> maps,String fileName) throws Exception {
//        String tempDir = SandContext.getModule("Http").getGroups("FileUpload").getProperty("TempDir")+"\\pdf\\";
        String tempDir = "d:\\pdf\\";
        FileUtil.mkdir(tempDir);
        Document document = new Document(PageSize.A4);
        String url = tempDir +fileName;
        PdfWriter.getInstance(document, new FileOutputStream(new File(url)));
//        BaseFont baseFont = BaseFont.createFont(new ClassPathResource("/font/simsun.ttc,0").getPath(), BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
//        BaseFont baseFont =BaseFont.createFont(new ClassPathResource("/font/simfang.ttf").getPath(),BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//        BaseFont baseFont =BaseFont.createFont((Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath()+"/font/simfang.ttf").substring(1),BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
//        BaseFont baseFont =BaseFont.createFont("f:\\simfang.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);
        document.open();
        PdfPTable pTable = new PdfPTable(2);
        PdfPCell cell;
        int size = 30;
        cell = getPdfCell(font, size, "指标名称");
        pTable.addCell(cell);
        cell = getPdfCell(font, size, "指标值");
        pTable.addCell(cell);
        for (Map map : maps) {
            cell = getPdfCell(font, size, Objects.toString(map.get("zbqc"), ""));
            pTable.addCell(cell);
            cell = getPdfCell(font, size, Objects.toString(map.get("zbz"), "") + " " + Objects.toString(map.get("szdw"), ""));
            pTable.addCell(cell);
        }
        document.add(pTable);
        document.close();
        return url;
    }

    @NotNull
    private PdfPCell getPdfCell(Font font, int size, String name) {
        PdfPCell cell;
        cell = new PdfPCell(new Paragraph(name, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(size);
        return cell;
    }

}

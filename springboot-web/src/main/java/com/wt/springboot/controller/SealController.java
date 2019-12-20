package com.wt.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.itextpdf.text.DocumentException;
import com.wt.springboot.utils.PdfEditor;
import com.wt.springboot.pojo.Result;
import com.wt.springboot.pojo.SealData;
import com.wt.springboot.pojo.SealResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Administrator
 * @date 2019-08-13 下午 2:06
 * PROJECT_NAME sand-demo
 */
@Controller
@RequestMapping("/seal")
public class SealController {

    private static final Log logger = LogFactory.getLog(SealController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("#{'${seal.sign.sync}'}")
    private String sealSyncUrl;

    @Value("#{'${seal.sign.outPath}'}")
    private String sealOutPath;

    @Value("#{'${seal.sign.tempPath}'}")
    private String sealTempPath;

    @Autowired
    private SealData sealData;

    private final String signKey = "sign";

    @RequestMapping(value = "/test",method = {RequestMethod.GET})
    @ResponseBody
    public Object test() throws IOException {
        File file = new File("F:" + File.separator + "laspb_modified.pdf");
        String sealData="{\"sourceId\":201908130001,\"businessCode\": \"TD-ZF-JDS\",\"departmentCode\": \"310000\",\"fileName\": \"laspb_modified.pdf\",\"sealDataList\": [{\"positionY\":\"0\" ,\"page\": \"1\",\"type\": \"keyword\",\"keyword\": \"sign\",\"positionX\":\"0\" ,\"fillInText\": \"\"}]}";
        SealResponse response= signSeal(sealData, file);
        decryptBase64(response.getData(),"laspb_sign.pdf");
        return response;
    }

    @GetMapping("/signFilePath")
    public @ResponseBody Result signFilePath(@RequestParam("fileName") String fileName,
                                                   @RequestParam("filePath") String filePath){
        return sign(fileName, filePath);
    }


    /**]
     *
     * @param oldPdfPath  待签章的pdf文件地址
     * @param fileName  pdf文件名称   如 a.pdf
     * @return
     */
    public Result sign(String fileName, String oldPdfPath) {
        InputStream inputStream = null;
        OutputStream tempOutputStream = null;
        Result result;
        try {
            inputStream = new FileInputStream(oldPdfPath);
            //将受保护的pdf转成未受保护，否则签章无法读取pdf
            FileUtil.mkdir(sealTempPath);
            String tempPdf = sealTempPath + File.separator + fileName;
            tempOutputStream = new FileOutputStream(tempPdf);
            PdfEditor.unlock(inputStream,tempOutputStream);
            //签章
            SealResponse response = signSeal(JSON.toJSONString(buildSealData(fileName,new FileInputStream(tempPdf))), new File(tempPdf));
            //base64转pdf
            decryptBase64(response.getData(), fileName);
            result = new Result().setSuccess(Boolean.TRUE).setData(sealOutPath + File.separator + fileName);
        } catch (IOException | NoSuchFieldException | IllegalAccessException | DocumentException e) {
            logger.debug("签章失败",e);
            result = new Result().setSuccess(Boolean.FALSE).setMessage(e.getMessage());
        }finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(tempOutputStream);
        }
        return result;
    }

    private int signPageIndex(InputStream is) throws IllegalAccessException, NoSuchFieldException, IOException {
        return PdfEditor.readSize(is);
    }

    private SealData buildSealData(String fileName,InputStream is) throws IllegalAccessException, NoSuchFieldException, IOException {
        SealData.SealDataListBean sealDataListBean = new SealData.SealDataListBean();
        sealDataListBean.setPositionY("0");
        sealDataListBean.setPage(Optional.of(signPageIndex(is)).orElse(1).toString());
        sealDataListBean.setType("keyword");
        sealDataListBean.setKeyword(signKey);
        sealDataListBean.setPositionX("0");
        sealDataListBean.setFillInText("");
        sealData.setFileName(fileName);
        sealData.setSealDataList(Collections.singletonList(sealDataListBean));
        return sealData;
    }

    private SealResponse signSeal(String sealData, File file){
        Resource resource=new FileSystemResource(file);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", resource);
        params.add("sealData", sealData);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE,MediaType.MULTIPART_FORM_DATA_VALUE);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<SealResponse> responseEntity = restTemplate.exchange(sealSyncUrl, HttpMethod.POST, httpEntity, new   ParameterizedTypeReference<SealResponse>() {});
        if(responseEntity.getStatusCode()!= HttpStatus.OK){
            throw new RuntimeException(Objects.requireNonNull(responseEntity.getBody()).getMessage());
        }
        return responseEntity.getBody();
    }

    private void decryptBase64(String base64,String fileName) throws IOException {
        checkArgument(!Strings.isNullOrEmpty(base64),"base64数据不存在.");
        checkArgument(!Strings.isNullOrEmpty(fileName),"导出文件名不存在.");
        File outPath = new File(sealOutPath);
        FileUtil.mkdir(outPath);
        byte[] decode = Base64.getDecoder().decode(base64);
        ByteArrayResource byteArrayResource = new ByteArrayResource(decode);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(sealOutPath + File.separator + fileName));
            IOUtils.write(byteArrayResource.getByteArray(),outputStream);
        } catch (IOException e) {
            Throwables.throwIfInstanceOf(e,IOException.class);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }
}

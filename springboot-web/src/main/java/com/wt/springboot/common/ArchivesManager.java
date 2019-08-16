package com.wt.springboot.common;

import com.gisinfo.platform.framework.core.web.GlobalCacheManager;
import com.gisinfo.platform.framework.core.web.struts2.action.ExecuterManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class ArchivesManager {

    public static final Logger log = Logger.getLogger(ArchivesManager.class);
    private static String iP;
    static {
             iP= GlobalCacheManager.getSettingManager().getSetting("ArchiveSystemIp","iP");
    }

    public String callArchive(String acname,String field,String value) throws IOException {
        return Request.Post("http://" + iP + ":8080/rams/systemAction!getArchiveData.action")
                .connectTimeout(2000)
                .socketTimeout(2000)
                .body(MultipartEntityBuilder.create()
                        .addTextBody("code", DigestUtils.md5Hex(acname + field + value + "md5key"), ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Consts.UTF_8))
                        .addTextBody("acname", acname, ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Consts.UTF_8))
                        .addTextBody("field", field, ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Consts.UTF_8))
                        .addTextBody("value", value, ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Consts.UTF_8))
                        .build())
                .execute()
                .returnContent()
                .asString(Consts.UTF_8);

    }

    public String callArchiveFile(String id) {
        String url="http://"+iP+":8080/rams/systemAction!getArchiveFile.action";
        String result="";
        try {
             result= Request.Post(url)
                    .connectTimeout(2000)
                    .socketTimeout(2000)
                    .body(MultipartEntityBuilder.create()
                            .addTextBody("code", DigestUtils.md5Hex(id+"md5key"), ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Consts.UTF_8))
                            .addTextBody("id", id, ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Consts.UTF_8))
                            .build())
                    .execute()
                    .handleResponse(new ResponseHandler<String>() {
                        @Override
                        public String handleResponse(HttpResponse httpResponse) throws IOException {
                            StatusLine statusLine = httpResponse.getStatusLine();
                            HttpEntity entity = httpResponse.getEntity();
                            if (statusLine.getStatusCode() == 200) {
                                Header header = httpResponse.getHeaders(HttpHeaders.CONTENT_TYPE)[0];
                                if (header.getValue().contains(ContentType.APPLICATION_JSON.getMimeType())) {
                                    log.info("获取档案流出错:" + EntityUtils.toString(httpResponse.getEntity()));
                                    return EntityUtils.toString(httpResponse.getEntity());
                                }
                                ServletOutputStream outputStream = null;
                                InputStream inputStream = null;
                                try {
                                    HttpServletResponse response = ExecuterManager.httpExecuter().getResponse();
                                    response.setContentType("application/pdf");
                                    outputStream = response.getOutputStream();
                                    inputStream = httpResponse.getEntity().getContent();
                                    IOUtils.write(IOUtils.toByteArray(inputStream), outputStream);
                                } catch (IOException e) {
                                    log.info(e.getMessage());
                                } finally {
                                    IOUtils.closeQuietly(inputStream);
                                    IOUtils.closeQuietly(outputStream);
                                }
                            } else {
                                log.info("code:" + statusLine.getStatusCode());
                            }
                            EntityUtils.consume(entity);
                            return null;
                        }
                    });
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return result;
    }
}

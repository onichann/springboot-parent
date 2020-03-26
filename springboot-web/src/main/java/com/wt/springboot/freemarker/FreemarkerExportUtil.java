package com.wt.springboot.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

/**
 * @author wutong
 *dataMap.put("kjtxtpList", buildData(v.get("scjg")));
 * FreemarkerExportUtil freemarkerExportUtil = new FreemarkerExportUtil();
 *         String url = SandContext.getValueFromGroup("Http", "FileUpload", "CgscReport");
 *         FileUtil.mkdir(url);
 *         String exportFilePath = url+scrwGuid+".doc";
 *         freemarkerExportUtil.exportWord("/ftl/审查报告.ftl",dataMap,exportFilePath);
 */
public class FreemarkerExportUtil {
    private static Logger logger = LoggerFactory.getLogger(FreemarkerExportUtil.class);

    /**
     *
     * @param templatePath  模板相对路径
     * @param dataMap 数据
     * @param exportFilePath 导出绝对路径
     */
    public void exportWord(String templatePath, Map<String, Object> dataMap, String exportFilePath) throws Exception {
        Writer out = null;
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setDefaultEncoding("UTF-8");
            templatePath = templatePath.replaceAll("\\\\", "/");
            String ftlPath=templatePath.substring(0, templatePath.lastIndexOf("/"));
            configuration.setClassForTemplateLoading(this.getClass(),ftlPath);
            String ftlName=templatePath.substring(templatePath.lastIndexOf("/")+1);
            Template t = configuration.getTemplate(ftlName,"UTF-8");
            File outFile = new File(exportFilePath);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
            t.process(dataMap,out);
        } catch (Exception e) {
            logger.error("生成word文档出错");
            throw e;
        }  finally {
            IOUtils.closeQuietly(out);
        }
    }
}

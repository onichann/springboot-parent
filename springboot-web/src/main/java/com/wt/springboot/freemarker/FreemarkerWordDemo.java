package com.wt.springboot.freemarker;//package com.wt.freemarker;
//
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.util.LinkedHashMap;
//
///**
// * FreemarkerWordDemo is used to Generate xxx.doc file by template.xml.
// * template.xml is from a xx.doc save as xx.xml
// * @author JavaLuSir
// *
// */
//public class FreemarkerWordDemo {
//    //Configure of freemarker Configuration
//    private Configuration conf = null;
//    /**
//     * construct
//     */
//    public FreemarkerWordDemo() {
//        conf=new Configuration();
//        conf.setDateFormat("utf-8");
//    }
//
//    /**
//     * write a File to FileSystem
//     * @throws TemplateException
//     * @throws IOException
//     */
//    public void WriteDocFile() throws TemplateException, IOException{
//        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
//        //设置template.xml路径
//        conf.setClassForTemplateLoading(FreemarkerWordDemo.class, "/com/wt/freemarker/");
//        Template template = conf.getTemplate("template.xml");
//        //设置输出路径
//        OutputStream os = new FileOutputStream("D:/hello.doc");
//        OutputStreamWriter ow = new OutputStreamWriter(os);
//
//        map.put("id", "123122qq");
//        map.put("name","王强");
//        map.put("image", JFreeChartD.getBase64ImageStr(JFreeChartD.writeBarPic()));
//
//        template.process(map, ow);//写入doc文件
//        ow.close();
//        os.close();
//    }
//
//    public static void main(String[] args) throws TemplateException, IOException {
//        FreemarkerWordDemo fwd = new FreemarkerWordDemo();
//        fwd.WriteDocFile();
//    }
//
//}
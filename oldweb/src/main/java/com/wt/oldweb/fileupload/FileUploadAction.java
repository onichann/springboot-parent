package com.wt.oldweb.fileupload;

import com.alibaba.fastjson.JSON;
import com.gisinfo.platform.framework.common.util.IDUtil;
import com.gisinfo.platform.framework.core.web.GlobalCacheManager;
import com.gisinfo.platform.framework.jdbc.JDBCName;
import com.gisinfo.platform.framework.jdbc.JdbcUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "FileUploadAction",urlPatterns = "/FileUploadAction")
public class FileUploadAction extends HttpServlet {
    private int maxSize = 200 * 1024 * 1024;
    private String uploadDir;
    private String tplx="";
    private String splx="";
    private static Logger logger= Logger.getLogger(FileUploadAction.class);
    public FileUploadAction() {
        this.uploadDir = GlobalCacheManager.getSettingManager().getSetting("upload", "uploadDir");
        this.maxSize = Integer.parseInt(GlobalCacheManager.getSettingManager().getSetting("upload", "maxSize"));
        this.tplx=GlobalCacheManager.getSettingManager().getSetting("upload","tplx");
        this.splx=GlobalCacheManager.getSettingManager().getSetting("upload","splx");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String id="";
        id=request.getParameter("id");
        Map<String,Object> result = new HashMap<>();
        ReturnJson returnJson = new ReturnJson();
        List<T_ZTDT_LYJQ_TPSP> t_ztdt_lyjq_tpsps=new ArrayList<>();
        File file = new File(uploadDir);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if(isMultipart){
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(1024 * 4);
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setFileSizeMax(maxSize);
                List<FileItem> fileItems = upload.parseRequest(request);
                Iterator iter = fileItems.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if(item.isFormField()){
                        id=item.getString();
                        continue;
                    }else{
                        String newName=transfor(item.getName());
                        String lx=item.getName().substring(item.getName().lastIndexOf("."));
                        String dz="";
                        if(tplx.indexOf(lx)>-1){
                            dz=uploadDir+"TP"+File.separator+id.toUpperCase()+File.separator;
                        }else if(splx.indexOf(lx)>-1){
                            dz=uploadDir+"SP"+File.separator+id.toUpperCase()+File.separator;
                        }else{
                            dz=uploadDir+"OTHER"+File.separator+id.toUpperCase()+File.separator;
                        }
                        File dzFile = new File(dz);
                        if(!dzFile.exists()){
                            dzFile.mkdirs();
                        }
                        item.write(new File(dz + newName));
                        //保存数据库的信息
                        T_ZTDT_LYJQ_TPSP t_ztdt_lyjq_tpsp=new T_ZTDT_LYJQ_TPSP();
                        t_ztdt_lyjq_tpsp.setMc(newName);
                        t_ztdt_lyjq_tpsp.setLx(lx);
                        t_ztdt_lyjq_tpsp.setDz(dz+newName);
                        t_ztdt_lyjq_tpsp.setScsj(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime()));
                        t_ztdt_lyjq_tpsps.add(t_ztdt_lyjq_tpsp);
                    }
                }
                //设置id和dz
                for(T_ZTDT_LYJQ_TPSP t_ztdt_lyjq_tpsp:t_ztdt_lyjq_tpsps){
                    t_ztdt_lyjq_tpsp.setTpsc_guid(IDUtil.guid());
                    t_ztdt_lyjq_tpsp.setId(id);
                }
                //保存数据入库
                save(t_ztdt_lyjq_tpsps);
                returnJson.setSuccess(true);
                returnJson.setInfo("文件上传成功");
                Map<String,Object> map=new HashMap<>();
                map.put("file",t_ztdt_lyjq_tpsps);
                map.put("dir",uploadDir);
                returnJson.setData(map);
            }else{
                returnJson.setSuccess(false);
                returnJson.setInfo("未上传附件");
                returnJson.setData(null);
            }

        } catch (Exception e) {
            returnJson.setSuccess(false);
            returnJson.setInfo("上传文件失败:"+e.getMessage());
            returnJson.setData(null);
            logger.info(e.getMessage());
        }finally{
            response.getWriter().append(JSON.toJSONString(returnJson));
            //response.getWriter().write("<html><body><textarea>" + JSON.toJSONString(result) + "</textarea></body></html>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);

    }

    private String transfor(String oldFileName){
        return IDUtil.guid()+oldFileName.substring(oldFileName.lastIndexOf("."));
    }

    private void save(List<T_ZTDT_LYJQ_TPSP> t_ztdt_lyjq_tpsps) throws Exception{
        Connection conn=JdbcUtil.getConnection(JDBCName.DEFAULT);
        String sql="insert into t_ztdt_lyjq_tpsp(tpsc_guid,id,mc,dz,lx,scsj) values";
//        String sql="insert into t_ztdt_lyjq_tpsp(tpsc_guid,id,mc,dz,lx) values";
        String[] strings=new String[t_ztdt_lyjq_tpsps.size()];
        int i=0;
        for(T_ZTDT_LYJQ_TPSP t_ztdt_lyjq_tpsp:t_ztdt_lyjq_tpsps){
            StringBuilder values=new StringBuilder("");
            values.append("(");
            values.append("'").append(t_ztdt_lyjq_tpsp.getTpsc_guid()).append("'").append(",");
            values.append("'").append(t_ztdt_lyjq_tpsp.getId()).append("'").append(",");
            values.append("'").append(t_ztdt_lyjq_tpsp.getMc()).append("'").append(",");
            values.append("'").append(t_ztdt_lyjq_tpsp.getDz()).append("'").append(",");
            values.append("'").append(t_ztdt_lyjq_tpsp.getLx()).append("'").append(",");
            values.append("to_timestamp('").append(t_ztdt_lyjq_tpsp.getScsj()).append("','yyyy-mm-dd hh-mi-ss')");
            values.append(")");
            strings[i]=values.toString();
            i++;
        }
        sql+=StringUtils.join(strings,",");
        try {
            JdbcUtil.executeUpdate(conn,sql);
        } catch (Exception e) {
            throw new RuntimeException("SQL执行异常");
        }finally {
            JdbcUtil.close(conn);
        }
    }
}

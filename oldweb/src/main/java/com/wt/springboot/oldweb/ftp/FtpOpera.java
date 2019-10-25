package com.wt.springboot.oldweb.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/3/2.
 */
public class FtpOpera {

    private static String FTP_CONTROL_ENCODE = "GBK";
    private FtpConnInfo connInfo = null;
    private FTPClient client = null;
    private boolean hasLogined = false;


    public FtpOpera(FtpConnInfo info) {
        this.connInfo = info;
    }

    /**
     * 是否登录FTP服务器
     *
     * @return
     */
    public boolean isLogin(){
        return this.hasLogined;
    }

    /**
     * 连接登录FTP服务器
     *
     * @return
     * @throws com.gisinfo.web.app.grab.DataGrabException
     */
    public FTPClient connect() throws DataGrabException {
        this.client = new FTPClient();
//        调试代码时，需要查看FTP命令的执行情况可以将该注释放出
//        this.client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        try {
            if (isEmptyOrBlank(connInfo.getPort())) {
                client.connect(connInfo.getServer());
            } else {
                client.connect(connInfo.getServer(), Integer.parseInt(connInfo.getPort()));
            }
        } catch (IOException e) {
            throw new DataGrabException("连接FTP服务失败");
        }

        try {
            client.login(connInfo.getUsername(), connInfo.getPassword());
        } catch (IOException e) {
            throw new DataGrabException("登录FTP服务失败");
        }

        int reply = client.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            this.close();
            throw new DataGrabException("登录FTP服务失败");
        }

        hasLogined = true;

        client.setControlEncoding(FTP_CONTROL_ENCODE);
        if(!FTPReply.isPositiveCompletion(client.getReplyCode())){
            this.close();
            throw new DataGrabException("设置FTP参数失败");
        }

        client.setBufferSize(5 * 1024 * 1024);
        if(!FTPReply.isPositiveCompletion(client.getReplyCode())){
            this.close();
            throw new DataGrabException("设置FTP参数失败");
        }

        try {
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            if(!FTPReply.isPositiveCompletion(client.getReplyCode())){
                this.close();
                throw new DataGrabException("设置FTP参数失败");
            }
        } catch (IOException e) {
            this.close();
            throw new DataGrabException("设置FTP参数失败");
        }

        return client;
    }

    /**
     * 关闭FTP连接
     */
    public void close() {
        hasLogined = false;

        if (client != null) {
            try {
                client.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转FTP的工作目录
     *
     * @param remote
     * @return
     * @throws DataGrabException
     */
    public boolean gotoPath(String remote) throws DataGrabException {
        if(!isLogin()){
            throw new DataGrabException("尚未连接登录FTP服务");
        }

        String encodePath = remote;
        try {
            encodePath = new String(remote.getBytes(FTP_CONTROL_ENCODE), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        try {
            client.changeWorkingDirectory(encodePath);
            return FTPReply.isPositiveCompletion(client.getReplyCode());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 列出FTP当前工作目录下的文件
     *
     * @return
     * @throws DataGrabException
     */
    public List<FTPFile> list() throws DataGrabException {
        if(!isLogin()){
            throw new DataGrabException("尚未连接登录FTP服务");
        }

        try {
            FTPFile[] files = client.listFiles();
            return Arrays.asList(files);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<FTPFile>();
    }

    /**
     * 列出FTP远程路径下的文件
     *
     * @param remote 远程路径
     * @return
     * @throws DataGrabException
     */
    public List<FTPFile> list(String remote) throws DataGrabException {
        if(!isLogin()){
            throw new DataGrabException("尚未连接登录FTP服务");
        }

        String encodePath = remote;
        try {
            encodePath = new String(remote.getBytes(FTP_CONTROL_ENCODE), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ArrayList<FTPFile>();
        }

        try {
            FTPFile[] files = client.listFiles(encodePath);
            return Arrays.asList(files);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<FTPFile>();
    }

    /**
     * 下载FTP上的远程文件至本地
     *
     * @param remote 远程文件路径
     * @param local 本地文件路径
     * @return
     * @throws DataGrabException
     */
    public boolean download(String remote,String local) throws DataGrabException {
        if(!isLogin()){
            throw new DataGrabException("尚未连接登录FTP服务");
        }

        List<FTPFile> files = this.list(remote);
        if(files.size() == 0){
            return false;
        }

        File file = new File(local);
        if(file.isDirectory() || file.exists()){
            return false;
        }

        FileOutputStream fos = null;
        boolean copyRes = false;
        try {
            String encodeRemote = new String(remote.getBytes(FTP_CONTROL_ENCODE),"ISO-8859-1");
            fos = new FileOutputStream(file);
            copyRes = client.retrieveFile(encodeRemote,fos);
            if(!FTPReply.isPositiveCompletion(client.getReplyCode())){
                return false;
            }

            fos.close();
            return copyRes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 上传本地文件至FTP
     *
     * @param remote 远程文件路径
     * @param local 本地文件路径
     * @return
     * @throws DataGrabException
     */
    public boolean upload(String remote,String local) throws DataGrabException {
        if(!isLogin()){
            throw new DataGrabException("尚未连接登录FTP服务");
        }

        List<FTPFile> files = this.list(remote);
        if(files.size() != 0){
            return false;
        }

        File file = new File(local);
        if(file.isDirectory() || !file.exists()){
            return false;
        }

        FileInputStream fis = null;
        boolean copyRes = false;
        try {
            String encodeRemote = new String(remote.getBytes(FTP_CONTROL_ENCODE),"ISO-8859-1");
            fis = new FileInputStream(file);
            copyRes = client.appendFile(encodeRemote, fis);
            if(!FTPReply.isPositiveCompletion(client.getReplyCode())){
                return false;
            }

            fis.close();
            return copyRes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除FTP上的文件
     *
     * @param remote 远程文件
     * @return
     * @throws DataGrabException
     */
    public boolean delete(String remote) throws DataGrabException {
        if(!isLogin()){
            throw new DataGrabException("尚未连接登录FTP服务");
        }
        boolean res = false;
        try {
            String encodeRemote = new String(remote.getBytes(FTP_CONTROL_ENCODE), "ISO-8859-1");
            res = client.deleteFile(encodeRemote);

            if(!FTPReply.isPositiveCompletion(client.getReplyCode())){
                return false;
            }

            return res;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        FtpConnInfo info = new FtpConnInfo();
        info.setServer("192.168.0.114");
//        info.setUsername("getdown");
//        info.setPassword("getdown1");


        FtpOpera tool = new FtpOpera(info);
        try {
            tool.connect();

            String baseRemote = "/";//"/smc/预警信号";
            List<FTPFile> files = tool.list(baseRemote);
            String name = "";
            for (FTPFile file : files) {
                name = file.getName();
                if(".".equals(name) || "..".equals(name)){
                    continue;
                }
                System.out.println(file.getName());// + "\t" + tool.download(baseRemote + "/" + file.getName(),"e:\\test_temp\\" + file.getName()));
            }

//            System.out.println(tool.upload("/smc/预警信号/测试.txt", "e:\\test_temp\\smc_tqyj_201408241848.txt"));
           // System.out.println(tool.delete("/smc/预警信号/测试.txt"));
        } catch (DataGrabException e) {
            e.printStackTrace();
        } finally {
            tool.close();
        }
    }
    
    public static boolean isEmptyOrBlank(Object paramObject)
    {
      if (paramObject == null)
        return true;
      if ((paramObject instanceof String))
        return "".equals(((String)paramObject).trim());
      return false;
    }

}

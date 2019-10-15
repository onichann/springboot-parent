package com.wt.oldweb.ftp;

/**
 * Created by Administrator on 2015/3/2.
 */
public class FtpConnInfo {

    private String server = "";

    private String port = "21";

    private String username = "anonymous";

    private String password = "anonymous";

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        if(null == port || "".equals(port.trim())){
            this.port = "21";
            return;
        }

        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(null == username || "".equals(username.trim())){
            this.username = "anonymous";
            return;
        }

        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(null == password || "".equals(password.trim())){
            this.password = "anonymous";
            return;
        }

        this.password = password;
    }
}

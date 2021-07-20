package com.wt.springboot.bio;

import java.io.*;
import java.net.Socket;

/**
 * @author Pat.Wu
 * @date 2021/7/20 9:17
 * 说明:
 */
public class BioClient {
    public static void main(String[] args) {
        try {
            Socket socket=new Socket("127.0.0.1", 9999);
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while ((line = br.readLine()) != null) {
                pw.println(line);
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

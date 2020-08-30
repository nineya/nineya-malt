package com.nineya.malt.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author linsongwang
 * @date 2020/6/14 15:06
 */
public class HttpProxyService {
    private static int port = 12345;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("启动成功！监听端口："+port);
        while (true){
            new PoroxySockerHandle(serverSocket.accept()).start();
        }
    }

    private static class PoroxySockerHandle extends Thread{

        private Socket socket;

        public PoroxySockerHandle(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            OutputStream clientOutput = null;
            InputStream clientInput = null;
            Socket proxySocket = null;
            InputStream proxyInput = null;
            OutputStream proxyOutput = null;
            try {
                clientInput = socket.getInputStream();
                clientOutput = socket.getOutputStream();
                byte[] buffer = clientInput.readAllBytes();
                String headStr = new String(buffer);
                String host = "blog.nineya.com";
                int port = 443;
                System.out.println(headStr);
                String heads[] = headStr.split("\r\n");
                for (String line : heads){
                    if (line.length() == 0) {
                        break;
                    } else {
                        String[] temp = line.split(" ");
                        if (temp[0].contains("Host")) {
                            String[] hostTemp = temp[1].split(":");
                            host = hostTemp[0];
                            if (hostTemp.length>1)port = Integer.parseInt(hostTemp[1]);
                            break;
                        }
                    }
                }
                System.out.println("接受请求: host=" + host + ",port=" + port);

                if ("CONNECT".contains(headStr)) {//https先建立隧道
                    clientOutput.write("HTTP/1.1 200 Connection Established\r\n\r\n".getBytes());
                    clientOutput.flush();
                    System.out.println("200 建立隧道成功！");
                }else {
                    proxySocket = new Socket(host, port);
                    System.out.println("连接目标服务器： "+host+":"+port+" 成功！");
                    proxyInput = proxySocket.getInputStream();
                    proxyOutput = proxySocket.getOutputStream();
                    proxyOutput.write(buffer);
                    proxyOutput.flush();
                    clientOutput.write(proxyInput.readAllBytes());
                    clientOutput.flush();
                    proxySocket.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
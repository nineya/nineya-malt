package com.nineya.malt.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * @author linsongwang
 * @date 2020/6/14 11:25
 */
public class HttpProxy {
    private static int port = 12345;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("启动成功！监听端口："+port);
        while (true){
            new SocketHandle(serverSocket.accept()).start();
        }
    }
}
class SocketHandle extends Thread {

    private Socket socket;

    public SocketHandle(Socket socket) {
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
            String host = null;
            int port = 80;
            byte[] buffer = clientInput.readAllBytes();
            String headStr = new String(buffer);
            System.out.println(headStr);
            String heads[] = headStr.split("\r\n");
            for (String line : heads){
                System.out.println(line);
                if (line.length() == 0) {
                    break;
                } else {
                    String[] temp = line.split(" ");
                    if (temp[0].contains("Host")) {
                        String[] hostTemp = temp[1].split(":");
                        host = hostTemp[0];
                        port = Integer.parseInt(hostTemp[1]);
                        break;
                    }
                }
            }
            System.out.println("获取请求: host=" + host + ",port=" + port);

            if ("CONNECT".equalsIgnoreCase(headStr.substring(0, headStr.indexOf(" ")))) {//https先建立隧道
                clientOutput.write("HTTP/1.1 200 Connection Established\r\n\r\n".getBytes());
                clientOutput.flush();
            }

            //连接到目标服务器
            proxySocket = new Socket(host, port);
            System.out.println("连接目标服务器： "+host+":"+port+" 成功！");
            proxyInput = proxySocket.getInputStream();
            proxyOutput = proxySocket.getOutputStream();
            //根据HTTP method来判断是https还是http请求
            proxyOutput.write(buffer);
            clientOutput.write(proxyInput.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (proxyInput != null) {
                try {
                    proxyOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (proxyOutput != null) {
                try {
                    proxyOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (proxySocket != null) {
                try {
                    proxySocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientInput != null) {
                try {
                    clientInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientOutput != null) {
                try {
                    clientOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
class ProxyHandleThread extends Thread {

    private InputStream input;
    private OutputStream output;

    public ProxyHandleThread(InputStream input, OutputStream output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            while (true) {
                output.write(input.readAllBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
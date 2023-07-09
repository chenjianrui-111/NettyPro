package com.cjr.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author CJR
 */
public class BIOServer {

    public static void main(String[] args) throws IOException {

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");

        while (true){

            //监听等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //可以与客户端通信
                    handler(socket);
                }
            });
        }
    }

    //编写一个 handler方法与客户端通信
    public static void handler(Socket socket){

        byte[] bytes = new byte[1024];
        try {
            System.out.println("线程信息 id =" + Thread.currentThread().getId() + "名字=" +Thread.currentThread().getName());
            InputStream inputStream = socket.getInputStream();

            //循环读取客户端发送的数据
            while (true){
                System.out.println("线程信息 id =" + Thread.currentThread().getId() + "名字=" +Thread.currentThread().getName());
                int read = inputStream.read(bytes);
                if (read != -1){
                    System.out.println(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println("关闭与client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

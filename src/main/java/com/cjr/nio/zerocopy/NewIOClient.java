package com.cjr.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author CJR
 */
public class NewIOClient {
    public static void main(String[] args) throws Exception {

        FileChannel fileChannel = null;
        long startTime;
        long transferCount;
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(new InetSocketAddress("localhost", 7001));
            String filename = "protoc-3.6.1-win32.zip";

            //得到一个文件channel
            fileChannel = new FileInputStream(filename).getChannel();

            //准备发送
            startTime = System.currentTimeMillis();

            //在linux下一个transferTo 方法就可以完成传输
            //在windows 下 一次调用 transferTo 只能发送8m , 就需要分段传输文件, 而且要主要
            //传输时的位置 =》 课后思考...
            //transferTo 底层使用到零拷贝
            transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
            System.out.println("发送的总的字节数 =" + transferCount + " 耗时:" + (System.currentTimeMillis() - startTime));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            //关闭
            fileChannel.close();
        }
    }
}

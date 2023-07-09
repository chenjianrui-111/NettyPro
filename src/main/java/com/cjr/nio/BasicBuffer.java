package com.cjr.nio;

import java.nio.IntBuffer;

/**
 * @author CJR
 */
public class BasicBuffer {
    public static void main(String[] args) {

        //创建一个buffer,大小为5，即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        //从buffer读取数据，利用buffer.flip()完成读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

    }
}

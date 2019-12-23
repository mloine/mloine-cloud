package com.mloine.msclass;

import java.util.concurrent.Semaphore;

/**
 *  @Author: XueYongKang
 *  @Description: Semaphore 并发工具类
 *  @Data: 2019/12/18 17:43
 */
public class TestSemaphore {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
                new Thread(()->{
                    try {
                        semaphore.acquire();//默认一个
                        System.out.println("我抢到坑位了！"+ finalI);
                        Thread.sleep(1000L);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

        }
    }
}

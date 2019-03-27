package com.shengsiyuan.netty.codeing;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Liushishuang
 * @Description:
 * @Date: 18:19 2019-3-26
 */
public class TestCountDownLatch {

    @Test
    public void testCountDownLatch1() throws Exception {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    begin.await();
                System.out.println(Thread.currentThread().getName() + "起跑");

                System.out.println(Thread.currentThread().getName() + "到达终点");
                end.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        System.out.println("1秒后统一开始");
        TimeUnit.SECONDS.sleep(1);
        begin.countDown();
        end.await();
        System.out.println("停止比赛");


    }
}

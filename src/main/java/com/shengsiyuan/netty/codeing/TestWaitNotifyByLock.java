package com.shengsiyuan.netty.codeing;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Liushishuang
 * @Description:
 * @Date: 19:17 2019-3-25
 */
public class TestWaitNotifyByLock {
    public static void main(String[] args) {
        AlternateDemo ad = new AlternateDemo();
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                ad.loopA(i);
            }
        },"A").start();
        new Thread(() -> {

            for (int i = 1; i <= 5; i++) {
                ad.loopB(i);
            }

        }, "B").start();

        new Thread(() -> {

            for (int i = 1; i <= 5; i++) {
                ad.loopC(i);

                System.out.println("-----------------------------------");
            }

        }, "C").start();

    }

    static class AlternateDemo {
        private int number;

        private Lock lock = new ReentrantLock();
        private Condition condition1 = lock.newCondition();
        private Condition condition2 = lock.newCondition();
        private Condition condition3 = lock.newCondition();

        public void loopA(int totalLoop) {
            lock.lock();
            try {
                if (number != 1) {
                    condition1.await();
                }

                for (int i = 1; i < 1; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i + totalLoop);
                }

                number = 2;
                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        public void loopB(int totalLoop) {
            lock.lock();
            try {
                //1. 唤醒
                if (number != 2) {
                    condition2.await();
                }
                //2. 打印
                for (int i = 1; i < 1; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i + totalLoop);
                }
                //3.唤醒
                number = 3;
                condition3.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        public void loopC(int totalLoop) {
            lock.lock();
            try {
                //1. 唤醒
                if (number != 3) {
                    condition3.await();
                }
                //2. 打印
                for (int i = 1; i < 1; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i + totalLoop);
                }
                //3.唤醒
                number = 1;
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

}

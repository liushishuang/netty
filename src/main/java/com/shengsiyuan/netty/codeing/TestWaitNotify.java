package com.shengsiyuan.netty.codeing;

/**
 * @Author: Liushishuang
 * @Description:
 * @Date: 10:47 2019-3-26
 */
public class TestWaitNotify {
    public static void main(String[] args) {
        WaitNofity waitNofity = new WaitNofity();
        new Thread(waitNofity::Even,"Even").start();
        new Thread(waitNofity::Odd,"Odd").start();
    }

    static class WaitNofity {
        private int start = 1;
        private boolean flag = false;

        public void Even() {
            while (start <= 100) {
                synchronized (WaitNofity.class) {
                    System.out.println("偶数线程抢到了锁");
                    if (flag) {
                        System.out.println(Thread.currentThread().getName() + "--偶数--" + start);

                        start++;
                        flag = false;
                        System.out.println("=========");
                        WaitNofity.class.notify();
                    } else {
                        System.out.println("偶数线程进入WaitSet");
                        try {
                            WaitNofity.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public void Odd() {
            while (start <= 100) {
                synchronized (WaitNofity.class) {
                    System.out.println("奇数线程抢到了锁");
                    if (!flag) {
                        System.out.println(Thread.currentThread().getName() + "--奇数--" + start);

                        start++;
                        flag = true;
                        System.out.println("=========");
                        WaitNofity.class.notify();
                    } else {
                        System.out.println("奇数线程进入WaitSet");
                        try {
                            WaitNofity.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}

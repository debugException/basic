package com.xh.basic.bean;

import java.util.concurrent.CountDownLatch;

/**
 * @author szq
 * @Package com.xh.basic.bean
 * @Description: 示例：CountDownLatch
 * 老板进入会议室等待5个人全部到达会议室才会开会。所以这里有两个线程老板等待开会线程、员工到达会议室
 * @date 2018/5/29:07
 */
public class CountDownLatchTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(5);

    /**
     * Boss线程，等到员工到达开会
     */
    static class BossThread extends  Thread{
        @Override
        public void run() {
            System.out.println("Boss在会议室等待，总共有" + countDownLatch.getCount() + "个人开会。。。");
            try{
                //Boss等待
                countDownLatch.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("所有人都已经到齐了，开始开会。。。");
        }
    }

    /**
     * 员工到达会议室
     */
    static class EmployeeThread extends  Thread{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "到达会议室。。。");
            //员工到达会议室 count-1
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) {
        //Boss线程启动
        new BossThread().start();
        for(int i=0; i<countDownLatch.getCount(); i++){
            new EmployeeThread().start();
        }
    }

}

package com.xh.basic.async;

/**
 * @author szq
 * @Package com.xh.basic.async
 * @Description: to do ...
 * @date 2018/5/89:06
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class Task {

    public static Random random = new Random();

    @Async("taskExecutor")
    public void doTaskOne() throws Exception{
        log.info("开始做任务一");
        log.info("完成任务一，耗时：" + time() + "毫秒");
    }

    @Async("taskExecutor")
    public void doTaskTwo() throws Exception{
        log.info("开始做任务二");
        log.info("完成任务二，耗时：" + time() + "毫秒");
    }

    @Async("taskExecutor")
    public void doTaskThree() throws Exception{
        log.info("开始做任务三");
        log.info("完成任务三，耗时：" + time() + "毫秒");
    }

    private long time() throws  Exception{
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        return end - start;
    }
}

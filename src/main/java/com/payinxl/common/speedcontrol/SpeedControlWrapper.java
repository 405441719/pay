package com.payinxl.common.speedcontrol;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 速度控制
 * 
 * @author jx
 */
public class SpeedControlWrapper
{
    // 发送速度
    private volatile int speed = 60;

    // 统计周期开始时间
    private volatile long lastTime = -1L;

    // 统计周期默认为60秒
    private volatile long interval = 60L * 1000L;
    private AtomicInteger count = new AtomicInteger();

    // 设置周期的开始时间
    private void reset()
    {
        if (count.compareAndSet(count.get(), 0))
        {
            lastTime = System.currentTimeMillis();
        }
    }

    protected void change(int speed, long interval)
    {
        if (speed > 0)
        {
            this.speed = speed;
        }
        if (interval > 0L)
        {
            this.interval = interval;
        }
    }

    protected boolean speedLimit()
    {
        long elapsedTime = System.currentTimeMillis() - lastTime;
        if (elapsedTime > interval)
        {
            reset();
        }
        return speed >= count.incrementAndGet();
    }

}

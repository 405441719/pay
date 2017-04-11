package com.payinxl.common.speedcontrol;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 控制速率代理
 * 
 * @author jx
 */
public class SpeedControlProxy
{
    private static final Map<String, SpeedControlWrapper> instanceMap = new Hashtable<String, SpeedControlWrapper>();

    public static void init(List<SpeedControllable> speedControllables)
    {
        for (SpeedControllable speedControllable : speedControllables)
        {
            init(speedControllable);
        }
    }

    /**
     * 启动时初始化
     * 
     * @param device
     */
    public static void init(SpeedControllable speedControllable)
    {
        SpeedControlWrapper speedControlWrapper = new SpeedControlWrapper();
        speedControlWrapper.change(speedControllable.getSpeedControlSpeed(),
            speedControllable.getSpeedControlInterval());
        instanceMap.put(speedControllable.getSpeedControlInstanceId(), speedControlWrapper);
    }

    public static void refresh(List<SpeedControllable> speedControllables)
    {
        for (SpeedControllable speedControllable : speedControllables)
        {
            refresh(speedControllable);
        }
    }

    /**
     * 刷新speed,interval参数
     * 
     * @param speedControllable
     */
    public static void refresh(SpeedControllable speedControllable)
    {
        SpeedControlWrapper speedControlWrapper = instanceMap.get(speedControllable.getSpeedControlInstanceId());
        if (speedControlWrapper == null)
        {
            init(speedControllable);
        }
        else
        {
            speedControlWrapper.change(speedControllable.getSpeedControlSpeed(),
                speedControllable.getSpeedControlInterval());
        }
    }

    /**
     * 速度限制
     * 
     * @param deviceid
     * @return true不限速，false被限速
     */
    public static boolean speedLimit(String instanceId)
    {
        SpeedControlWrapper speedControlWrapper = instanceMap.get(instanceId);
        if (speedControlWrapper == null)
        {
            return false;
        }
        return speedControlWrapper.speedLimit();
    }

}

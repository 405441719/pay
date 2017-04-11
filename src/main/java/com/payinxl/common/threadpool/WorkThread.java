package com.payinxl.common.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkThread extends Thread
{
	public static ExecutorService threadpool;
    private static Logger logger = LoggerFactory.getLogger(WorkThread.class);
    private  WorkData workData;
    public WorkThread(WorkData workData)
    {
    	this.workData=workData;
    }
    public static void init(int num){
       logger.info("初始化固定线程池大小为"+num);
	   threadpool= Executors.newFixedThreadPool(num);
    }
    @Override
    public void run()
    {
    	if(workData!=null){
    		logger.info("执行线程：服务"+workData.getWorkService().getClass().getName()+";数据：" + workData.getData());
    		workData.getWorkService().work(workData.getData());
    	}else{
    		logger.error("workData数据为空");
    	}
        
    }
    public static void put(WorkService workService, Object data)
    {
        WorkData workData = new WorkData();
        workData.setWorkService(workService);
        workData.setData(data);
        Thread thread = new WorkThread(workData);
        threadpool.execute(thread);
    }
}


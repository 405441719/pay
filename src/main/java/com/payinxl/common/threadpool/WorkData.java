package com.payinxl.common.threadpool;

public class WorkData
{
    private WorkService workService;
    private Object data;

    public WorkService getWorkService()
    {
        return workService;
    }

    public void setWorkService(WorkService workService)
    {
        this.workService = workService;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

}

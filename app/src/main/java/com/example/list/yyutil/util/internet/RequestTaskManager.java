package com.example.list.yyutil.util.internet;

import java.util.HashMap;

/**
 * Created by yy on 2018/6/11.
 */

public class RequestTaskManager {

    //	管理所有联网请求
    private HashMap<String, MyTask> requestTask;

    private static RequestTaskManager instance;

    public RequestTaskManager() {
        isEmpty();
    }

    synchronized public static RequestTaskManager getInstance ()
    {
        if ( instance == null ) instance = new RequestTaskManager();
        return instance;
    }

    private boolean isEmpty ()
    {
        if ( requestTask == null )
        {
            requestTask = new HashMap<>();
        }
        return false;
    }

    public void addTask (MyTask myTask)
    {
        MyTask tempTask = requestTask.get(myTask.getUrl());
        if ( tempTask != null )
        {
//            Common.log1("============REMOVE============"+myTask.getUrl());
            tempTask.setDead(true);
            requestTask.remove(myTask.getUrl());
        }
        requestTask.put(myTask.getUrl(), myTask);
    }

    public void removeTask ( MyTask myTask )
    {
//        Common.log1("======ALL REMOVE ========="+requestTask.size());
        myTask.setDead(true);
        requestTask.remove(myTask.getUrl());
    }

    public void removeTask ( String url )
    {
        MyTask tempTask = requestTask.get(url);
        if ( tempTask != null )
        {
//            Common.log1("============REMOVE String ============"+url);
            tempTask.setDead(true);
            requestTask.remove(url);
        }


    }





}

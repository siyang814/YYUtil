package com.example.list.yyutil.util.image.updata;

import android.text.TextUtils;

import com.example.list.yyutil.util.image.updata.model.ImageEnty;
import com.example.list.yyutil.util.internet.CallBack;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yy on 2018/5/17.
 */

public class UpdataImageListManager{


    //线程池
    public static final int MAX_SIZE = 2;

    private ThreadPoolExecutor mThreadPoolExecutor = new ThreadPoolExecutor(
            MAX_SIZE,
            MAX_SIZE,
            60,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(),//线程管理队列
            new ThreadFactory() {
                AtomicInteger mAtomicInteger = new AtomicInteger();
                @Override
                public Thread newThread(Runnable r) {
                    //参数二区分线程
                    Thread thread =
                            new Thread(r,"Thread Number # "+mAtomicInteger.getAndIncrement());

                    return thread;
                }
            }
    );



    private CallBack callBack;
//      任务计数
    private int taskNum = 0;

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }


    public void setInfo (String url , List<ImageEnty> list, CallBack callBack )
    {
//        userId = Common.getUserIdEncrypt();
        this.url = url;
        this.callBack = callBack;
        setImgLinkedList( list );
    }

    String userId = null;
    String url = null;
    private LinkedList<ImageEnty> imgLinkedList;

    private LinkedList<ImageEnty> getImgLinkedList() {
        return imgLinkedList;
    }

    private void setImgLinkedList(List<ImageEnty> list) {

        this.imgLinkedList = new LinkedList<>();

        for ( ImageEnty enty : list )
        {
            if ( enty.status != ImageEnty.OK && !TextUtils.isEmpty(enty.getImgPath()))
            {
                imgLinkedList.addLast(enty);
            }
        }
    }
//  启动
    public void start ()
    {
        for ( int i = 0; i < MAX_SIZE; i++ )
        {
            postRequest();
        }
    }

    private ImageEnty getEnty ()
    {
        ImageEnty imageEnty = null;

        while ( !getImgLinkedList().isEmpty() )
        {
            imageEnty = getImgLinkedList().poll();
            if ( !TextUtils.isEmpty(imageEnty.getImgPath()))
            {
                imageEnty.setListener(listener);
                return imageEnty;
            }
        }
        if ( callBack != null && getTaskNum() == 0 )
        {
            callBack.handler(null);
        }

        return null;
    }

    synchronized private void setAtLast ( ImageEnty imageEnty )
    {
        setTaskNum(getTaskNum()-1);
        getImgLinkedList().addLast(imageEnty);
        postRequest();
    }

    synchronized private void removeEnty ( ImageEnty imageEnty )
    {
        setTaskNum(getTaskNum()-1);
//        Common.log1("removeEnty taskNum="+getTaskNum());
        getImgLinkedList().remove(imageEnty);
        postRequest();
    }

    synchronized private void postRequest ()
    {
        ImageEnty enty = getEnty();
        if ( enty != null ) {
            setTaskNum(getTaskNum()+1);
            mThreadPoolExecutor.execute(new UpdataImageRunnable(enty, userId));
        }
    }


    EntyListener listener = new EntyListener() {
        @Override
        public void sendOK(ImageEnty imageEnty) {
            super.sendOK(imageEnty);
//            Common.log1("listener taskNum="+getTaskNum());
            removeEnty(imageEnty);
        }

        @Override
        public void sendError(ImageEnty imageEnty) {
            super.sendError(imageEnty);
            setAtLast(imageEnty);
        }

        @Override
        public void start(ImageEnty imageEnty) {
            super.start(imageEnty);

        }
    };




}

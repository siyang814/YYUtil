package com.example.list.yyutil.util.internet;

/**
 * Created by yy on 2018/6/11.
 */

public abstract class CallBackBase {

    abstract public void handler(MyTask mTask);

    abstract public void handlerError (MyTask mTask);

    abstract public void handlerCancel (MyTask mTask);
}


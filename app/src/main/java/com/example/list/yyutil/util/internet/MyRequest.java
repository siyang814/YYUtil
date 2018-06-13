package com.example.list.yyutil.util.internet;

import android.os.AsyncTask;
import android.os.Build;

import com.example.list.yyutil.util.Common;
import com.example.list.yyutil.util.ParserUtil;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ConcurrentModificationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyRequest{

	private MyTask mTask;
//	private com.lis99.mobile.util.MyHttpClient http;
	private OkHttpUtil okhttp;
	private static ExecutorService myExecutor;

	private TimeOutTest timeTest;

//	private RequestHeadInterface interceptor;

	static{
		//线程池， 线程数量3
		myExecutor = Executors.newFixedThreadPool(3);
	}
	
	
	public MyRequest ( MyTask task )
	{
		mTask = task;
		okhttp = OkHttpUtil.getInstance();
		RequestTaskManager.getInstance().addTask(mTask);
//		http = new MyHttpClient();
//		okhttp = new OkHttpUtil();
	}
	private boolean isshow;
	synchronized public void start ()
	{
		if (!InternetUtil.checkNetWorkStatus(mTask.getActivity()) ) {
			if ( !isshow )
			{
				isshow = true;
//				com.lis99.mobile.util.DialogManager.getInstance().alert(mTask.getActivity(), "网络好像有问题");
			}
			return;
		}
		isshow = false;
		MyAsync myAsync = new MyAsync();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			myAsync.executeOnExecutor(myExecutor, "");
		}
//		new MyAsync().execute("");
	}


	public class MyAsync extends AsyncTask
	{
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

//			上一次请求， 直接return 出去
			if ( mTask.isDead() )
			{
//				Common.log1("=================DDDDDDDD================="+mTask.getUrl());
				mTask = null;
				return;
			}
//			移除请求任务
			RequestTaskManager.getInstance().removeTask(mTask);

			if ( mTask.isShowDialog())
			{
//				com.lis99.mobile.util.DialogManager.getInstance().stopWaitting();
			}

			if ( result == null )
			{
//				Common.toast("拉取失败");
//				Common.log("result==null");
//				错误返回
				if ( mTask!=null /*&& mTask.getCallBack() != null*/ )
					mTask.handlerError(mTask);
//				mTask.getCallBack().handlerError(mTask);
				return;
			}

			String res = result.toString();
			if ( result != null && mTask != null )
			{
				//jason 数据
				mTask.setresult(res);
//				jason解析类
				if ( mTask.getResultModel() != null )
				{
					mTask.setResultModel(ParserUtil.getParserResult(res, mTask.getResultModel(), mTask));
				}
				//没有生成解析类, 解析错误不回掉（调用错误返回）
				if (mTask.getResultModel() == null && !mTask.isErrorCallBack() )
				{
					//以下代码用于处理正常返回时，但数据为空的情况
//					if (mTask.getCallBack()!=null) {
						try {
							mTask.handlerError(mTask);
						} catch (NullPointerException e) {
//							Common.Log_i("MyRequest catched error:"+e.getMessage());
						}
//					}
					return;
				}

					try {
						mTask.handler(mTask);
					} catch (NullPointerException e) {
//						Common.Log_i("MyRequest catched error:"+e.getMessage());
					}

			}
			
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if ( mTask.isShowDialog())
			{
				try{
//					com.lis99.mobile.util.DialogManager.getInstance().startWaiting(LSBaseActivity.activity, null, "数据加载中...");
				}
				catch (Exception e)
				{

				}
			}
		}

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
//			得到请求的时间
			timeTest = new TimeOutTest(mTask.getUrl());
//			String requestInfo = mTask.getMap() == null ? "" : mTask.getMap().toString();

			String result = null;

			switch ( mTask.getRequestState() )
			{
			case MyTask.POST:
//				result = http.HttpPost(mTask.getUrl(), mTask.getMap());
				try {
					result = okhttp.post(mTask.getUrl(), mTask.getMap());
				}
				catch ( ConnectException e )
				{
					MyException myException = new MyException(e.getMessage(), MyException.TIMEOUT);
					mTask.setMyException(myException);
				}
				catch (ConnectTimeoutException e)
				{
					// 捕获ConnectionTimeout
					MyException myException = new MyException(e.getMessage(), MyException.TIMEOUT);
					mTask.setMyException(myException);
				}
				catch (SocketTimeoutException e)
				{
					// 捕获SocketTimeout
					MyException myException = new MyException(e.getMessage(), MyException.TIMEOUT);
					mTask.setMyException(myException);
				}
				catch (IOException e) {
					e.printStackTrace();
					MyException myException = new MyException(e.getMessage(), MyException.TIMEOUT);
					mTask.setMyException(myException);
				}
				break;
			case MyTask.GET:
//				result = http.HttpGet(mTask.getUrl());
				try {
					result = okhttp.get(mTask.getUrl());
				}
				catch ( ConnectException e )
				{
					MyException myException = new MyException(e.getMessage(), MyException.TIMEOUT);
					mTask.setMyException(myException);
				}
				catch (ConnectTimeoutException e)
				{
					// 捕获ConnectionTimeout
					MyException myException = new MyException(e.getMessage(), MyException.TIMEOUT);
					mTask.setMyException(myException);
				}
				catch (SocketTimeoutException e)
				{
					// 捕获SocketTimeout
					MyException myException = new MyException(e.getMessage(), MyException.TIMEOUT);
					mTask.setMyException(myException);
				}
				catch (IOException e) {
					e.printStackTrace();
					MyException myException = new MyException(e.getMessage(), MyException.TIMEOUT);
					mTask.setMyException(myException);
				}
				break;
			case MyTask.IMAGE:
//				result = http.HttpImage(mTask.getUrl(), mTask.getMap());
				try {
					result =okhttp.postImage(mTask.getUrl(), mTask.getMap());
				}
				catch ( ConnectException e )
				{
					MyException myException = new MyException(e.getMessage(), MyException.TIMEOUT);
					mTask.setMyException(myException);
				}
				catch (ConnectTimeoutException e)
				{
					// 捕获ConnectionTimeout
					MyException myException = new MyException(e.getMessage(), MyException.TIMEOUT);
					mTask.setMyException(myException);
				}
				catch (SocketTimeoutException e)
				{
					// 捕获SocketTimeout
					MyException myException = new MyException(e.getMessage(), MyException.TIMEOUT);
					mTask.setMyException(myException);
				}
				catch (IOException e) {
					e.printStackTrace();
					MyException myException = new MyException(e.getMessage(), MyException.TIMEOUT);
					mTask.setMyException(myException);
				}
				break;
			}

			Common.log("RequestHttpURL="+mTask.getUrl());

			try
			{
				if ( mTask.getMap() != null )
					Common.log("RequestInfo="+mTask.getMap().toString());
			}
			catch (ConcurrentModificationException e)
			{
				Common.log(e.getMessage());
			}

			Common.log("Httpresult="+result);

//			Common.log("Httpresult="+Common.convert(result));
//			统计请求时间
			timeTest.callBack();

			return result;
		}
	}
	
	
	

}

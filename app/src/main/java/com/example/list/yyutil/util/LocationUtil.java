package com.example.list.yyutil.util;


import android.app.Activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

/***
 * 定位功能
 * 
 * @author yy
 * 
 */
public class LocationUtil
{
	private static LocationUtil instance;

	private Activity activity;


	public static LocationUtil getinstance()
	{
		if (instance == null)
			instance = new LocationUtil();
		return instance;
	}

	// 定位相关
	private LocationClient mLocClient;
	private MyLocationListenner myListener = new MyLocationListenner();

	public void getLocation(Activity activity)
	{
		this.activity = activity;
		// 定位初始化
		mLocClient = new LocationClient(activity);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);

		option.setAddrType("all");
		option.setCoorType("bd09ll"); // 设置坐标类型
//		option.setScanSpan(900);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener
	{

		@Override
		public void onReceiveLocation(BDLocation location)
		{
			// map view 销毁后不在处理新接收的位置
			if (location == null){
				Common.toast(activity, "定位失败");
				stopLocation();

				if ( glocation != null )
				{
					glocation.Location(0, 0, "");
				}

				return;
			}
//			Common.log("getLatitude()=="+location.getLatitude()+"=location.getLongitude()=="+location.getLongitude());
			if ( glocation != null )
			{
				if (location.getLatitude()==4.9E-324D||location.getLongitude()==4.9E-324D) {
					glocation.Location(0, 0, "");
				}else {
					glocation.Location(location.getLatitude(), location.getLongitude(), location.getCity());

				}
			}
			stopLocation();
		}

		public void onReceivePoi(BDLocation poiLocation)
		{
		}
	}

	public void stopLocation()
	{
		if ( mLocClient != null && mLocClient.isStarted() )
		{
			mLocClient.stop();
		}
		setGlocation(null);
		activity = null;
	}
	
	private getLocation glocation;
	
	

	public getLocation getGlocation()
	{
		return glocation;
	}



	public void setGlocation(getLocation glocation)
	{
		this.glocation = glocation;
	}

	public interface getLocation
	{
		public void Location ( double latitude, double longitude, String cityName );
	}
//
	public LatLng gaode2baidu(LatLng sourceLatLng )
	{
		// 将google地图、soso地图、aliyun地图、mapabc地图和amap地图// 所用坐标转换成百度坐标
		CoordinateConverter converter  = new CoordinateConverter();
		converter.from(CoordinateConverter.CoordType.COMMON);
		// sourceLatLng待转换坐标
		converter.coord(sourceLatLng);
		LatLng desLatLng = converter.convert();
		return desLatLng;

	}
	
}

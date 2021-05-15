package com.o.baidusdkdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends AppCompatActivity {
    //定义一个地图组件
    private TextView                             text; //定义用于显示LocationProvider的TextView组件
    private MapView                              mMapView;
    private boolean                              isFirstLoc = true;//记录是否是第一次定位
    private BaiduMap                             mBaiduMap;         //定义百度地图对象
    private MyLocationConfiguration.LocationMode LocationMode; //设置当前定位模式
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //放在setContentView方法之前
        SDKInitializer.initialize(getApplicationContext());//初始化地图SDK
        setContentView(R.layout.activity_main);

        //必须放到setContentView之后
        mMapView = findViewById(R.id.bmapview);//获取地图组件
        mBaiduMap = mMapView.getMap(); //获取百度地图对象

        text = findViewById(R.id.provider);
        //获取系统的LocationManager 对象
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                locationManager.GPS_PROVIDER,//指定GPS定位的提供者
                1000,//间隔时间
                1,//位置间隔一米
                new LocationListener() {//监听GPS定位信息是否改变
                    @Override
                    public void onLocationChanged(@NonNull Location location) {

                    }

                    @Override//GPS状态改变
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        if (checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                        locationUpdates(location);
                    }
                    @Override//启动
                    public void onProviderEnabled(@NonNull String provider) {

                    }

                    @Override//关闭
                    public void onProviderDisabled(@NonNull String provider) {

                    }
                }
        );
    }
    public void locationUpdates(Location location)
    {
        if(location!=null)
        {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("您的位置是：\n");
            stringBuilder.append("经度：");
            stringBuilder.append(location.getLongitude());
            stringBuilder.append("\n 纬度：");
            stringBuilder.append(location.getLatitude());
            text.setText(stringBuilder.toString());//显示到界面
            Log.i("111","111");
            LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());

            if(isFirstLoc)
            {
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);//更新坐标位置
                mBaiduMap.animateMapStatus(u);//设置地图位置
                isFirstLoc=false;//取消第一次定位
            }
            //构造定位数据
            MyLocationData locData =new  MyLocationData.Builder().accuracy(location.getAccuracy())
                    .direction(100)//设置方向信息
                    //设置纬度坐标
            .latitude(location.getLatitude())
                    //设置经度坐标
            .latitude(location.getLongitude()).build();

            mBaiduMap.setMyLocationData(locData);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                    .fromResource(
                    R.drawable.ic_launcher_foreground
                    //设置图标
            );

            //设置定位模式
            LocationMode=MyLocationConfiguration.LocationMode
                    .NORMAL;//

            //设置构造方式
            MyLocationConfiguration config = new MyLocationConfiguration(LocationMode,true,bitmapDescriptor);

            //显示定位图标

            mBaiduMap.setMyLocationConfiguration(config);


        }else{
            text.setText("没有获取到GPS信息！");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mBaiduMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mMapView=null;
    }

}
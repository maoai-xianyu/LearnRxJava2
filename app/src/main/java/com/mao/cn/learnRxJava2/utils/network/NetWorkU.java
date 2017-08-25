package com.mao.cn.learnRxJava2.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.mao.cn.learnRxJava2.LearnRxJava2Application;
import com.mao.cn.learnRxJava2.utils.tools.LogU;


/**
 * author:  zhangkun .
 * date:    on 2017/8/3.
 */

public class NetWorkU {

    /**
     * 判断是否链接网络
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) LearnRxJava2Application.context().getSystemService(Context.CONNECTIVITY_SERVICE);
        //新版本调用方法获取网络状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                LogU.i("===状态===" + networkInfo.getState());
                LogU.i("===类型===" + networkInfo.getTypeName());
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            //否则调用旧版本方法
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            LogU.i("NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * 判断是否有网
     *
     * @return
     */
    public static boolean isNetworkAvailableOld() {
        ConnectivityManager connectivity = (ConnectivityManager) LearnRxJava2Application.context().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            LogU.e("无法获得ConnectivityManager");
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.isAvailable()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return
     */
    public static boolean checkNetState() {
        boolean netstate = false;
        ConnectivityManager connectivity = (ConnectivityManager) LearnRxJava2Application.context().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        netstate = true;
                        break;
                    }
                }
            }
        }
        return netstate;
    }

    /**
     * 判断设备当前是否在网络上漫游
     *
     * @return
     */
    public static boolean isNetworkRoaming() {
        ConnectivityManager connectivity = (ConnectivityManager) LearnRxJava2Application.context().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            LogU.e("无法获得ConnectivityManager");
        } else {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                TelephonyManager tm = (TelephonyManager) LearnRxJava2Application.context().getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null && tm.isNetworkRoaming()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return
     * @throws Exception
     */
    public static boolean isMobileDataEnable() throws Exception {
        ConnectivityManager connectivityManager = (ConnectivityManager) LearnRxJava2Application.context().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
    }


    /**
     * @return
     * @throws Exception
     */
    public static boolean isWifiDataEnable() throws Exception {
        ConnectivityManager connectivityManager = (ConnectivityManager) LearnRxJava2Application.context().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
    }


    /**
     * 判断WIFI网络是否可用
     *
     * @return
     */
    public boolean isWifiConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) LearnRxJava2Application.context().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWiFiNetworkInfo != null && mWiFiNetworkInfo.isAvailable();
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     */
    public boolean isMobileConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) LearnRxJava2Application.context()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mMobileNetworkInfo != null && mMobileNetworkInfo.isAvailable();
    }
}

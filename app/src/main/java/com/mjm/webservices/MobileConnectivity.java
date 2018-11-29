package com.mjm.webservices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MobileConnectivity {

	public static boolean checkInternet(Context ctx) {
		ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		// Check if wifi or mobile network is available or not. If any of them is
		// available or connected then it will return true, otherwise false;
		return wifi.isConnected() || mobile.isConnected();
	}

	public static boolean checkWiFi(Context ctx) {
		ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifi.isConnected();
	}

	public static boolean checkMobileData(Context ctx) {
		ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return mobile.isConnected();
	}

}

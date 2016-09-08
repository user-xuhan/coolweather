package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpUtil {

	public static void sendHttprequest(final String address, final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					Log.d("url", address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					
					InputStream in = connection.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
//					Log.d("line", bufferedReader.readLine());
					while ((line = bufferedReader.readLine())!= null) {
						response.append(line);
						Log.d("line", line);
					}
					
					if(listener != null){

						listener.onFinish(response.toString());
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					if(listener != null){
						Log.d("error", "sssss");
						listener.onError(e);
					}
				}finally{
					if(connection != null){
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}

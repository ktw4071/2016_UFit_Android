package kr.co.team.LKLH.ufit;

import android.util.Log;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ccei on 2016-08-03.
 */

public class UFitHttpURLConnectionManager {
    public static HttpURLConnection getHttpURLConnection(String targetURL, String reqMethod){
        HttpURLConnection httpConnection = null;
        switch(reqMethod){
            case "GET" :
                try{
                URL url = new URL(targetURL);

                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setDoInput(true);
                httpConnection.setConnectTimeout(15000);

                }catch(Exception e){
                    Log.e("겟매니저실패", "getHttpURLConnection() fail! ", e);
                }
                return httpConnection;
            case "POST":
                try{

                    URL url = new URL(targetURL);

                    httpConnection = (HttpURLConnection)url.openConnection();
//                    httpConnection.setReadTimeout(10000);
                    httpConnection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                    httpConnection.setRequestProperty( "charset", "utf-8");
                    httpConnection.setConnectTimeout(15000);
                    httpConnection.setRequestMethod("POST");
                    httpConnection.setDoInput(true);
                    httpConnection.setDoOutput(true);
                    httpConnection.setUseCaches(false);

                }catch(Exception e){
                    Log.e("포스트매니저실패", "getHttpURLConnection() fail! ", e);
                }
                return httpConnection;

            case "DELETE":
                try{

                    URL url = new URL(targetURL);

                    httpConnection = (HttpURLConnection)url.openConnection();
//                    httpConnection.setReadTimeout(10000);
                    httpConnection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                    httpConnection.setRequestProperty( "charset", "utf-8");
                    httpConnection.setConnectTimeout(15000);
                    httpConnection.setRequestMethod("DELETE");
                    httpConnection.setDoInput(true);
                    httpConnection.setDoOutput(true);
                    httpConnection.setUseCaches(false);

                }catch(Exception e){
                    Log.e("딜리트매니저실패", "getHttpURLConnection() fail! ", e);
                }
                return httpConnection;
            default:
                return httpConnection;
        }

    }

    public  static void setDismissConnection(HttpURLConnection returnedConn, Reader inR, Writer outW){

        if( inR != null){
            try{
                inR.close();
            }catch(IOException ioe){

            }
        }
        if( outW != null){
            try{
                outW.close();
            }catch(IOException ioe){

            }
        }
        if( returnedConn != null){

            returnedConn.disconnect();
        }
    }

}
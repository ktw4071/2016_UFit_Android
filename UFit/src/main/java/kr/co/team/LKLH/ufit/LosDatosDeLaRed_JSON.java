package kr.co.team.LKLH.ufit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.util.Log.e;
import static android.util.Log.i;

/**
 * Created by Admin on 2016-08-08.
 */
public class LosDatosDeLaRed_JSON {
    private final String TAG = "LosDatosDeLaRed_JSON";
    final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    JSONArray jsonArray;
    JSONObject jsonObject;

    OkHttpClient client = new OkHttpClient();
    Request request;
    Response response;
    RequestBody body;
    ArrayList<UFitEntityObject> items;
    final MediaType IMAGE_MIME_TYPE = MediaType.parse("image/*");

    public ArrayList<UFitEntityObject> LosDatosDeLaRed_GET_JSON(String url,String jsonTag, int code) {
        items = new ArrayList<>();

        request = new Request.Builder()
                             .url(url)
                             .build();

        try {
            response = client.newCall(request).execute();
            jsonObject = new JSONObject(response.body().string());
            jsonArray  = jsonObject.getJSONArray(jsonTag);
            for (int i = 0 ; i < jsonArray.length() ; i++) {
                items.add(new UFitEntityObject(jsonArray.getJSONObject(i), code));
            }
            return items;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        } finally {
            if(response != null) response.close();
        }
    }
    public ArrayList<UFitEntityObject> LosDatosDeLaRed_POST_JSON(JSONObject[] postItem, String url, int code, int putORpost) {
        items = new ArrayList<>();

        body = RequestBody.create(JSON, postItem[0].toString());
        switch (putORpost) {
            case 0 : {
                request = new Request.Builder()
                        .url(url)
                        .put(body)
                        .build();
                break;
            }case 1 : {
                request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                break;
            }
        }


        try {
            response = client.newCall(request).execute();
            items.add(new UFitEntityObject(postItem[0], code));
            return items;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        } finally {
            if(response != null) response.close();
        }
    }
    public String LosDatosDeLaRed_DELETE_JSON(String url, JSONObject[] deleteItem) {
        items = new ArrayList<>();
        body = RequestBody.create(JSON, deleteItem[0].toString());
        Log.e(TAG, deleteItem[0].toString());
        request = new Request.Builder()
                             .url(url)
                             .delete(body)
                             .build();
        try {
            response = client.newCall(request).execute();
            boolean flag = response.isSuccessful();
            //응답 코드 200등등
            int responseCode = response.code();
            Log.e(TAG, responseCode+"");
            if (flag) {
                Log.e("response결과", responseCode + "---" + response.message()); //읃답에 대한 메세지(OK)
                Log.e("response응답바디", response.body().string()); //json으로 변신
                return "success";
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            if(response != null) response.close();
        }
        return "fail";
    }
    public String LosDatosDeLaRed_MPFD_JSON(int typecode, String url, ArrayList<UFitImageUploadHelper.UpLoadValueObject>[] items) {
        client = new OkHttpClient.Builder()
                                 .connectTimeout(30, TimeUnit.SECONDS)
                                 .readTimeout(15, TimeUnit.SECONDS)
                                 .build();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (int i = 0; i < items[0].size(); i++) {
            File file = items[0].get(i).file;
            builder.addFormDataPart("_image", file.getName(), RequestBody.create(IMAGE_MIME_TYPE, file));
        }

        body = builder.build();
        switch (typecode) {
            case 0 : {
                request = new Request.Builder()
                        .url(url)
                        .put(body)
                        .build();
                break;
            }case 1 : {
                request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                break;
            }
        }

        try {
            Log.e(TAG, "찍혀라 얍");
            response = client.newCall(request).execute();
            Log.e(TAG, "찍혀라 얍");
            boolean flag = response.isSuccessful();
            //응답 코드 200등등
            int responseCode = response.code();
            Log.e(TAG, responseCode+"");
            if (flag) {
                Log.e("response결과", responseCode + "---" + response.message()); //읃답에 대한 메세지(OK)
                Log.e("response응답바디", response.body().string()); //json으로 변신
                return "success";
            }

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            if(response != null) response.close();
        }
        return "fail";
    }

}

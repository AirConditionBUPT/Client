/*
    网络传输工具类
 */
package Utils;

import com.google.gson.JsonObject;
import okhttp3.*;

public class WebUtils {

    //建立一个OKHTTP GET用于进行网络请求
    public static void sendOkHttpGetRequest(String address, Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //OKHTTP POST用于进行网络请求
    public static void sendOkHttpPostRequest(String address, JsonObject postJson, Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSONType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSONType,postJson.toString());
        Request request = new Request.Builder().url(address).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}

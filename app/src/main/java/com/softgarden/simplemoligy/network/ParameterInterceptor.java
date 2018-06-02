package com.softgarden.simplemoligy.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.softgarden.baselibrary.utils.L;
import com.softgarden.baselibrary.utils.MD5Util;
import com.softgarden.simplemoligy.config.HostUrl;
import com.softgarden.simplemoligy.utils.SP;
import com.softgarden.simplemoligy.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


/**
 * Created by Administrator on 2016/10/10.
 * 参数格式为Json格式的拦截器
 */

public class ParameterInterceptor implements Interceptor {
    private static final String TAG = ParameterInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        RequestBody requestBody = makeRequestBody(oldRequest);
        Request newRequest = oldRequest.newBuilder().post(requestBody).build();
        Response response = chain.proceed(newRequest);
        //打印返回数据
        //if (BuildConfig.DEBUG) {
            ResponseBody resultBody = response.body();
            String result = resultBody.string();
            try {
                JSONObject jsonObject = new JSONObject(result);
                int status = jsonObject.getInt("status");
                String info = jsonObject.getString("info");
                String data = jsonObject.getString("data");
                if (status != 1 && !TextUtils.isEmpty(info)) {
                    L.e(info);
                }
                if (result.contains("[]")) {
                    result = StringUtil.replace("[]", "null", result);
                }
                L.json(result);
            } catch (Exception e) {
                L.e(result);
            }

         /*   * 因为调用ResponseBody.string()后即关闭，后续无法获取内容 */
            response = response.newBuilder()
                    .body(ResponseBody.create(resultBody.contentType(), result))
                    .build();
        //}
        return response;
    }

    @NonNull
    private RequestBody makeRequestBody(Request oldRequest) {
        HttpUrl oldUrl = oldRequest.url();
        String oldPath = oldUrl.url().getPath();
        FormBody.Builder newBodyBuilder = new FormBody.Builder();
        JSONObject params = new JSONObject();
        try {

            /** 链接上的参数 */
//        for (int i = oldUrl.querySize() - 1; i >= 0; i--) {
//            String name = oldUrl.queryParameterName(i);
//            String value = oldUrl.queryParameterValue(i);
//            dataStr.append(String.format("\"%s\":\"%s\",", name, value));
//        }

            /** Body上的参数 */
            //这里要判断下  不然参数为空时会classCastException
            if (oldRequest.body() instanceof FormBody) {
                FormBody body = (FormBody) oldRequest.body();
                if (body != null)
                    for (int i = body.size() - 1; i >= 0; i--) {
                        String name = body.name(i);
                        String value = body.value(i);
                        params.put(name, value);
                    }
            } else if (oldRequest.body() instanceof MultipartBody) {
                /*** 当参数以 @MultipartBody 提交时 */
                L.d(TAG, "instanceof MultipartBody");

            } else {/*** 当参数以 @Body 提交时 */
                String bodyString = bodyToString(oldRequest.body());
                if (!TextUtils.isEmpty(bodyString)) {
                    params = new JSONObject(bodyString);
                    L.d("bodyToString---", bodyString);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringBuffer signSb = new StringBuffer();
        String userID = SP.getUserID();
        String token = SP.getToken();
        if (!TextUtils.isEmpty(userID)&&!TextUtils.isEmpty(token)) {
            signSb.append(HostUrl.HOST_URL);
            signSb.append(oldPath+"?");

            for (int i = oldUrl.querySize() - 1; i >= 0; i--) {
                String name = oldUrl.queryParameterName(i);
                String value = oldUrl.queryParameterValue(i);
                signSb.append(name + "=" + value + "&");
            }

            signSb.append("userId=" + userID + "&token=" + token);
        }

        /** * 添加Sign参数 */
        String paramsStrS = params.toString().replaceAll("\\\\s*", "\\\\");
        String paramsStrT = paramsStrS.replaceAll("\\\\t", "\\\\\\\\");
        String paramsStrR = paramsStrT.replaceAll("\\\\r", "\\\\\\\\");
        String paramsStrN = paramsStrR.replaceAll("\\\\n", "\\\\\\\\");
        String paramsStr = paramsStrN.replaceAll("\\\\", "");
        //String paramsStr = replaceBlank(params.toString().trim());
        newBodyBuilder.add("param", paramsStr);
        newBodyBuilder.add("sign", MD5Util.encodeByMD5(signSb.toString().toLowerCase()));
        newBodyBuilder.add("userId", SP.getUserID());
        newBodyBuilder.add("orgin", "103");
        L.d("请求地址RequestUrl=====", oldUrl.url().toString());
        L.d("请求参数Params=========", params.toString());//打印请求log
        L.json(paramsStr);
        return newBodyBuilder.build();
    }


    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\\\|\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    /**
     * body 中的内容
     *
     * @param request
     * @return
     */
    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}

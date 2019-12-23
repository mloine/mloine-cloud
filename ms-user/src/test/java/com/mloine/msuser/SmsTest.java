package com.mloine.msuser;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SmsTest {

    public static final String SIGN = "";
    public static final String TEMPLATE_CODE = "";
    public static final String ACCESSKEYID = "";
    public static final String ACCESSKEYSECRET = "";
    public static final String PHONE = "";

    public static void main(String[] args) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESSKEYID, ACCESSKEYSECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        //手机号
        request.putQueryParameter("PhoneNumbers", PHONE);
        //签名
        request.putQueryParameter("SignName", SIGN);
        //模版号
        request.putQueryParameter("TemplateCode", TEMPLATE_CODE);

        //参数
        request.putQueryParameter("TemplateParam", "{\"code\":\"XYK6\"}");


        CommonResponse response = client.getCommonResponse(request);
        System.out.println(response.getData());

    }
}

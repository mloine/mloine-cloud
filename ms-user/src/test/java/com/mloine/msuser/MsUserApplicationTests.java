package com.mloine.msuser;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

//@SpringBootTest
class MsUserApplicationTests {

    @Test
    void contextLoads() throws FileNotFoundException {
        compare();
    }

    public static void main(String[] args) throws Exception {
        //初始需要处理的 语句
        String sqlAll = readFileToString("E:\\薛勇康工作空间\\普利司通\\TEST_drupal_7_bridgestone_Dump.201912191431.sql");
//        String sqlAll = readFileToString("E:\\薛勇康工作空间\\普利司通\\init.sql");
        //准备替换 映射组
        HashMap<String, String> init = initMap("E:\\薛勇康工作空间\\普利司通\\init.sql");

        init.keySet().stream().forEach((key) -> {
            sqlAll.replace(key,init.get(key));
        });

        FileOutputStream fileOutputStream = new FileOutputStream(new File("E:\\薛勇康工作空间\\普利司通\\TEST_drupal_7_bridgestone_Dump.201912191431-after.sql"));
        fileOutputStream.write(sqlAll.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();

        System.out.println(sqlAll);
    }
    /**
     *  @Author: XueYongKang
     *  @Description:    比对文件对应url 映射是否冲突
     *  @Data: 2019/12/20 20:48
     */
    public static void compare(){
        HashMap<String, String> init = initMap("E:\\薛勇康工作空间\\普利司通\\2019-12-20\\init.sql");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        HashMap<String, String> initAfter = initMap("E:\\薛勇康工作空间\\普利司通\\2019-12-20\\init-after.sql");

        //1.获取文件对应映射总数
        Set<String> initKeySet = init.keySet();
        System.out.println("之前文件总数:"+ initKeySet.size());
        System.out.println("之后文件总数:"+initAfter.keySet().size());

        HashMap<String, String> errorMap = new HashMap<>();
        //2.比对找出重复的key 不对应url映射的 键值对
        System.out.println("-------------------------------------------合成两部分映射关系-------------------------------------------------------------------------------");

        initKeySet.stream().forEach(key -> {
            String initValue = init.get(key);
            String initAfterValue = initAfter.get(key);
            if(!StringUtils.isEmpty(initAfterValue) &&
                    !StringUtils.isEmpty(initAfterValue)  &&
                    !initValue.equals(initAfterValue)){
                //不符合要求的数据
                errorMap.put(key,initValue +"|"+initAfterValue);
                System.out.println("***被替换部分:" +key + "     替换冲突:first:" + initValue +" | second:"+initAfterValue);
            }else{
                System.out.println("被替换部分:" +key + "     替换为:" + initValue);
            }
        });
        initAfter.keySet().stream().forEach(key -> {
            if(!initKeySet.contains(key)){
                System.out.println("被替换部分:" +key + "     替换为:" + initAfter.get(key));
            }

        });

        //3.输出结果
        System.out.println("------------------------------end--------------------------------------------");
        errorMap.keySet().forEach(key-> System.out.println("key:"+key+"   value:" +errorMap.get(key)));

    }

    public static String readFileToString(String path) {
        // 定义返回结果
        StringBuffer jsonString = new StringBuffer();

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));// 读取文件
            String thisLine = null;
            while ((thisLine = in.readLine()) != null) {
//                jsonString += thisLine;
                jsonString.append(thisLine);
                jsonString.append("\n");
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException el) {
                }
            }
        }

        // 返回拼接好的JSON String
        return jsonString.toString();
    }


    public static HashMap<String, String> initMap(String path) {
        HashMap<String, String> initMap = new HashMap<>();
        // 定义返回结果
        StringBuffer jsonString = new StringBuffer();

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));// 读取文件
            String thisLine = null;
            while ((thisLine = in.readLine()) != null) {
//                jsonString += thisLine;
                jsonString.append(thisLine);
                System.out.println(thisLine);
                String[] split = thisLine.split(",");
                System.out.println(split[0] +"--" + split[1]);
                initMap.put(split[0],split[1]);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException el) {
                }
            }
        }

        // 返回拼接好的JSON String
        return initMap;
    }

}

package com.denworld.jwtdemo.utils;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ywang Email:denworld@gmail.com
 * @since 2023/03/08 15:16
 */
public class ResponseUtil {

    public static void responseJson(HttpServletResponse response, int status, Object data) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(status);
            //token数据返回
            response.getWriter().write(JSONUtil.toJsonStr(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}

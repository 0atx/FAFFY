package com.faffy.web.controller;

import lombok.Getter;
import org.json.simple.JSONObject;

@Getter
public class ResponseBody {
    JSONObject jsonObject;
    String msg;
    Object result;

    public ResponseBody(String msg, Object result) {
        this.jsonObject.put("msg",msg);
        this.jsonObject.put("result",result);
    }

}

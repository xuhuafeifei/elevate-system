package com.example.demo.entity;

import com.example.demo.utils.GsonUtils;
import lombok.Data;

/**
 * @author feigebuge
 * @email 2508020102@qq.com
 */
@Data
public class Response<T> {
    private T message;
    private String event;

    public Response(T message, String event) {
        this.message = message;
        this.event = event;
    }

    public String toJson() {
        return GsonUtils.toJson(this);
    }

    public static <T> String toJson(T message, String event) {
        Response<T> response = new Response<>(message, event);
        return response.toJson();
    }
}

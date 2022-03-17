package com.ccand99.swagger_demo.pojo;

import org.springframework.http.HttpStatus;

public class ResultV0<T> {
    private String msg;
    private T data;
    private Integer status;

    public ResultV0() {
    }

    public ResultV0(String msg, T data, Integer status) {
        this.msg = msg;
        this.data = data;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static <T> ResultV0<T> OK(T data) {
        return new ResultV0<T>(HttpStatus.OK.name(),data,HttpStatus.OK.value());
    }
}

package com.xh.basic.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * @author szq
 * @Package com.xh.basic.bean
 * @Description: Restful统一Json响应对象封装
 * @date 2018/5/29:27
 */
public class Resp<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final static String SUCCESS_CODE = "200";

    /**
     * 返回状态码
     */
    private String status;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回内容
     */
    private T data;

    /**
     * 分页信息
     */
    private PageInfo page;

    /**
     * 其他内容
     */
    private Map<String, Object> ext;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public Map<String, Object> getExt() {
        return ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    public Resp(){
        this.status = SUCCESS_CODE;
        this.message = "SUCCESS";
    }

    public Resp(String status, String message){
        this.status = status;
        this.message = message;
    }

    public Resp(String status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Resp(String status, String message, T data, Map<String, Object> ext){
        this.status = status;
        this.message = message;
        this.data = data;
        this.ext = ext;
    }

    public Resp(String status, String message, T data, PageInfo page){
        this.status = status;
        this.message = message;
        this.data = data;
        this.page = page;
    }

    public Resp(String status, String message, T data, Map<String, Object> ext, PageInfo page){
        this.status = status;
        this.message = message;
        this.data = data;
        this.ext = ext;
        this.page = page;
    }

    public Resp(String status, String message, T data, Long total, Integer pageNo, Integer pageSize){
        PageInfo pageInfo = new PageInfo(total, pageNo, pageSize);
        this.status = status;
        this.message = message;
        this.data = data;
        this.page = pageInfo;
    }

    public Resp(String status, String message, T data, Map<String, Object> ext, Long total, Integer pageNo, Integer pageSize){
        PageInfo pageInfo = new PageInfo(total, pageNo, pageSize);
        this.status = status;
        this.message = message;
        this.data = data;
        this.ext = ext;
        this.page = pageInfo;
    }

    //快速返回成功
    public <T>Resp success(){
        return new Resp<T>(SUCCESS_CODE, "请求成功", null);
    }

    public <T>Resp success(String message){
        return new Resp<T>(SUCCESS_CODE, message);
    }

    public <T>Resp success(T result){
        return new Resp<T>(SUCCESS_CODE, "请求成功", result);
    }

    public <T>Resp success(String message, T result){
        return new Resp<T>(SUCCESS_CODE, message, result);
    }

    public <T>Resp success(String message, T result, Map<String, Object> extra){
        return new Resp<T>(SUCCESS_CODE, message, result, extra);
    }

    public <T>Resp success(T result, Long total, Integer pageNo, Integer pageSize){
        PageInfo pageInfo = new PageInfo(total, pageNo, pageSize);
        return new Resp<T>(SUCCESS_CODE, "请求成功", result, pageInfo);
    }

    public <T>Resp success(String message, T result, Long total, Integer pageNo, Integer pageSize){
        PageInfo pageInfo = new PageInfo(total, pageNo, pageSize);
        return new Resp<T>(SUCCESS_CODE, message, result, pageInfo);
    }

    public <T>Resp success(String message, T result, Map<String, Object> extra, Long total, Integer pageNo, Integer pageSize){
        PageInfo pageInfo = new PageInfo(total, pageNo, pageSize);
        return new Resp<T>(SUCCESS_CODE, message, result, extra, pageInfo);
    }

    //快速返回失败状态
    public <T>Resp fail(){
        return new Resp<T>(ErrorCode.SYSTEM_ERROR.getCode(),ErrorCode.SYSTEM_ERROR.getMessage());
    }

    public <T>Resp fail(T result){
        return new Resp<T>(ErrorCode.SYSTEM_ERROR.getCode(),ErrorCode.SYSTEM_ERROR.getMessage(),result);
    }

    public <T>Resp fail(String message, T result){
        return new Resp<T>(ErrorCode.SYSTEM_ERROR.getCode(),message,result);
    }

    public <T>Resp fail(String message, T result, Map<String, Object> extra){
        return new Resp<T>(ErrorCode.SYSTEM_ERROR.getCode(),message,result, extra);
    }

    public <T>Resp fail(ErrorCode errorCode){
        return new Resp<T>(errorCode.getCode(),errorCode.getMessage());
    }

    public <T>Resp fail(ErrorCode errorCode, T result){
        return new Resp<T>(errorCode.getCode(),errorCode.getMessage(),result);
    }

    public <T>Resp fail(ErrorCode errorCode, String message, T result){
        return new Resp<T>(errorCode.getCode(),message,result);
    }

    public <T>Resp fail(ErrorCode errorCode, String message, T result, Map<String, Object> extra){
        return new Resp<T>(errorCode.getCode(),message,result, extra);
    }

    //快速返回自定义状态码
    public <T>Resp result(String statusCode, String message){
        return new Resp<T>(statusCode,message);
    }

    public <T>Resp result(String statusCode, String message, T result){
        return new Resp<T>(statusCode,message,result);
    }

    public <T>Resp result(String statusCode, String message, T result, Map<String, Object> extra){
        return new Resp<T>(statusCode,message,result, extra);
    }

    public <T>Resp result(String statusCode, String message, T result, Long total, Integer pageNo, Integer pageSize){
        PageInfo pageInfo = new PageInfo(total, pageNo, pageSize);
        return new Resp<T>(statusCode,message,result, pageInfo);
    }

    public <T>Resp result(String statusCode, String message, T result, Map<String, Object> extra, Long total, Integer pageNo, Integer pageSize){
        PageInfo pageInfo = new PageInfo(total, pageNo, pageSize);
        return new Resp<T>(statusCode,message,result, extra,pageInfo);
    }



}

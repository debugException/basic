package com.xh.basic.bean;

/**
 * @author szq
 * @Package com.xh.basic.bean
 * @Description: to do ...
 * @date 2018/4/1913:40
 */
public class ResponseBean {

    private Integer code;

    private String msg;

    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private ResponseBean rtnSuccess(Object obj){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(200);
        responseBean.setMsg("success");
        responseBean.setData(obj);
        return responseBean;
    }

    private ResponseBean rtnError(Integer code, String msg){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(code);
        responseBean.setMsg(msg);
        return responseBean;
    }
}

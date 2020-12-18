package com.rencc.common.response;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class Response<T> implements Serializable {

	private int code;// 此处本准备用枚举代替，但是jackson对枚举的序列化支持并不是特别好

	private String message;

	private T result;

	public Response() {
	    this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getMessage();
	}

	public Response(ResponseCode reponseCode) {
		this.code = reponseCode.getCode();
		this.message = reponseCode.getMessage();
	}

	public Response(ResponseCode reponseCode, String message) {
		this.code = reponseCode.getCode();
		this.message = message;
	}

	public Response(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public Response<T> success(T result) {
		this.code = ResponseCode.SUCCESS.getCode();
		this.message = ResponseCode.SUCCESS.getMessage();
		this.result = result;
		return this;
	}

	public Response<T> failed(int code, String message) {
		this.code = code;
		this.message = message;
		return this;
	}

	public Response<T> failed(int code, String message,T result) {
		this.code = code;
		this.message = message;
		this.result = result;
		return this;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public String toString() {
		String resultStr = (result == null ? "":JSON.toJSONString(result));
		if(resultStr.length() > 4000){
			resultStr = resultStr.substring(0,4000) + "....";
		}
		return "Response.len=" + resultStr.length() + ",,code=" + code + ",,message=" + message + ",,result=" + resultStr;
	}
}

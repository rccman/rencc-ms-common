package com.rencc.common.response;

public enum ResponseCode {
	
	SUCCESS(200,"OK"),
	CANNOT_REQUE_AGAIN(201,"已处理,不能重复请求"),//属于成功code
	ILLEGAL_PARAM(103,"不合法的请求参数"),
	ILLEGAL_METHOD(104,"不支持的请求方法"),
	ILLEGAL_BODY(105,"不合法的请求包体,需要json格式"),
	DATA_NOT_FOUND(106,"请求数据不存在"),
	BUSSINESS_EXCEPTION(107,"业务检查性错误"),
	FAIL_AFFECT_0(108,"操作失败,数据状态已发生变化"),
	FAIL_DATA_STATUS_CHANGED(108,"操作失败,数据状态已发生变化"),
	SQL_EXCEPTION(109,"SQL异常"),
	NOT_ALLOW_PARALLEL(110,"操作失败,数据不允许并行处理"),
	PARAMS_REQUESTID_LOST(111,"操作失败,请求唯一标识不存在"),
	MEDIATYPE_NOTSupported(112,"不支持的请求类型,需要application/json"),
	UNKOWN_EXCEPTION(500,"未知的服务器异常"),
	OUT_OF_LIMIT(501,"接口调用超过限制"),
	FREQUENT_OPERATION(502,"API调用太频繁，请稍候再试"),
	CALL_FAILS(504,"调用服务失败"),
	ILLEGAL_ACCESS(401,"不合法的请求"),
	ILLEGAL_URL(403,"不合法的域名/IP"),
	CONNECTION_ERR(404,"网络异常,请重试"),
	UNKNOWN(-1,"未知错误"),
	RESULT_ERROR(506,"接口返回业务数据异常");

	private int code;
	
	private String message;
	
	private ResponseCode(int code,String message){
		this.code=code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}
}

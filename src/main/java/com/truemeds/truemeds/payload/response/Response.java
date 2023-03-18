package com.truemeds.truemeds.payload.response;

public class Response {
	
	
	private int Status;
	private Object data;
	private String msg;
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "Response [Status=" + Status + ", data=" + data + ", msg=" + msg + "]";
	}
	
	
	
	
	

}

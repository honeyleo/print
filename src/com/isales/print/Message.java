package com.isales.print;

import com.alibaba.fastjson.JSONObject;

public class Message {

	private int ret;
	
	private String msg;
	
	private Object data;

	private Message(Builder builder) {
		this.ret = builder.ret;
		this.msg = builder.msg;
		this.data = builder.data;
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}
	
	public int getRet() {
		return ret;
	}

	public String getMsg() {
		return msg;
	}

	public Object getData() {
		return data;
	}

	public static class Builder {
		
		private int ret;
		
		private String msg;
		
		private JSONObject data;
		
		private Builder() {
			
		}
		
		public Builder setRet(int ret) {
			this.ret = ret;
			return this;
		}
		public Builder setMsg(String msg) {
			this.msg = msg;
			return this;
		}
		public Builder put(String key, Object value) {
			data.put(key, value);
			return this;
		}
		
		public Message build() {
			return new Message(this);
		}
	}
	
}

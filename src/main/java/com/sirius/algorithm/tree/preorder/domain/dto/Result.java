package com.sirius.algorithm.tree.preorder.domain.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by pippo on 14-10-12.
 */
public class Result {

	public static final Result SUCCESS = new Result();

	private boolean success = true;

	private int code = 200;

	private String msg;

	private Object payload;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("success", success)
				.append("code", code)
				.append("msg", msg)
				.append("payload", payload)
				.toString();
	}
}

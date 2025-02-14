package com.collections.genesys.response;

public class FailureResponse {
	private String status;
	private String msg;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "FailureResponse [status=" + status + ", msg=" + msg + "]";
	}

}

package com.ilts.anywhere.response.http;

import org.springframework.http.ResponseEntity;

public abstract class HttpResponse {
	
	private HttpResponseType responseType = null;
	private String HttpCode;
	private String HttpDefinition;
	private Object data;
	private String customMessage;
	public HttpResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(HttpResponseType responseType) {
		this.responseType = responseType;
	}

	public String getHttpCode() {
		return HttpCode;
	}

	public void setHttpCode(String httpCode) {
		HttpCode = httpCode;
	}

	public String getHttpDefinition() {
		return HttpDefinition;
	}

	public void setHttpDefinition(String httpDefinition) {
		HttpDefinition = httpDefinition;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

        public void setData(Object data,String cutsomMessage) {
		this.customMessage = data+": s"+cutsomMessage;
	}
	public HttpResponse(HttpResponseType responseType) {
		this.responseType = responseType;
	}

	protected abstract void construct();
	
	public ResponseEntity<Object> getResponseEntity() {
		return null;
	}

    /**
     * @return the customMessage
     */
    public String getCustomMessage() {
        return customMessage;
    }

    /**
     * @param customMessage the customMessage to set
     */
    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }
	
}

/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: Response.java
 */
package com.ilts.anywhere.response;

public abstract class Response {
    private ResponseType responseType = null;
    public String status;
    public String message;

    public Response(ResponseType responseType) {
        this.responseType = responseType;
        processResponse();
    }

    private void processResponse(){}

    protected abstract void construct();

    public String getStatus() {
        return this.status;
    }

    protected void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
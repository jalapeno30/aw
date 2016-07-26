/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: StatusJson.java
 */
package com.ilts.anywhere.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 *
 * @author ssanapureddy
 */
@JsonDeserialize
public class StatusJson {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("transId")
    private String transId;
    @JsonProperty("dateCreated")
    private String dateCreated;  

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

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
     * @return the transId
     */
    public String getTransId() {
        return transId;
    }

    /**
     * @param transId the transId to set
     */
    public void setTransId(String transId) {
        this.transId = transId;
    }

    /**
     * @return the dateModified
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateModified the dateModified to set
     */
    public void setDateCreated(String dateModified) {
        this.dateCreated = dateModified;
    }
    
}

/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: ExistingCardJSON.java
 */
package com.ilts.anywhere.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 *
 * @author ssanapureddy
 */
@JsonDeserialize
public class ExistingCardJSON {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("cardid")
    private String cardid;
    @JsonProperty("transId")
    private String transId;
    @JsonProperty("dateModified")
    private String dateModified;

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
     * @return the cardid
     */
    public String getCardid() {
        return cardid;
    }

    /**
     * @param cardid the cardid to set
     */
    public void setCardid(String cardid) {
        this.cardid = cardid;
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
    public String getDateModified() {
        return dateModified;
    }

    /**
     * @param dateModified the dateModified to set
     */
    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
}

/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: DummyCardDetails.java
 */
package com.ilts.anywhere.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ilts.anywhere.authentication.model.User;

/**
 *
 * @author ssanapureddy
 */
@JsonDeserialize
public class DummyCardDetails {
  
    private String cardDetailsUuid;
   @JsonProperty("cardFirstname")
    private String cardFirstname;
 @JsonProperty("cardLastname")
    private String cardLastname;
@JsonProperty("cardType")
    private String cardType;
@JsonProperty("cardNumber")
    private String cardNumber;
@JsonProperty("cardCvvNumber")
    private int cardCvvNumber;
@JsonProperty("cardStreetaddress")
    private String cardStreetaddress;
@JsonProperty("cardAptBdg")
    private String cardAptBdg;
@JsonProperty("cardCity")
    private String cardCity;
@JsonProperty("cardState")
    private String cardState;
  @JsonProperty("cardCountry")
    private String cardCountry;
  @JsonProperty("cardZipcode")
    private int cardZipcode;
@JsonProperty("cardPhoneNumber")
    private int cardPhoneNumber;
@JsonProperty("cardEmailId")
    private String cardEmailId;
 @JsonProperty("cardSave")
    private Boolean cardSave;
 
  
   @JsonProperty("transId")
    private String transId;
 
  @JsonProperty("userId")
    private User userId;
 
    private PaymentPurchases paymentpurchaseId;

    public DummyCardDetails() {
    }

    public DummyCardDetails(String cardDetailsUuid) {
        this.cardDetailsUuid = cardDetailsUuid;
    }

    public DummyCardDetails(String cardDetailsUuid, String cardFirstname, String cardLastname, String cardType, String cardNumber, int cardCvvNumber, String cardStreetaddress, String cardAptBdg, String cardCity, String cardState, String cardCountry, int cardZipcode, int cardPhoneNumber, String cardEmailId) {
        this.cardDetailsUuid = cardDetailsUuid;
        this.cardFirstname = cardFirstname;
        this.cardLastname = cardLastname;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardCvvNumber = cardCvvNumber;
        this.cardStreetaddress = cardStreetaddress;
        this.cardAptBdg = cardAptBdg;
        this.cardCity = cardCity;
        this.cardState = cardState;
        this.cardCountry = cardCountry;
        this.cardZipcode = cardZipcode;
        this.cardPhoneNumber = cardPhoneNumber;
        this.cardEmailId = cardEmailId;
    }

    public String getCardDetailsUuid() {
        return cardDetailsUuid;
    }

    public void setCardDetailsUuid(String cardDetailsUuid) {
        this.cardDetailsUuid = cardDetailsUuid;
    }

    public String getCardFirstname() {
        return cardFirstname;
    }

    public void setCardFirstname(String cardFirstname) {
        this.cardFirstname = cardFirstname;
    }

    public String getCardLastname() {
        return cardLastname;
    }

    public void setCardLastname(String cardLastname) {
        this.cardLastname = cardLastname;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardCvvNumber() {
        return cardCvvNumber;
    }

    public void setCardCvvNumber(int cardCvvNumber) {
        this.cardCvvNumber = cardCvvNumber;
    }

    public String getCardStreetaddress() {
        return cardStreetaddress;
    }

    public void setCardStreetaddress(String cardStreetaddress) {
        this.cardStreetaddress = cardStreetaddress;
    }

    public String getCardAptBdg() {
        return cardAptBdg;
    }

    public void setCardAptBdg(String cardAptBdg) {
        this.cardAptBdg = cardAptBdg;
    }

    public String getCardCity() {
        return cardCity;
    }

    public void setCardCity(String cardCity) {
        this.cardCity = cardCity;
    }

    public String getCardState() {
        return cardState;
    }

    public void setCardState(String cardState) {
        this.cardState = cardState;
    }

    public String getCardCountry() {
        return cardCountry;
    }

    public void setCardCountry(String cardCountry) {
        this.cardCountry = cardCountry;
    }

    public int getCardZipcode() {
        return cardZipcode;
    }

    public void setCardZipcode(int cardZipcode) {
        this.cardZipcode = cardZipcode;
    }

    public int getCardPhoneNumber() {
        return cardPhoneNumber;
    }

    public void setCardPhoneNumber(int cardPhoneNumber) {
        this.cardPhoneNumber = cardPhoneNumber;
    }

    public String getCardEmailId() {
        return cardEmailId;
    }

    public void setCardEmailId(String cardEmailId) {
        this.cardEmailId = cardEmailId;
    }

    public Boolean getCardSave() {
        return cardSave;
    }

    public void setCardSave(Boolean cardSave) {
        this.cardSave = cardSave;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public PaymentPurchases getPaymentpurchaseId() {
        return paymentpurchaseId;
    }

    public void setPaymentpurchaseId(PaymentPurchases paymentpurchaseId) {
        this.paymentpurchaseId = paymentpurchaseId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cardDetailsUuid != null ? cardDetailsUuid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DummyCardDetails)) {
            return false;
        }
        DummyCardDetails other = (DummyCardDetails) object;
        if ((this.cardDetailsUuid == null && other.cardDetailsUuid != null) || (this.cardDetailsUuid != null && !this.cardDetailsUuid.equals(other.cardDetailsUuid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "latestList.DummyCardDetails[ cardDetailsUuid=" + cardDetailsUuid + " ]";
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
    
}

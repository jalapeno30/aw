/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: PaymentPurchases.java
 */
package com.ilts.anywhere.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ilts.anywhere.authentication.model.User;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author ssanapureddy
 */
@Entity
@JsonDeserialize
@Table(name = "payment_purchases", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "PaymentPurchases.findAll", query = "SELECT p FROM PaymentPurchases p"),
//    @NamedQuery(name = "PaymentPurchases.findByPaymentpurchaseId", query = "SELECT p FROM PaymentPurchases p WHERE p.paymentpurchaseId = :paymentpurchaseId"),
//    @NamedQuery(name = "PaymentPurchases.findByCost", query = "SELECT p FROM PaymentPurchases p WHERE p.cost = :cost"),
//    @NamedQuery(name = "PaymentPurchases.findByDatePayment", query = "SELECT p FROM PaymentPurchases p WHERE p.datePayment = :datePayment"),
//    @NamedQuery(name = "PaymentPurchases.findByPaymentId", query = "SELECT p FROM PaymentPurchases p WHERE p.paymentId = :paymentId"),
//    @NamedQuery(name = "PaymentPurchases.findByConfirmId", query = "SELECT p FROM PaymentPurchases p WHERE p.confirmId = :confirmId"),
//    @NamedQuery(name = "PaymentPurchases.findByCancelled", query = "SELECT p FROM PaymentPurchases p WHERE p.cancelled = :cancelled")})
public class PaymentPurchases implements Serializable {
    private static final long serialVersionUID = 1L;
        @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "payment_purchase_Id", unique = true)

    private String paymentpurchaseId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cost")
    private Integer cost;
    @Transient
    @JsonProperty("date")
    private String date;
    @Column(name = "date_payment")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePayment;
    @Column(name = "payment_id")
    private String paymentId;
    @Column(name = "confirm_id")
    private String confirmId;
    @Column(name = "cancelled")
    private String cancelled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentpurchaseId")
    private List<PaymentPurchaseLinks> paymentPurchaseLinksList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentpurchaseId")
    private List<PaymentPurchaseOrders> paymentPurchaseOrdersList;
//    @OneToMany(mappedBy = "paymentpurchaseId")
//    private List<DummyCardDetails> dummyCardDetailsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentpurchaseId")
    private List<PaymentPurchaseStatus> paymentPurchaseStatusList;
    @JsonProperty("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;

    public PaymentPurchases() {
    }

    public PaymentPurchases(String paymentpurchaseId) {
        this.paymentpurchaseId = paymentpurchaseId;
    }

    public String getPaymentpurchaseId() {
        return paymentpurchaseId;
    }

    public void setPaymentpurchaseId(String paymentpurchaseId) {
        this.paymentpurchaseId = paymentpurchaseId;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Date getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(String confirmId) {
        this.confirmId = confirmId;
    }

    public String getCancelled() {
        return cancelled;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

    @XmlTransient
    public List<PaymentPurchaseLinks> getPaymentPurchaseLinksList() {
        return paymentPurchaseLinksList;
    }

    public void setPaymentPurchaseLinksList(List<PaymentPurchaseLinks> paymentPurchaseLinksList) {
        this.paymentPurchaseLinksList = paymentPurchaseLinksList;
    }

    @XmlTransient
    public List<PaymentPurchaseOrders> getPaymentPurchaseOrdersList() {
        return paymentPurchaseOrdersList;
    }

    public void setPaymentPurchaseOrdersList(List<PaymentPurchaseOrders> paymentPurchaseOrdersList) {
        this.paymentPurchaseOrdersList = paymentPurchaseOrdersList;
    }

//    @XmlTransient
//    public List<DummyCardDetails> getDummyCardDetailsList() {
//        return dummyCardDetailsList;
//    }
//
//    public void setDummyCardDetailsList(List<DummyCardDetails> dummyCardDetailsList) {
//        this.dummyCardDetailsList = dummyCardDetailsList;
//    }

    @XmlTransient
    public List<PaymentPurchaseStatus> getPaymentPurchaseStatusList() {
        return paymentPurchaseStatusList;
    }

    public void setPaymentPurchaseStatusList(List<PaymentPurchaseStatus> paymentPurchaseStatusList) {
        this.paymentPurchaseStatusList = paymentPurchaseStatusList;
    }

//    public String getUser() {
//        return user;
//    }
//
//    public void setUser(String user) {
//        this.user = user;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentpurchaseId != null ? paymentpurchaseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentPurchases)) {
            return false;
        }
        PaymentPurchases other = (PaymentPurchases) object;
        if ((this.paymentpurchaseId == null && other.paymentpurchaseId != null) || (this.paymentpurchaseId != null && !this.paymentpurchaseId.equals(other.paymentpurchaseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "latestList.PaymentPurchases[ paymentpurchaseId=" + paymentpurchaseId + " ]";
    }

       public User getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(User userId) {
        this.userId = userId;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }
    
}

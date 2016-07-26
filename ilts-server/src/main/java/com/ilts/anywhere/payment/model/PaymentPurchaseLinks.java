/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: PaymentPurchaseLinks.java
 */
package com.ilts.anywhere.payment.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author ssanapureddy
 */
@Entity
@Table(name = "payment_purchase_links", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "PaymentPurchaseLinks.findAll", query = "SELECT p FROM PaymentPurchaseLinks p"),
//    @NamedQuery(name = "PaymentPurchaseLinks.findByPaymentPurchaseLinkId", query = "SELECT p FROM PaymentPurchaseLinks p WHERE p.paymentPurchaseLinkId = :paymentPurchaseLinkId"),
//    @NamedQuery(name = "PaymentPurchaseLinks.findByHref", query = "SELECT p FROM PaymentPurchaseLinks p WHERE p.href = :href"),
//    @NamedQuery(name = "PaymentPurchaseLinks.findByRel", query = "SELECT p FROM PaymentPurchaseLinks p WHERE p.rel = :rel"),
//    @NamedQuery(name = "PaymentPurchaseLinks.findByMethod", query = "SELECT p FROM PaymentPurchaseLinks p WHERE p.method = :method"),
//    @NamedQuery(name = "PaymentPurchaseLinks.findByDeleted", query = "SELECT p FROM PaymentPurchaseLinks p WHERE p.deleted = :deleted")})
public class PaymentPurchaseLinks implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "payment_purchase_link_id", unique = true)

    private String paymentPurchaseLinkId;
    @Column(name = "href")
    private String href;
    @Column(name = "rel")
    private String rel;
    @Column(name = "method")
    private String method;
    @Column(name = "deleted")
    private Boolean deleted;
    @JoinColumn(name = "payment_purchase_Id", referencedColumnName = "payment_purchase_Id")
    @ManyToOne(optional = false)
    private PaymentPurchases paymentpurchaseId;

    public PaymentPurchaseLinks() {
    }

    public PaymentPurchaseLinks(String paymentPurchaseLinkId) {
        this.paymentPurchaseLinkId = paymentPurchaseLinkId;
    }

    public String getPaymentPurchaseLinkId() {
        return paymentPurchaseLinkId;
    }

    public void setPaymentPurchaseLinkId(String paymentPurchaseLinkId) {
        this.paymentPurchaseLinkId = paymentPurchaseLinkId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
        hash += (paymentPurchaseLinkId != null ? paymentPurchaseLinkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentPurchaseLinks)) {
            return false;
        }
        PaymentPurchaseLinks other = (PaymentPurchaseLinks) object;
        if ((this.paymentPurchaseLinkId == null && other.paymentPurchaseLinkId != null) || (this.paymentPurchaseLinkId != null && !this.paymentPurchaseLinkId.equals(other.paymentPurchaseLinkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "latestList.PaymentPurchaseLinks[ paymentPurchaseLinkId=" + paymentPurchaseLinkId + " ]";
    }
    
}

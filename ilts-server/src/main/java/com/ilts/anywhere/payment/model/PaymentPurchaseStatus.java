/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: PaymentPurchaseStatus.java
 */
package com.ilts.anywhere.payment.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author ssanapureddy
 */
@Entity
@Table(name = "payment_purchase_status", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "PaymentPurchaseStatus.findAll", query = "SELECT p FROM PaymentPurchaseStatus p"),
//    @NamedQuery(name = "PaymentPurchaseStatus.findByPaymentstatusId", query = "SELECT p FROM PaymentPurchaseStatus p WHERE p.paymentstatusId = :paymentstatusId"),
//    @NamedQuery(name = "PaymentPurchaseStatus.findByStatus", query = "SELECT p FROM PaymentPurchaseStatus p WHERE p.status = :status"),
//    @NamedQuery(name = "PaymentPurchaseStatus.findByDatecreated", query = "SELECT p FROM PaymentPurchaseStatus p WHERE p.datecreated = :datecreated"),
//    @NamedQuery(name = "PaymentPurchaseStatus.findByDatemodified", query = "SELECT p FROM PaymentPurchaseStatus p WHERE p.datemodified = :datemodified"),
//    @NamedQuery(name = "PaymentPurchaseStatus.findByDeleted", query = "SELECT p FROM PaymentPurchaseStatus p WHERE p.deleted = :deleted")})
public class PaymentPurchaseStatus implements Serializable {
    private static final long serialVersionUID = 1L;
        @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "payment_status_Id", unique = true)
    private String paymentstatusId;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Column(name = "datecreated")
//    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreated;
    @Basic(optional = false)
    @Column(name = "datemodified")
//    @Temporal(TemporalType.TIMESTAMP)
    private Date datemodified;
    @Column(name = "deleted")
    private Boolean deleted;
    @JoinColumn(name = "payment_purchase_Id", referencedColumnName = "payment_purchase_Id")
    @ManyToOne(optional = false)
    private PaymentPurchases paymentpurchaseId;

    public PaymentPurchaseStatus() {
    }

    public PaymentPurchaseStatus(String paymentstatusId) {
        this.paymentstatusId = paymentstatusId;
    }

    public PaymentPurchaseStatus(String paymentstatusId, String status, Date datemodified) {
        this.paymentstatusId = paymentstatusId;
        this.status = status;
        this.datemodified = datemodified;
    }

    public String getPaymentstatusId() {
        return paymentstatusId;
    }

    public void setPaymentstatusId(String paymentstatusId) {
        this.paymentstatusId = paymentstatusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(Date datemodified) {
        this.datemodified = datemodified;
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
        hash += (paymentstatusId != null ? paymentstatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentPurchaseStatus)) {
            return false;
        }
        PaymentPurchaseStatus other = (PaymentPurchaseStatus) object;
        if ((this.paymentstatusId == null && other.paymentstatusId != null) || (this.paymentstatusId != null && !this.paymentstatusId.equals(other.paymentstatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "latestList.PaymentPurchaseStatus[ paymentstatusId=" + paymentstatusId + " ]";
    }
    
}

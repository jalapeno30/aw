/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: PaymentPurchaseOrders.java
 */
package com.ilts.anywhere.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ilts.anywhere.cart.Orders;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author ssanapureddy
 */

@Entity
@Table(name = "payment_purchase_orders", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "PaymentPurchaseOrders.findAll", query = "SELECT p FROM PaymentPurchaseOrders p"),
//    @NamedQuery(name = "PaymentPurchaseOrders.findByPaymentPurchaseOrderId", query = "SELECT p FROM PaymentPurchaseOrders p WHERE p.paymentPurchaseOrderId = :paymentPurchaseOrderId"),
//    @NamedQuery(name = "PaymentPurchaseOrders.findByDeleted", query = "SELECT p FROM PaymentPurchaseOrders p WHERE p.deleted = :deleted")})
public class PaymentPurchaseOrders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "payment_purchase_order_id", unique = true)

    private String paymentPurchaseOrderId;
    @Column(name = "deleted")
    private Boolean deleted;
    @JoinColumn(name = "payment_purchase_Id", referencedColumnName = "payment_purchase_Id")
    @ManyToOne(optional = false)
    private PaymentPurchases paymentpurchaseId;
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @ManyToOne(optional = false)
    private Orders orderId;
    @Transient
    @JsonProperty("orderIds")
    private List<String> orderNum;

    public PaymentPurchaseOrders() {
    }

    public PaymentPurchaseOrders(String paymentPurchaseOrderId) {
        this.paymentPurchaseOrderId = paymentPurchaseOrderId;
    }

    public String getPaymentPurchaseOrderId() {
        return paymentPurchaseOrderId;
    }

    public void setPaymentPurchaseOrderId(String paymentPurchaseOrderId) {
        this.paymentPurchaseOrderId = paymentPurchaseOrderId;
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

    public List<String> getOrder() {
        return orderNum;
    }

    public void setOrder(List<String> order) {
        this.orderNum = order;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentPurchaseOrderId != null ? paymentPurchaseOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentPurchaseOrders)) {
            return false;
        }
        PaymentPurchaseOrders other = (PaymentPurchaseOrders) object;
        if ((this.paymentPurchaseOrderId == null && other.paymentPurchaseOrderId != null) || (this.paymentPurchaseOrderId != null && !this.paymentPurchaseOrderId.equals(other.paymentPurchaseOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "latestList.PaymentPurchaseOrders[ paymentPurchaseOrderId=" + paymentPurchaseOrderId + " ]";
    }

    /**
     * @return the orderNum
     */
    public Orders getOrderId() {
        return orderId;
    }

    /**
     * @param order the orderNum to set
     */
    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }
    
}

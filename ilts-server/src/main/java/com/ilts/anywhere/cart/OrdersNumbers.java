/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilts.anywhere.cart;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author ssanapureddy
 */
@Entity
@Table(name = "orders_numbers", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "OrdersNumbers.findAll", query = "SELECT o FROM OrdersNumbers o"),
//    @NamedQuery(name = "OrdersNumbers.findByOrdersNumberId", query = "SELECT o FROM OrdersNumbers o WHERE o.ordersNumberId = :ordersNumberId"),
//    @NamedQuery(name = "OrdersNumbers.findByNumbers", query = "SELECT o FROM OrdersNumbers o WHERE o.numbers = :numbers")})
public class OrdersNumbers implements Serializable {
    private static final long serialVersionUID = 1L;

//        @Id
//    @Column(name = "orders_number_id", unique = true)
//    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "before_insert_orders_numbers_id")
//    @GenericGenerator( 
//    name="before_insert_orders_numbers_id", strategy="org.hibernate.id.SelectGenerator",
//    parameters = {
//        @org.hibernate.annotations.Parameter( name="keys", value="numbers" )
//    }
//)
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "orders_number_id")
     @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "orders_number_id", unique = true)  
    private String ordersNumberId;
//            @NaturalId
    @Column(name = "numbers")
    private String numbers;

    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @ManyToOne(optional = false)
    private Orders orderId;

    public OrdersNumbers() {
    }

    public OrdersNumbers(String ordersNumberId) {
        this.ordersNumberId = ordersNumberId;
    }

    public String getOrdersNumberId() {
        return ordersNumberId;
    }

    public void setOrdersNumberId(String ordersNumberId) {
        this.ordersNumberId = ordersNumberId;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordersNumberId != null ? ordersNumberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdersNumbers)) {
            return false;
        }
        OrdersNumbers other = (OrdersNumbers) object;
        if ((this.ordersNumberId == null && other.ordersNumberId != null) || (this.ordersNumberId != null && !this.ordersNumberId.equals(other.ordersNumberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.List_Test.Beanclasses.OrdersNumbers[ ordersNumberId=" + ordersNumberId + " ]";
    }
    
}

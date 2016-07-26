/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: Orders.java
 */
package com.ilts.anywhere.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ilts.anywhere.authentication.model.User;
import com.ilts.anywhere.games.model.Draw;
import com.ilts.anywhere.games.model.Game;
import com.ilts.anywhere.payment.model.PaymentPurchaseOrders;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author ssanapureddy
 */
@JsonDeserialize
@Entity
@Table(name = "orders", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
//    @NamedQuery(name = "Orders.findByOrderId", query = "SELECT o FROM Orders o WHERE o.orderId = :orderId"),
//    @NamedQuery(name = "Orders.findBySystemName", query = "SELECT o FROM Orders o WHERE o.systemName = :systemName"),
//    @NamedQuery(name = "Orders.findByCost", query = "SELECT o FROM Orders o WHERE o.cost = :cost"),
//    @NamedQuery(name = "Orders.findByDeleted", query = "SELECT o FROM Orders o WHERE o.deleted = :deleted"),
//    @NamedQuery(name = "Orders.findByActive", query = "SELECT o FROM Orders o WHERE o.active = :active")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "order_id", unique = true)
    private String orderId;
    @JsonProperty("system")
//     @NaturalId
    @Column(name = "system_name")
    private String systemName;
    @Column(name = "cost")
    private Integer cost;
    @Column(name = "deleted")
    private Boolean deleted;
    @Column(name = "active")
    private Boolean active;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
//    private List<Purchases> purchasesList;

    @JsonProperty("numbers")
    @Transient
    private ArrayList<ArrayList<Integer>> numbers = new ArrayList<ArrayList<Integer>>();
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
//    private List<OrdersNumbers> ordersNumbersList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
//    private List<PaypalPurchaseOrders> paypalPurchaseOrdersList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private List<PaymentPurchaseOrders> paymentPurchaseOrdersList;
    @JsonProperty("gameID")
    @JoinColumn(name = "game_id", referencedColumnName = "o_game_id")
    @ManyToOne
    private Game gameId;
    @JsonProperty("drawID")
    @JoinColumn(name = "draw_id", referencedColumnName = "o_draw_id")
    @ManyToOne
    private Draw drawId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = true)
    private User userId;

    @JsonProperty("token")
    @Transient
    private String token;

    public Orders() {
    }

    public Orders(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ArrayList<ArrayList<Integer>> getNumbers() {
        return numbers;
    }

    public void setNumbers(ArrayList<ArrayList<Integer>> numbers) {
        this.numbers = numbers;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

//    @XmlTransient
//    public List<Purchases> getPurchasesList() {
//        return purchasesList;
//    }
//
//    public void setPurchasesList(List<Purchases> purchasesList) {
//        this.purchasesList = purchasesList;
//    }
//    @XmlTransient
//    public List<OrdersNumbers> getOrdersNumbersList() {
//        return ordersNumbersList;
//    }
//
//    public void setOrdersNumbersList(List<OrdersNumbers> ordersNumbersList) {
//        this.ordersNumbersList = ordersNumbersList;
//    }
//    @XmlTransient
//    public List<PaypalPurchaseOrders> getPaypalPurchaseOrdersList() {
//        return paypalPurchaseOrdersList;
//    }
//
//    public void setPaypalPurchaseOrdersList(List<PaypalPurchaseOrders> paypalPurchaseOrdersList) {
//        this.paypalPurchaseOrdersList = paypalPurchaseOrdersList;
//    }
    public Game getGameId() {
        return gameId;
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    public Draw getDrawId() {
        return drawId;
    }

    public void setDrawId(Draw drawId) {
        this.drawId = drawId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.List_Test.Beanclasses.Orders[ orderId=" + orderId + " ]";
    }

}

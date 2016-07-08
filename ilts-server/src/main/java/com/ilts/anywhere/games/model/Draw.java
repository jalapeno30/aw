/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: Draw.java
 */
package com.ilts.anywhere.games.model;

import com.ilts.anywhere.betting.model.Bet;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "o_draws", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Draws.findAll", query = "SELECT d FROM Draws d"),
//    @NamedQuery(name = "Draws.findByDrawId", query = "SELECT d FROM Draws d WHERE d.drawId = :drawId"),
//    @NamedQuery(name = "Draws.findByDrawJackpot", query = "SELECT d FROM Draws d WHERE d.drawJackpot = :drawJackpot"),
//    @NamedQuery(name = "Draws.findByDrawDate", query = "SELECT d FROM Draws d WHERE d.drawDate = :drawDate")})
public class Draw implements Serializable {
    private static final long serialVersionUID = 1L;
//          @Id
//    @Column(name = "user_id", unique = true)
//    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "before_insert_users_id")
//    @GenericGenerator( 
//    name="before_insert_users_id", strategy="org.hibernate.id.SelectGenerator",
//    parameters = {
//        @org.hibernate.annotations.Parameter( name="keys", value="userName" )
//    }
//)
    
    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "o_draw_id")
      @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "o_draw_id", unique = true)
    private String drawId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "o_draw_jackpot")
    private BigDecimal drawJackpot;
    @Basic(optional = false)
    @Column(name = "o_draw_date")
    @Temporal(TemporalType.DATE)
    private java.util.Date drawDate;
     @Column(name = "o_draw_day")
    private String drawDay;
      @Column(name = "o_draw_vendorCode")
    private String drawVendorcode;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "drawId")
//    private List<Bet> betsList;
    @JoinColumn(name = "o_game_id",updatable = false,  insertable = true, referencedColumnName = "o_game_id")
    @ManyToOne(optional = false)
    private Game game;
//    @OneToMany(mappedBy = "drawId")
//    private List<Orders> ordersList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "drawId")
//    private List<DrawStatus> drawStatusList;

    public Draw() {
    }

    public Draw(String drawId) {
        this.drawId = drawId;
    }

    public Draw(String drawId, BigDecimal drawJackpot, java.util.Date drawDate) {
        this.drawId = drawId;
        this.drawJackpot = drawJackpot;
        this.drawDate = drawDate;
    }

    public String getDrawId() {
        return drawId;
    }

    public void setDrawId(String drawId) {
        this.drawId = drawId;
    }

    public BigDecimal getDrawJackpot() {
        return drawJackpot;
    }

    public void setDrawJackpot(BigDecimal drawJackpot) {
        this.drawJackpot = drawJackpot;
    }

    public java.util.Date getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(java.util.Date drawDate) {
        this.drawDate = drawDate;
    }

//    @XmlTransient
//    public List<Bet> getBetsList() {
//        return betsList;
//    }
//
//    public void setBetsList(List<Bet> betsList) {
//        this.betsList = betsList;
//    }

//    @XmlTransient
//    public List<Orders> getOrdersList() {
//        return ordersList;
//    }
//
//    public void setOrdersList(List<Orders> ordersList) {
//        this.ordersList = ordersList;
//    }

//    @XmlTransient
//    public List<DrawStatus> getDrawStatusList() {
//        return drawStatusList;
//    }
//
//    public void setDrawStatusList(List<DrawStatus> drawStatusList) {
//        this.drawStatusList = drawStatusList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (drawId != null ? drawId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Draw)) {
            return false;
        }
        Draw other = (Draw) object;
        if ((this.drawId == null && other.drawId != null) || (this.drawId != null && !this.drawId.equals(other.drawId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.List_Test.Draws[ drawId=" + drawId + " ]";
    }


    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }
}
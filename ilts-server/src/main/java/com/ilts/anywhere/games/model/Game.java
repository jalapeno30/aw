/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: Game.java
 */
package com.ilts.anywhere.games.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "o_games", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Games.findAll", query = "SELECT g FROM Games g"),
//    @NamedQuery(name = "Games.findByGameId", query = "SELECT g FROM Games g WHERE g.gameId = :gameId"),
//    @NamedQuery(name = "Games.findByGameName", query = "SELECT g FROM Games g WHERE g.gameName = :gameName"),
//    @NamedQuery(name = "Games.findByGameNumbers", query = "SELECT g FROM Games g WHERE g.gameNumbers = :gameNumbers"),
//    @NamedQuery(name = "Games.findByGameCost", query = "SELECT g FROM Games g WHERE g.gameCost = :gameCost"),
//    @NamedQuery(name = "Games.findByGameLogo", query = "SELECT g FROM Games g WHERE g.gameLogo = :gameLogo")})
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
  @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "o_game_id", unique = true)
    private String gameId;
    @Basic(optional = false)
    @Column(name = "o_game_name")
    private String gameName;
    @Basic(optional = false)
    @Column(name = "o_game_numbers")
    private int gameNumbers;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "o_game_cost")
    private BigDecimal gameCost;
    @Basic(optional = false)
    @Column(name = "o_game_logo")
    private String gameLogo;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameId")
//    private List<Draw> drawsList;
//    @OneToMany(mappedBy = "gameId")
//    private List<Orders> ordersList;

    public Game() {
    }

    public Game(String gameId) {
        this.gameId = gameId;
    }

    public Game(String gameId, String gameName, int gameNumbers, BigDecimal gameCost, String gameLogo) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameNumbers = gameNumbers;
        this.gameCost = gameCost;
        this.gameLogo = gameLogo;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getGameNumbers() {
        return gameNumbers;
    }

    public void setGameNumbers(int gameNumbers) {
        this.gameNumbers = gameNumbers;
    }

    public BigDecimal getGameCost() {
        return gameCost;
    }

    public void setGameCost(BigDecimal gameCost) {
        this.gameCost = gameCost;
    }

    public String getGameLogo() {
        return gameLogo;
    }

    public void setGameLogo(String gameLogo) {
        this.gameLogo = gameLogo;
    }

//    @XmlTransient
//    public List<Draw> getDrawsList() {
//        return drawsList;
//    }
//
//    public void setDrawsList(List<Draw> drawsList) {
//        this.drawsList = drawsList;
//    }

//    @XmlTransient
//    public List<Orders> getOrdersList() {
//        return ordersList;
//    }
//
//    public void setOrdersList(List<Orders> ordersList) {
//        this.ordersList = ordersList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameId != null ? gameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.List_Test.Games[ gameId=" + gameId + " ]";
    }
}
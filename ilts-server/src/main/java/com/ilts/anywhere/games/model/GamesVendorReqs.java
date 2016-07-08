/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilts.anywhere.games.model ;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author ssanapureddy
 */
@Entity
@Table(name = "games_vendor_reqs", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "GamesVendorReqs.findAll", query = "SELECT g FROM GamesVendorReqs g"),
//    @NamedQuery(name = "GamesVendorReqs.findByGamesVendorReqId", query = "SELECT g FROM GamesVendorReqs g WHERE g.gamesVendorReqId = :gamesVendorReqId"),
//    @NamedQuery(name = "GamesVendorReqs.findByGameNumbersDisplay", query = "SELECT g FROM GamesVendorReqs g WHERE g.gameNumbersDisplay = :gameNumbersDisplay"),
//    @NamedQuery(name = "GamesVendorReqs.findByLuckypick", query = "SELECT g FROM GamesVendorReqs g WHERE g.luckypick = :luckypick"),
//    @NamedQuery(name = "GamesVendorReqs.findByAutoplay", query = "SELECT g FROM GamesVendorReqs g WHERE g.autoplay = :autoplay")})
public class GamesVendorReqs implements Serializable {
    private static final long serialVersionUID = 1L;
  
//           @Id
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
//    @Column(name = "games_vendor_req_id")
      @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "games_vendor_req_id", unique = true)
    private String gamesVendorReqId;
    @Column(name = "game_numbers_display")
    private Integer gameNumbersDisplay;
    @Column(name = "luckypick")
    private Boolean luckypick;
    @Column(name = "autoplay")
    private Boolean autoplay;
    @Column(name = "game_color")
    private String gameColor;
    @JoinColumn(name = "game_id", referencedColumnName = "o_game_id")
    @OneToOne(optional = false)
    private Game gameId;

    public GamesVendorReqs() {
    }

    public GamesVendorReqs(String gamesVendorReqId) {
        this.gamesVendorReqId = gamesVendorReqId;
    }

    public String getGamesVendorReqId() {
        return gamesVendorReqId;
    }

    public void setGamesVendorReqId(String gamesVendorReqId) {
        this.gamesVendorReqId = gamesVendorReqId;
    }

    public Integer getGameNumbersDisplay() {
        return gameNumbersDisplay;
    }

    public void setGameNumbersDisplay(Integer gameNumbersDisplay) {
        this.gameNumbersDisplay = gameNumbersDisplay;
    }

    public Boolean getLuckypick() {
        return luckypick;
    }

    public void setLuckypick(Boolean luckypick) {
        this.luckypick = luckypick;
    }

    public Boolean getAutoplay() {
        return autoplay;
    }

    public void setAutoplay(Boolean autoplay) {
        this.autoplay = autoplay;
    }

    public Game getGameId() {
        return gameId;
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gamesVendorReqId != null ? gamesVendorReqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GamesVendorReqs)) {
            return false;
        }
        GamesVendorReqs other = (GamesVendorReqs) object;
        if ((this.gamesVendorReqId == null && other.gamesVendorReqId != null) || (this.gamesVendorReqId != null && !this.gamesVendorReqId.equals(other.gamesVendorReqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ilts.anywhere.games.model.GamesVendorReqs[ gamesVendorReqId=" + gamesVendorReqId + " ]";
    }

    /**
     * @return the gameColor
     */
    public String getGameColor() {
        return gameColor;
    }

    /**
     * @param gameColor the gameColor to set
     */
    public void setGameColor(String gameColor) {
        this.gameColor = gameColor;
    }
    
}

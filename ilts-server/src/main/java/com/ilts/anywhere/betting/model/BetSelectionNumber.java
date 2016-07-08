package com.ilts.anywhere.betting.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "bet_selection_numbers", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "BetSelectionNumbers.findAll", query = "SELECT b FROM BetSelectionNumbers b"),
//    @NamedQuery(name = "BetSelectionNumbers.findByBetSelectionNumberId", query = "SELECT b FROM BetSelectionNumbers b WHERE b.betSelectionNumberId = :betSelectionNumberId"),
//    @NamedQuery(name = "BetSelectionNumbers.findByBetSelectionNumber", query = "SELECT b FROM BetSelectionNumbers b WHERE b.betSelectionNumber = :betSelectionNumber")})
public class BetSelectionNumber implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bet_selection_number_id")
    private Long betSelectionNumberId;
    @Basic(optional = false)
    @Column(name = "bet_selection_number")
    private int betSelectionNumber;
    @JoinColumn(name = "bet_selection_id", referencedColumnName = "bet_selection_id")
    @ManyToOne(optional = false)
    private BetSelection betSelectionId;

    public BetSelectionNumber() {
    }

    public BetSelectionNumber(Long betSelectionNumberId) {
        this.betSelectionNumberId = betSelectionNumberId;
    }

    public BetSelectionNumber(Long betSelectionNumberId, int betSelectionNumber) {
        this.betSelectionNumberId = betSelectionNumberId;
        this.betSelectionNumber = betSelectionNumber;
    }

    public Long getBetSelectionNumberId() {
        return betSelectionNumberId;
    }

    public void setBetSelectionNumberId(Long betSelectionNumberId) {
        this.betSelectionNumberId = betSelectionNumberId;
    }

    public int getBetSelectionNumber() {
        return betSelectionNumber;
    }

    public void setBetSelectionNumber(int betSelectionNumber) {
        this.betSelectionNumber = betSelectionNumber;
    }

    public BetSelection getBetSelectionId() {
        return betSelectionId;
    }

    public void setBetSelectionId(BetSelection betSelectionId) {
        this.betSelectionId = betSelectionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (betSelectionNumberId != null ? betSelectionNumberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BetSelectionNumber)) {
            return false;
        }
        BetSelectionNumber other = (BetSelectionNumber) object;
        if ((this.betSelectionNumberId == null && other.betSelectionNumberId != null) || (this.betSelectionNumberId != null && !this.betSelectionNumberId.equals(other.betSelectionNumberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ilts.anywhere.betting.model.BetSelectionNumbers[ betSelectionNumberId=" + betSelectionNumberId + " ]";
    }
    
}

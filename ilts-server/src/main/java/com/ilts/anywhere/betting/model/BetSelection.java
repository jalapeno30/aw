package com.ilts.anywhere.betting.model;


import com.ilts.anywhere.games.model.LottoSystem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "bet_selections", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "BetSelections.findAll", query = "SELECT b FROM BetSelections b"),
//    @NamedQuery(name = "BetSelections.findByBetSelectionId", query = "SELECT b FROM BetSelections b WHERE b.betSelectionId = :betSelectionId"),
//    @NamedQuery(name = "BetSelections.findByBetSelectionBonus", query = "SELECT b FROM BetSelections b WHERE b.betSelectionBonus = :betSelectionBonus")})
public class BetSelection implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bet_selection_id")
    private Long betSelectionId;
    @Basic(optional = false)
    @Column(name = "bet_selection_bonus")
    private int betSelectionBonus;
    @JoinColumn(name = "bet_selection_system", referencedColumnName = "system_name")
    @ManyToOne(optional = false)
    private LottoSystem betSelectionSystem;
    @JoinColumn(name = "bet_id", referencedColumnName = "bet_id")
    @ManyToOne(optional = false)
    private Bet betId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "betSelectionId")
    private List<BetSelectionNumber> betSelectionNumbersList;

    public BetSelection() {
    }

    public BetSelection(Long betSelectionId) {
        this.betSelectionId = betSelectionId;
    }

    public BetSelection(Long betSelectionId, int betSelectionBonus) {
        this.betSelectionId = betSelectionId;
        this.betSelectionBonus = betSelectionBonus;
    }

    public Long getBetSelectionId() {
        return betSelectionId;
    }

    public void setBetSelectionId(Long betSelectionId) {
        this.betSelectionId = betSelectionId;
    }

    public int getBetSelectionBonus() {
        return betSelectionBonus;
    }

    public void setBetSelectionBonus(int betSelectionBonus) {
        this.betSelectionBonus = betSelectionBonus;
    }

    public LottoSystem getBetSelectionSystem() {
        return betSelectionSystem;
    }

    public void setBetSelectionSystem(LottoSystem betSelectionSystem) {
        this.betSelectionSystem = betSelectionSystem;
    }

    public Bet getBetId() {
        return betId;
    }

    public void setBetId(Bet betId) {
        this.betId = betId;
    }

    @XmlTransient
    public List<BetSelectionNumber> getBetSelectionNumbersList() {
        return betSelectionNumbersList;
    }

    public void setBetSelectionNumbersList(List<BetSelectionNumber> betSelectionNumbersList) {
        this.betSelectionNumbersList = betSelectionNumbersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (betSelectionId != null ? betSelectionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BetSelection)) {
            return false;
        }
        BetSelection other = (BetSelection) object;
        if ((this.betSelectionId == null && other.betSelectionId != null) || (this.betSelectionId != null && !this.betSelectionId.equals(other.betSelectionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ilts.anywhere.betting.model.BetSelections[ betSelectionId=" + betSelectionId + " ]";
    }
    
}

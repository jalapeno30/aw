package com.ilts.anywhere.betting.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import com.ilts.anywhere.authentication.model.User;
import com.ilts.anywhere.games.model.Draw;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "bets", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Bets.findAll", query = "SELECT b FROM Bets b"),
//    @NamedQuery(name = "Bets.findByBetId", query = "SELECT b FROM Bets b WHERE b.betId = :betId"),
//    @NamedQuery(name = "Bets.findByBetCost", query = "SELECT b FROM Bets b WHERE b.betCost = :betCost"),
//    @NamedQuery(name = "Bets.findByBetTax", query = "SELECT b FROM Bets b WHERE b.betTax = :betTax"),
//    @NamedQuery(name = "Bets.findByDeleted", query = "SELECT b FROM Bets b WHERE b.deleted = :deleted")})
public class Bet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bet_id")
    private Long betId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "bet_cost")
    private Double betCost;
    @Basic(optional = false)
    @Column(name = "bet_tax")
    private Double betTax;
    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;
    @JoinColumn(name = "draw_id", referencedColumnName = "o_draw_id")
    @ManyToOne(optional = false)
    private Draw drawId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "betId")
    private List<BetSelection> betSelectionsList;

    public Bet() {
    }

    public Bet(Long betId) {
        this.betId = betId;
    }

    public Bet(Long betId, Double betCost, Double betTax, boolean deleted) {
        this.betId = betId;
        this.betCost = betCost;
        this.betTax = betTax;
        this.deleted = deleted;
    }

    public Long getBetId() {
        return betId;
    }

    public void setBetId(Long betId) {
        this.betId = betId;
    }

    public Double getBetCost() {
        return betCost;
    }

    public void setBetCost(Double betCost) {
        this.betCost = betCost;
    }

    public Double getBetTax() {
        return betTax;
    }

    public void setBetTax(Double betTax) {
        this.betTax = betTax;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    @XmlTransient
    public List<BetSelection> getBetSelectionsList() {
        return betSelectionsList;
    }

    public void setBetSelectionsList(List<BetSelection> betSelectionsList) {
        this.betSelectionsList = betSelectionsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (betId != null ? betId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bet)) {
            return false;
        }
        Bet other = (Bet) object;
        if ((this.betId == null && other.betId != null) || (this.betId != null && !this.betId.equals(other.betId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ilts.anywhere.betting.model[ betId=" + betId + " ]";
    }
    
}

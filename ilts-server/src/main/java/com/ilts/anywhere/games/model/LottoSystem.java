/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: LottoSystem.java
 */
package com.ilts.anywhere.games.model;

import com.ilts.anywhere.betting.model.BetSelection;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "systems", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Systems.findAll", query = "SELECT s FROM Systems s"),
//    @NamedQuery(name = "Systems.findBySystemName", query = "SELECT s FROM Systems s WHERE s.systemName = :systemName"),
//    @NamedQuery(name = "Systems.findBySystemNumber", query = "SELECT s FROM Systems s WHERE s.systemNumber = :systemNumber")})
public class LottoSystem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "system_name",unique=true)
    private String systemName;
    @Column(name = "system_number",unique =true)
    private Integer systemNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "betSelectionSystem")
    private List<BetSelection> betSelectionsList;

    public LottoSystem() {
    }

    public LottoSystem(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Integer getSystemNumber() {
        return systemNumber;
    }

    public void setSystemNumber(Integer systemNumber) {
        this.systemNumber = systemNumber;
    }

//    @XmlTransient
//    public List<BetSelection> getBetSelectionsList() {
//        return betSelectionsList;
//    }
//
//    public void setBetSelectionsList(List<BetSelection> betSelectionsList) {
//        this.betSelectionsList = betSelectionsList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemName != null ? systemName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LottoSystem)) {
            return false;
        }
        LottoSystem other = (LottoSystem) object;
        if ((this.systemName == null && other.systemName != null) || (this.systemName != null && !this.systemName.equals(other.systemName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.List_Test.Systems[ systemName=" + systemName + " ]";
    }
}
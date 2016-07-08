/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: DrawStatusDetail.java
 */
package com.ilts.anywhere.games.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "draw_statuses", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "DrawStatuses.findAll", query = "SELECT d FROM DrawStatuses d"),
//    @NamedQuery(name = "DrawStatuses.findByDrawStatusesId", query = "SELECT d FROM DrawStatuses d WHERE d.drawStatusesId = :drawStatusesId"),
//    @NamedQuery(name = "DrawStatuses.findByStatusName", query = "SELECT d FROM DrawStatuses d WHERE d.statusName = :statusName")})
public class DrawStatusDetail implements Serializable {
    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "draw_statuses_id")
     @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "draw_statuses_id", unique = true)
    private String drawStatusesId;
    @Basic(optional = false)
    @Column(name = "status_name")
    private String statusName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statusId")
    private List<DrawStatus> drawStatusList;

    public DrawStatusDetail() {
    }

    public DrawStatusDetail(String drawStatusesId) {
        this.drawStatusesId = drawStatusesId;
    }

    public DrawStatusDetail(String drawStatusesId, String statusName) {
        this.drawStatusesId = drawStatusesId;
        this.statusName = statusName;
    }

    public String getDrawStatusesId() {
        return drawStatusesId;
    }

    public void setDrawStatusesId(String drawStatusesId) {
        this.drawStatusesId = drawStatusesId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

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
        hash += (drawStatusesId != null ? drawStatusesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DrawStatusDetail)) {
            return false;
        }
        DrawStatusDetail other = (DrawStatusDetail) object;
        if ((this.drawStatusesId == null && other.drawStatusesId != null) || (this.drawStatusesId != null && !this.drawStatusesId.equals(other.drawStatusesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.List_Test.DrawStatuses[ drawStatusesId=" + drawStatusesId + " ]";
    }
}
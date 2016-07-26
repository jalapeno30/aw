package com.ilts.anywhere.games.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "draw_status", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "DrawStatus.findAll", query = "SELECT d FROM DrawStatus d"),
//    @NamedQuery(name = "DrawStatus.findByDrawStatusId", query = "SELECT d FROM DrawStatus d WHERE d.drawStatusId = :drawStatusId"),
//    @NamedQuery(name = "DrawStatus.findByCreated", query = "SELECT d FROM DrawStatus d WHERE d.created = :created"),
//    @NamedQuery(name = "DrawStatus.findByDeleted", query = "SELECT d FROM DrawStatus d WHERE d.deleted = :deleted")})
public class DrawStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "draw_status_id", unique = true)
    private String drawStatusId;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date created;
    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;
    @NaturalId
    @JoinColumn(name = "draw_id", referencedColumnName = "o_draw_id")
    @ManyToOne(optional = false)
    private Draw drawId;
    @JoinColumn(name = "status_id", referencedColumnName = "draw_statuses_id")
    @ManyToOne(optional = false)
    private DrawStatusDetail statusId;

    public DrawStatus() {
    }

    public DrawStatus(String drawStatusId) {
        this.drawStatusId = drawStatusId;
    }

    public DrawStatus(String drawStatusId, java.util.Date created, boolean deleted) {
        this.drawStatusId = drawStatusId;
        this.created = created;
        this.deleted = deleted;
    }

    public String getDrawStatusId() {
        return drawStatusId;
    }

    public void setDrawStatusId(String drawStatusId) {
        this.drawStatusId = drawStatusId;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public void setCreated(java.util.Date created) {
        this.created = created;
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

    public DrawStatusDetail getStatusId() {
        return statusId;
    }

    public void setStatusId(DrawStatusDetail statusId) {
        this.statusId = statusId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (drawStatusId != null ? drawStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DrawStatus)) {
            return false;
        }
        DrawStatus other = (DrawStatus) object;
        if ((this.drawStatusId == null && other.drawStatusId != null) || (this.drawStatusId != null && !this.drawStatusId.equals(other.drawStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.List_Test.DrawStatus[ drawStatusId=" + drawStatusId + " ]";
    }

}

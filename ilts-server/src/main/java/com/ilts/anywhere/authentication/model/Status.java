package com.ilts.anywhere.authentication.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user_statuses", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "UserStatuses.findAll", query = "SELECT u FROM UserStatuses u"),
//    @NamedQuery(name = "UserStatuses.findByUserStatusesId", query = "SELECT u FROM UserStatuses u WHERE u.userStatusesId = :userStatusesId"),
//    @NamedQuery(name = "UserStatuses.findByUserStatusesName", query = "SELECT u FROM UserStatuses u WHERE u.userStatusesName = :userStatusesName")})
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "user_statuses_id", unique = true)
    private String userStatusesId;
    @Basic(optional = false)
    @Column(name = "user_statuses_name")
    private String userStatusesName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userStatusesId")
    private List<UserStatus> userStatusList;

    public Status() {
    }

    public Status(String userStatusesId) {
        this.userStatusesId = userStatusesId;
    }

    public Status(String userStatusesId, String userStatusesName) {
        this.userStatusesId = userStatusesId;
        this.userStatusesName = userStatusesName;
    }

    public String getUserStatusesId() {
        return userStatusesId;
    }

    public void setUserStatusesId(String userStatusesId) {
        this.userStatusesId = userStatusesId;
    }

    public String getUserStatusesName() {
        return userStatusesName;
    }

    public void setUserStatusesName(String userStatusesName) {
        this.userStatusesName = userStatusesName;
    }

    @XmlTransient
    public List<UserStatus> getUserStatusList() {
        return userStatusList;
    }

    public void setUserStatusList(List<UserStatus> userStatusList) {
        this.userStatusList = userStatusList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userStatusesId != null ? userStatusesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Status)) {
            return false;
        }
        Status other = (Status) object;
        if ((this.userStatusesId == null && other.userStatusesId != null) || (this.userStatusesId != null && !this.userStatusesId.equals(other.userStatusesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ilts.anywhere.authentication.model[ userStatusesId=" + userStatusesId + " ]";
    }

}

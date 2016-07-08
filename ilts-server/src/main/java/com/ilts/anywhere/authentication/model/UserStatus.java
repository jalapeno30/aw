/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilts.anywhere.authentication.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author ssanapureddy
 */
@Entity
@Table(name = "user_status", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "UserStatus.findAll", query = "SELECT u FROM UserStatus u"),
//    @NamedQuery(name = "UserStatus.findByUserStatusId", query = "SELECT u FROM UserStatus u WHERE u.userStatusId = :userStatusId")})
public class UserStatus implements Serializable {

    private static final long serialVersionUID = 1L;
//         @Id
//    @Column(name = "user_status_id", unique = true)
//    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "before_insert_user_status_id")
//    @GenericGenerator( 
//    name="before_insert_user_status_id", strategy="org.hibernate.id.SelectGenerator",
//    parameters = {
//        @Parameter( name="keys", value="userId" )
//    }
//)
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "user_status_id", unique = true)
    private String userStatusId;
    @JoinColumn(name = "user_statuses_id", referencedColumnName = "user_statuses_id")
    @ManyToOne(optional = false)
    private Status userStatusesId;
    @NaturalId
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;

    public UserStatus() {
    }

    public UserStatus(String userStatusId) {
        this.userStatusId = userStatusId;
    }

    public String getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(String userStatusId) {
        this.userStatusId = userStatusId;
    }

    public Status getUserStatusesId() {
        return userStatusesId;
    }

    public void setUserStatusesId(Status userStatusesId) {
        this.userStatusesId = userStatusesId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userStatusId != null ? userStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserStatus)) {
            return false;
        }
        UserStatus other = (UserStatus) object;
        if ((this.userStatusId == null && other.userStatusId != null) || (this.userStatusId != null && !this.userStatusId.equals(other.userStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ilts.anywhere.authentication.model[ userStatusId=" + userStatusId + " ]";
    }

}

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

/**
 *
 * @author ssanapureddy
 */
@Entity
@Table(name = "role_permissions", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "RolePermissions.findAll", query = "SELECT r FROM RolePermissions r"),
//    @NamedQuery(name = "RolePermissions.findByRolePermissionId", query = "SELECT r FROM RolePermissions r WHERE r.rolePermissionId = :rolePermissionId"),
//    @NamedQuery(name = "RolePermissions.findByValue", query = "SELECT r FROM RolePermissions r WHERE r.value = :value")})
public class RolePermissions implements Serializable {
    private static final long serialVersionUID = 1L;
  
//              @Id
//    @Column(name = "role_permission_id", unique = true)
//    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "before_insert_role_permissions_id")
//    @GenericGenerator( 
//    name="before_insert_role_permissions_id", strategy="org.hibernate.id.SelectGenerator",
//    parameters = {
//        @org.hibernate.annotations.Parameter( name="keys", value="role_id" )
//    }
//)
    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "role_permission_id")
       @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "role_permission_id", unique = true)
    private String rolePermissionId;
    @Basic(optional = false)
    @Column(name = "value")
    private boolean value;
    @JoinColumn(name = "permission_id", referencedColumnName = "permission_id")
    @ManyToOne(optional = false)
    private Permission permissionId;
    @NaturalId
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Role roleId;

    public RolePermissions() {
    }

    public RolePermissions(String rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public RolePermissions(String rolePermissionId, boolean value) {
        this.rolePermissionId = rolePermissionId;
        this.value = value;
    }

    public String getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(String rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public Permission getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Permission permissionId) {
        this.permissionId = permissionId;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolePermissionId != null ? rolePermissionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolePermissions)) {
            return false;
        }
        RolePermissions other = (RolePermissions) object;
        if ((this.rolePermissionId == null && other.rolePermissionId != null) || (this.rolePermissionId != null && !this.rolePermissionId.equals(other.rolePermissionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ilts.anywhere.authentication.model[ rolePermissionId=" + rolePermissionId + " ]";
    }
    
}
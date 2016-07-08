package com.ilts.anywhere.authentication.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "permissions", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Permissions.findAll", query = "SELECT p FROM Permissions p"),
//    @NamedQuery(name = "Permissions.findByPermissionId", query = "SELECT p FROM Permissions p WHERE p.permissionId = :permissionId"),
//    @NamedQuery(name = "Permissions.findByAlias", query = "SELECT p FROM Permissions p WHERE p.alias = :alias"),
//    @NamedQuery(name = "Permissions.findByDescription", query = "SELECT p FROM Permissions p WHERE p.description = :description")})
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;
//    @Id
//    @Basic(optional = false)
//    @Column(name = "permission_id")
      @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "permission_id", unique = true)
    private String permissionId;
    @Basic(optional = false)
    @Column(name = "alias")
    private String alias;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "permissionId")
    private List<RolePermissions> rolePermissionsList;

    public Permission() {
    }

    public Permission(String permissionId) {
        this.permissionId = permissionId;
    }

    public Permission(String permissionId, String alias, String description) {
        this.permissionId = permissionId;
        this.alias = alias;
        this.description = description;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<RolePermissions> getRolePermissionsList() {
        return rolePermissionsList;
    }

    public void setRolePermissionsList(List<RolePermissions> rolePermissionsList) {
        this.rolePermissionsList = rolePermissionsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permissionId != null ? permissionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permission)) {
            return false;
        }
        Permission other = (Permission) object;
        if ((this.permissionId == null && other.permissionId != null) || (this.permissionId != null && !this.permissionId.equals(other.permissionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ilts.anywhere.authentication.model[ permissionId=" + permissionId + " ]";
    }
}

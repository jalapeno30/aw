package com.ilts.anywhere.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcPermissionsDAO implements PermissionsDAO {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	JdbcPermissionsDAO() {
	}

	public List<Permission> getPermissionsByRole(String roleId) {
		
		String sql = "SELECT role_permissions.id AS id, permissions.id AS permissionId, permissions.alias, permissions.description, role_permissions.value, roles.name AS roleName "
				+ "FROM role_permissions "
				+ "LEFT JOIN permissions ON permissions.id = role_permissions.permissionId "
				+ "JOIN roles ON role_permissions.roleId = roles.id "
				+ "WHERE role_permissions.roleId = ?";
		
                
		List<Permission> permissions = new ArrayList<Permission>();
		List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql, new Object[]{roleId});
		for (Map row: rows) {
			Permission permission = new Permission();
			permission.setId(((Long)(row.get("id"))).intValue());
			permission.setPermissionId((Integer)(row.get("permissionId")));
			permission.setAlias((String)(row.get("alias")));
			permission.setDescription((String)(row.get("description")));
			permission.setAllowed((Boolean)(row.get("value")));
			permissions.add(permission);
		}
		
		return permissions;
	}

	public void savePermissions(List<Permission> permissions) {
		
		String sql = "UPDATE role_permissions SET value = ? WHERE id = ?";

		
		for(Permission permission: permissions) {
			this.jdbcTemplate.update(sql, new Object[]{
				permission.getAllowed(), permission.getId()
			});
		}
		
	}
	
}

package com.ilts.anywhere.authentication;

import java.util.List;

public interface PermissionsDAO {
	
	public List<Permission> getPermissionsByRole(String roleId);
	
	public void savePermissions(List<Permission> permissions);

}

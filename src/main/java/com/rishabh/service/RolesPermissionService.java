package com.rishabh.service;

import com.rishabh.dto.RolePermissionsDTO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface RolesPermissionService {

    List<RolePermissionsDTO> findPermissionIdByRoleId(int roleId);

    void assignPermissionToRoles(int roleId, List<Integer> permissionIds);

    void deletePermissionFromRoles(int roleId, List<Integer> permissionIds);

}

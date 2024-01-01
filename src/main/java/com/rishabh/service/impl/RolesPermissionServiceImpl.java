package com.rishabh.service.impl;

import com.rishabh.entity.Permission;
import com.rishabh.entity.Role;
import com.rishabh.entity.RolePermissionId;
import com.rishabh.entity.RolesPermission;
import com.rishabh.repository.PermissionRepository;
import com.rishabh.repository.RoleRepository;
import com.rishabh.repository.RolesPermissionRepository;
import com.rishabh.dto.RolePermissionsDTO;
import com.rishabh.service.RolesPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolesPermissionServiceImpl implements RolesPermissionService {

    //Declare repository fields for roles and permissions
    private final RolesPermissionRepository rolesPermissionRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;


   @Autowired
    RolesPermissionServiceImpl(RolesPermissionRepository rolePermissionRepository,RoleRepository roleRepository , PermissionRepository permissionRepository) {
        this.rolesPermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Finds permission IDs assigned to a specific role.
     *
     * @param roleId The ID of the role to find permissions for.
     * @return A list of RolePermissionsDTO objects representing permission IDs assigned to the role.
     * @throws IllegalArgumentException If no permissions were found for the specified role.
     */
    @Override
    public List<RolePermissionsDTO> findPermissionIdByRoleId(int roleId) {

        //Fetch role permissions from the repository
        List<RolesPermission> rolesPermissions = rolesPermissionRepository.findPermissionIdByRoleId(roleId);

        //check if permissions were found
        if (rolesPermissions != null) {

            //Map RolesPermission objects to RolePermissionsDTO using a builder pattern
            List<RolePermissionsDTO> rolePermissionsDTOList = rolesPermissions.stream()
                    .map(rolePermission -> RolePermissionsDTO.builder()
                                .roleId(rolePermission.getId().getRoleId())
                                .permissionId(rolePermission.getId().getPermissionId())
                                .build())
                                .toList();
            // Return the list of RolePermissionsDTO objects
              return rolePermissionsDTOList;
        }
        else {
            //Throw an exception if no permissions were found
            throw new IllegalArgumentException("No Role found for RoleId: " + roleId);
        }
    }

    /**
     * Assigns permissions to a role.
     *
     * @param roleId        The ID of the role to assign permissions to.
     * @param permissionIds The list of permission IDs to assign to the role.
     * @throws RuntimeException If the specified role or permission is not found.
     */
    @Override
    public void assignPermissionToRoles(int roleId, List<Integer> permissionIds) {

        //Retrieve the role by its ID from the role repository
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found" + roleId));

        //list to store the rolesPermissions to be created
        List<RolesPermission> rolesPermissionList = new ArrayList<>();

        //loop through the rolesPermissions
        for(int permissionId : permissionIds) {

            //Retrieve the permission by its ID from the permission repository
            Permission permission = permissionRepository.findById(permissionId)
                    .orElseThrow(() -> new RuntimeException("Permission not found" + permissionId));

            //Create a RolesPermission object using a builder pattern
            RolesPermission rolesPermission = RolesPermission.builder()
                            .id(new RolePermissionId(roleId, permissionId))
                            .role(role)
                            .permission(permission)
                            .build();

            //Add the RolesPermission
            rolesPermissionList.add(rolesPermission);
        }
        //save all the rolesPermissions in the database
        rolesPermissionRepository.saveAll(rolesPermissionList);
    }

    /**
     * Deletes permissions from a role.
     *
     * @param roleId        The ID of the role to delete permissions from.
     * @param permissionIds The list of permission IDs to delete from the role.
     */
    @Override
    public void deletePermissionFromRoles(int roleId, List<Integer> permissionIds) {

        //Find the role based on the roleId or throw an exception if not found.
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        //Iterate Over permissionIds List and Delete Permissions:
        for (int permissionId : permissionIds) {
          rolesPermissionRepository.deleteByRoleAndPermission(roleId,permissionId);
        }
    }
}













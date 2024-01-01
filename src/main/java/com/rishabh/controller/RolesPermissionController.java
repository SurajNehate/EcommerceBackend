package com.rishabh.controller;

import com.rishabh.dto.RolePermissionsDTO;
import com.rishabh.service.RolesPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Controller class responsible for handling role-permission-related operations.
 */
@RestController
@RequestMapping("/rolespermission")
public class RolesPermissionController {

    private final RolesPermissionService rolePermissionService;

    @Autowired
    public RolesPermissionController(RolesPermissionService theRolePermissionService) {
        rolePermissionService = theRolePermissionService;
    }
    /**
     * Endpoint for retrieving all permissions associated with a role.
     *
     * @param roleId The ID of the role to retrieve permissions for.
     * @return A list of RolePermissionsDTO representing permissions associated with the role.
     */
    @GetMapping("/{roleId}")
    @PreAuthorize("hasAuthority('READ_PERMISSION')")
    public List<RolePermissionsDTO> findPermissionsByRoleId(@PathVariable int roleId) {
        return rolePermissionService.findPermissionIdByRoleId(roleId);
    }

    /**
     * Endpoint for assigning permissions to a role.
     *
     * @param roleId       The ID of the role to which permissions will be assigned.
     * @param permissionIds A list of permission IDs to assign to the role.
     * @return ResponseEntity with a success message if permissions are assigned successfully.
     */
    @PutMapping( "/{roleId}" )
    @PreAuthorize("hasAuthority('WRITE_PERMISSION')")
    public ResponseEntity<String> savePermissionsToRole(@PathVariable int roleId, @RequestBody List<Integer> permissionIds) {
        rolePermissionService.assignPermissionToRoles(roleId, permissionIds);
        return ResponseEntity.ok("Permission successfully assigned to role : " + roleId);
    }


    /**
     * Endpoint for deleting permissions from a role.
     *
     * @param roleId       The ID of the role from which permissions will be deleted.
     * @param permissionIds A list of permission IDs to delete from the role.
     * @return ResponseEntity with a success message if permissions are deleted successfully.
     */
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('WRITE_PERMISSION') && hasAuthority('ROLE_SUPERADMIN')")
    public ResponseEntity<String> deletePermissionFromRoles(@PathVariable int roleId, @RequestBody List<Integer> permissionIds) {
        rolePermissionService.deletePermissionFromRoles(roleId, permissionIds);
        return ResponseEntity.ok("Permission successfully deleted from role : " + roleId);
    }
}


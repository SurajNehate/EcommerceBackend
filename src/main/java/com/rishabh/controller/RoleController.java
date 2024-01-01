package com.rishabh.controller;

import com.rishabh.dto.RoleDTO;
import com.rishabh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller class responsible for handling role-related operations.
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Endpoint for creating a new role.
     *
     * @param roleDTO The RoleDTO containing role details.
     * @return ResponseEntity with a success message if the role is created successfully.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_ROLE')")
    public ResponseEntity<String> createRole(@RequestBody RoleDTO roleDTO) {
        roleService.saveRole(roleDTO);
        return ResponseEntity.ok("Role created successfully");
    }

    /**
     * Endpoint for retrieving all roles.
     *
     * @return A list of RoleDTO representing all roles.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('READ_ROLE')")
    public List<RoleDTO> findAllRoles() {
        return roleService.findAllRoles();
    }

    /**
     * Endpoint for updating an existing role by ID.
     *
     * @param id      The ID of the role to be updated.
     * @param roleDTO The RoleDTO containing updated role details.
     * @return ResponseEntity with a success message if the role is updated successfully.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_ROLE')")
    public ResponseEntity<String> updateRole(@PathVariable int id, @RequestBody RoleDTO roleDTO) {
        roleService.updateRole(id, roleDTO);
        return ResponseEntity.ok("Role updated successfully : " + id);
    }

    /**
     * Endpoint for deleting a role by ID.
     *
     * @param id The ID of the role to be deleted.
     * @return ResponseEntity with a success message if the role is deleted successfully.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_ROLE') && hasAuthority('ROLE_SUPERADMIN')")
    public ResponseEntity<String> deleteRoleById(@PathVariable int id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.ok("Role deleted successfully : " + id);
    }
}







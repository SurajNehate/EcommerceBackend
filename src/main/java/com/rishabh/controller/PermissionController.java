package com.rishabh.controller;

import com.rishabh.dto.PermissionDTO;
import com.rishabh.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller class responsible for handling permission-related operations.
 */
@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService thepermissionService) {
        permissionService = thepermissionService;
    }

    /**
     * Endpoint for creating a new permission.
     *
     * @param permissionDTO The PermissionDTO containing permission details.
     * @return ResponseEntity with a success message if the permission is created successfully.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_PERMISSION')")
    public ResponseEntity<String> createPermission(@RequestBody PermissionDTO permissionDTO) {
        permissionService.savePermission(permissionDTO);
        return ResponseEntity.ok("Permission created successfully");
    }

    /**
     * Endpoint for retrieving all permissions.
     *
     * @return A list of PermissionDTO representing all permissions.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('READ_PERMISSION')")
    public List<PermissionDTO> findAll() {
        return permissionService.findAllPermissions();
    }

    /**
     * Endpoint for updating an existing permission by ID.
     *
     * @param id           The ID of the permission to be updated.
     * @param permissionDTO The PermissionDTO containing updated permission details.
     * @return ResponseEntity with a success message if the permission is updated successfully.
     */
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('WRITE_PERMISSION')")
    public ResponseEntity<String> updatePermission(@PathVariable int id , @RequestBody PermissionDTO permissionDTO) {
        permissionService.updatePermission(id ,permissionDTO);
        return ResponseEntity.ok("Permission updated successfully ");
    }

    /**
     * Endpoint for deleting a permission by ID.
     *
     * @param id The ID of the permission to be deleted.
     * @return ResponseEntity with a success message if the permission is deleted successfully.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_PERMISSION') && hasAuthority('ROLE_SUPERADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        permissionService.deleteByPermissionId(id);
        return ResponseEntity.ok("Permission deleted successfully : " + id);
    }
}

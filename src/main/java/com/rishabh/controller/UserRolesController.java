package com.rishabh.controller;

import com.rishabh.dto.UserRolesDTO;
import com.rishabh.service.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Controller class responsible for handling user roles-related operations.
 */
@RestController
@RequestMapping("/userroles")
public class UserRolesController {

    private final UserRolesService userRolesService;

    @Autowired
    public UserRolesController(UserRolesService userRolesService) {
        this.userRolesService = userRolesService;
    }

    /**
     * Endpoint for retrieving user roles by user ID.
     *
     * @param userId The ID of the user to retrieve roles for.
     * @return A list of UserRolesDTO representing roles associated with the user.
     */
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('READ_ROLE')")
    public List<UserRolesDTO> findUserRolesByUserId(@PathVariable int userId) {
        return userRolesService.findAllUserRolesByUserId(userId);
    }

    /**
     * Endpoint for assigning roles to a user by user ID.
     *
     * @param userId  The ID of the user to whom roles will be assigned.
     * @param roleIds A list of role IDs to assign to the user.
     * @return ResponseEntity with a success message if roles are assigned successfully.
     */
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('WRITE_ROLE')")
    public ResponseEntity<String> assignUserRoles(@PathVariable int userId, @RequestBody List<Integer> roleIds) {
        userRolesService.assignRolestoUser(userId, roleIds);
        return ResponseEntity.ok("Roles assigned successfully");
    }

    /**
     * Endpoint for deleting roles from a user by user ID.
     *
     * @param userId  The ID of the user from whom roles will be deleted.
     * @param roleIds A list of role IDs to delete from the user.
     * @return ResponseEntity with a success message if roles are deleted successfully.
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('WRITE_ROLE')")
    public ResponseEntity<String> deleteById(@PathVariable int userId, @RequestBody List<Integer> roleIds) {
        userRolesService.deleteRolesFromUser(userId, roleIds);
        return ResponseEntity.ok("Roles deleted successfully");
    }

}

package com.rishabh.controller;

import com.rishabh.dto.UserDTO;
import com.rishabh.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Controller class responsible for handling user-related operations.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for creating a new user.
     *
     * @param userDTO The UserDTO containing user details.
     * @return ResponseEntity with a success message if the user is created successfully.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_USER')")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return ResponseEntity.ok("User created successfully");
    }
    /**
     * Endpoint for retrieving all users.
     *
     * @return A list of UserDTO representing all users.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('READ_USER')")
    public List<UserDTO> getAllUsers() {
        return userService.findAllUsers();
    }

    /**
     * Endpoint for retrieving a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The UserDTO representing the user with the specified ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public UserDTO getUserById(@PathVariable int id) {
        return userService.findUserById(id);
    }

    /**
     * Endpoint for updating an existing user by ID.
     *
     * @param id      The ID of the user to be updated.
     * @param userDTO The UserDTO containing updated user details.
     * @return ResponseEntity with a success message if the user is updated successfully.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_USER')")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id , userDTO);
        return ResponseEntity.ok("User updated successfully : " + id);
    }
    /**
     * Endpoint for deleting a user by ID.
     *
     * @param id The ID of the user to be deleted.
     * @return ResponseEntity with a success message if the user is deleted successfully.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_USER')")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully : " + id);
    }

}

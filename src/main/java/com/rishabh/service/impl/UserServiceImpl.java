package com.rishabh.service.impl;

import com.rishabh.entity.Role;
import com.rishabh.entity.UserRoleId;
import com.rishabh.entity.UserRoles;
import com.rishabh.repository.RoleRepository;
import com.rishabh.repository.RolesPermissionRepository;
import com.rishabh.repository.UserRepository;
import com.rishabh.repository.UserRolesRepository;
import com.rishabh.dto.UserDTO;
import com.rishabh.entity.User;
import com.rishabh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service implementation for managing user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;
    private final RoleRepository roleRepository;

    private final RolesPermissionRepository rolesPermissionRepository;

    /**
     * Creates and configures a BCryptPasswordEncoder bean.
     *
     * @return BCryptPasswordEncoder bean
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository , UserRolesRepository userRolesRepository, RoleRepository roleRepository, RolesPermissionRepository rolesPermissionRepository) {
        this.userRolesRepository = userRolesRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.rolesPermissionRepository = rolesPermissionRepository;
    }

    /**
     * Find a user by their username.
     *
     * @param username The username to search for.
     * @return The found user or null if not found.
     */
    @Override
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    /**
     * Find a user by their ID.
     *
     * @param id The ID of the user to find.
     * @return The user DTO containing user information.
     * @throws RuntimeException if the user is not found.
     */
    @Override
    public UserDTO findUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO userDTO = UserDTO.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
       return userDTO;
    }

    /**
     * Save a new user.
     *
     * @param userDTO The user DTO containing user information to save.
     */
    @Override
    public void saveUser(UserDTO userDTO) {

        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .userName(userDTO.getUserName())
                .email(userDTO.getEmail())
                .password(passwordEncoder().encode(userDTO.getPassword()))
                .enabled(true)
                .build();
        userRepository.save(user);
        //List<UserRoles> userRolesList = new ArrayList<>();
        Role role = roleRepository.findByRoleName("USER");
        UserRoles  userRoles = UserRoles.builder()
                .id(new UserRoleId(user.getUserId(), role.getRoleId()))
                .user(user)
                .role(role)
                .build();
        //userRolesList.add(userRoles);
        userRolesRepository.save(userRoles);
    }
    /**
     * Update an existing user.
     *
     * @param id      The ID of the user to update.
     * @param userDTO The user DTO containing updated user information.
     */
    @Override
    public void updateUser( int id , UserDTO userDTO) {
        User user = User.builder()
                .userId(id)
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .userName(userDTO.getUserName())
                .email(userDTO.getEmail())
                .password(passwordEncoder().encode(userDTO.getPassword()))
                .enabled(true)
                .build();
        userRepository.save(user);
    }

    /**
     * Delete a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @throws RuntimeException if the user is not found.
     */
    @Override
    public void deleteUserById(int id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    /**
     * Retrieve a list of all users.
     *
     * @return A list of user DTOs containing user information.
     */
    @Override
    public List<UserDTO> findAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users
                .stream().map(user -> UserDTO.builder()
                        .userId(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build())
                .toList();
        return userDTOs;
    }


    /**
     * Load user details by username for Spring Security.
     *
     * @param username The username to load user details for.
     * @return UserDetails object containing user details for authentication.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        List<Integer> userRoleIds = userRolesRepository.findRoleIdsByUserUserId(user.getUserId());
        List<String> userRolesnames = roleRepository.findRoleNamesByRoleIdsIn(userRoleIds);
        Set<String> permissionNames = rolesPermissionRepository.findPermissionNamesByRoleIdsIn(userRoleIds);

        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(permissionNames);

        if(userRolesnames.contains("SUPERADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SUPERADMIN"));
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName() , user.getPassword() , authorities);
    }

    /**
     * Map permission names to authorities.
     *
     * @param permissionNames The collection of permission names.
     * @return A collection of SimpleGrantedAuthority objects representing authorities.
     */
    public Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<String> permissionNames) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissionNames) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }
}

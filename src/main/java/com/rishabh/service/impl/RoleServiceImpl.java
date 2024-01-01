package com.rishabh.service.impl;

import com.rishabh.repository.RoleRepository;
import com.rishabh.dto.RoleDTO;
import com.rishabh.entity.Role;
import com.rishabh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    //Constructor for RoleServiceImpl, with dependency injection
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves all roles from the repository and converts them into RoleDTO objects.
     *
     * @return A list of RoleDTO objects representing all roles.
     */
    @Override
    public List<RoleDTO> findAllRoles() {
        //Retrieve all roles from the roleRepository
        List<Role> roles = roleRepository.findAll();

        //Use Java Streams to map Role objects to RoleDTO objects
        List<RoleDTO> roleDTOS = roles.stream()
                .map(role -> RoleDTO.builder()
                        .roleId(role.getRoleId())
                        .roleName(role.getRoleName())
                        .build()).
                toList();
        // Return the list of RoleDTOs
        return roleDTOS;
    }

    /**
     * Saves a new role to the repository.
     *
     * @param roleDTO The RoleDTO object containing role information.
     */
    @Override
    public void saveRole(RoleDTO roleDTO) {

        //Create a new Role object using a builder pattern
        Role role = Role.builder()
                .roleName(roleDTO.getRoleName().toUpperCase())
                .build();
        //Save the Role object to the roleRepository
        roleRepository.save(role);
    }


    /**
     * Updates an existing role in the repository.
     *
     * @param id      The ID of the role to be updated.
     * @param roleDTO The RoleDTO object containing updated role information.
     */
    @Override
    public void updateRole(int id, RoleDTO roleDTO) {
        // Create a new Role object using a builder pattern
        Role role = Role.builder()
                .roleId(id)
                .roleName(roleDTO.getRoleName().toUpperCase())
                .build();
        // Save the updated Role to the roleRepository
        roleRepository.save(role);
    }

    /**
     * Deletes a role by its ID from the repository.
     *
     * @param id The ID of the role to be deleted.
     * @throws RuntimeException If the role with the given ID is not found.
     */
    @Override
    public void deleteRoleById(int id) {

       //fetch the role if not found throw exception
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Delete the retrieved Role object from the roleRepository
        roleRepository.delete(role);
    }
}


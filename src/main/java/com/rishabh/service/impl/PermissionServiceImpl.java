package com.rishabh.service.impl;

import com.rishabh.repository.PermissionRepository;
import com.rishabh.dto.PermissionDTO;
import com.rishabh.entity.Permission;
import com.rishabh.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl  implements PermissionService {


    private final PermissionRepository permissionRepository;

    @Autowired
    PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository= permissionRepository;
    }

    /**
     * Retrieves all permissions from the repository and converts them into PermissionDTO objects.
     *
     * @return A list of PermissionDTO objects representing all permissions.
     */
    @Override
    public List<PermissionDTO> findAllPermissions() {

       //fetch all the permissions
        List<Permission> permissions = permissionRepository.findAll();

        //Use Java Streams to map Permission objects to PermissionDTO objects
        List<PermissionDTO> permissionDTOS = permissions.stream()       //stream permissions and map it with permissionDTO
                .map(permission -> PermissionDTO.builder()
                        .permissionId(permission.getPermissionId())
                        .permissionName(permission.getPermissionName())
                        .build())
                .toList();
        //return permissionDTOS
        return permissionDTOS;
    }

    /**
     * Saves a new permission to the repository.
     *
     * @param permissionDTO The PermissionDTO object containing permission information.
     */
    @Override
    public void savePermission(PermissionDTO permissionDTO) {

        //Create a new Permission object using a builder pattern
        Permission permission = Permission.builder()
                .permissionName(permissionDTO.getPermissionName())
                .build();
        //save the  permissions
        permissionRepository.save(permission);
    }

    /**
     * Updates an existing permission in the repository.
     *
     * @param id       The ID of the permission to be updated.
     * @param permissionDTO The PermissionDTO object containing updated permission information.
     */
    @Override
    public void updatePermission(int id, PermissionDTO permissionDTO) {
        //Create a new Permission object using a builder pattern
        Permission permission = Permission.builder()
                .permissionId(id)
                .permissionName(permissionDTO.getPermissionName())
                .build();
        //update the permission
        permissionRepository.save(permission);
    }

    /**
     * Deletes a permission by its ID from the repository.
     *
     * @param id The ID of the permission to be deleted.
     * @throws RuntimeException If the permission with the given ID is not found.
     */
    @Override
    public void deleteByPermissionId(int id) {

        //fetch the permissions by the Id from the PermissionRepository
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found wit Id : " + id));

        //delete the permissions
        permissionRepository.delete(permission);
    }

}
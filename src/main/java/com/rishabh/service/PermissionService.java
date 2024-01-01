package com.rishabh.service;

import com.rishabh.dto.PermissionDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface PermissionService {

    List<PermissionDTO> findAllPermissions();

    void savePermission(PermissionDTO permissionDTO);

    void updatePermission(int id , PermissionDTO permissionDTO);

    void deleteByPermissionId(int id);
}


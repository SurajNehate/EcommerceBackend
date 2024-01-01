package com.rishabh.service;

import com.rishabh.dto.RoleDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RoleService {

  List<RoleDTO> findAllRoles();

  void saveRole(RoleDTO roleDTO);

  void updateRole(int id , RoleDTO roleDTO);

  void deleteRoleById(int id);

}

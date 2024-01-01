package com.rishabh.service;

import com.rishabh.dto.UserRolesDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserRolesService {

    List<UserRolesDTO> findAllUserRolesByUserId(int userId);

    void assignRolestoUser(int userId, List<Integer> roleIds);

    void deleteRolesFromUser(int userId, List<Integer> roleIds);

}
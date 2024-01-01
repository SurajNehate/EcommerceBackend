package com.rishabh.service;

import com.rishabh.dto.UserDTO;
import com.rishabh.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserService extends UserDetailsService {

    List<UserDTO> findAllUsers();

    User findByUserName(String username);

    UserDTO findUserById(int id);

    void saveUser(UserDTO userDTO);

    void updateUser( int id , UserDTO userDTO);

    void deleteUserById(int id);

}

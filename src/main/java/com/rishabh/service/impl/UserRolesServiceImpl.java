package com.rishabh.service.impl;

import com.rishabh.repository.RoleRepository;
import com.rishabh.repository.UserRepository;
import com.rishabh.repository.UserRolesRepository;
import com.rishabh.dto.UserRolesDTO;
import com.rishabh.entity.Role;
import com.rishabh.entity.User;
import com.rishabh.entity.UserRoleId;
import com.rishabh.entity.UserRoles;
import com.rishabh.service.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
@Service
public class UserRolesServiceImpl implements UserRolesService {



    private final UserRolesRepository userRolesRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

     // Construct
    @Autowired
    public UserRolesServiceImpl(UserRolesRepository userRoleRepository , UserRepository userRepository , RoleRepository roleRepository){
        this.userRolesRepository =userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Finds user roles by user ID.
     *
     * @param userId The ID of the user to find roles for.
     * @return A list of UserRolesDTO objects representing roles assigned to the user.
     * @throws IllegalArgumentException If no roles were found for the specified user.
     */
    @Override
    public List<UserRolesDTO> findAllUserRolesByUserId(int userId) {

       // Fetch UserRoles for the specified userId from the userRolesRepository
        List<UserRoles> userRoles = userRolesRepository.findUserRolesByUserId(userId);

        //Check if userRoles is not null
        if (userRoles != null) {

            //Map UserRoles objects to UserRolesDTO objects using Java Streams
            List<UserRolesDTO> userRolesDTOList = userRoles.stream()
                    .map(userRole -> UserRolesDTO.builder()
                            .userId(userRole.getId().getUserId())
                            .roleId(userRole.getId().getRoleId())
                            .build())
                    .toList();
            // return the List of UserRoles
            return userRolesDTOList;
        }
        else {
            // Throw exception if UserRoles not found in the list
            throw new IllegalArgumentException("No roles found for UserId :  " + userId );
        }
    }

    /**
     * Assigns roles to a user.
     *
     * @param userId  The ID of the user to assign roles to.
     * @param roleIds The list of role IDs to assign to the user.
     * @throws RuntimeException If the specified user or role is not found.
     */

    @Override
    public void assignRolestoUser(int userId, List<Integer> roleIds) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with Id : " +userId));

        List<UserRoles> userRolesList = new ArrayList<>();
        for (int roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found with Id : " + roleId));

            UserRoles  userRoles = UserRoles.builder()
                            .id(new UserRoleId(userId , roleId))
                            .user(user)
                            .role(role)
                            .build();
            userRolesList.add(userRoles);
        }
        userRolesRepository.saveAll(userRolesList);
    }


    /**
     * Deletes roles from a user.
     *
     * @param userId  The ID of the user to delete roles from.
     * @param roleIds The list of role IDs to delete from the user.
     * @throws RuntimeException If the specified user is not found.
     */
    @Override
    public void deleteRolesFromUser(int userId, List<Integer> roleIds) {

         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new RuntimeException("User not found with Id : " +userId));

        for (int roleId : roleIds) {
            userRolesRepository.deleteByUserAndRole(userId, roleId);
        }
    }
}


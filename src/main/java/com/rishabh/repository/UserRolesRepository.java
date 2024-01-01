package com.rishabh.repository;

import com.rishabh.entity.UserRoleId;
import com.rishabh.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, UserRoleId> {

    @Query("SELECT ur.id.roleId FROM UserRoles ur WHERE ur.id.userId = :userId")
    List<Integer> findRoleIdsByUserUserId(@Param("userId") int userId);

    @Query("SELECT ur FROM UserRoles ur WHERE ur.id.userId = :userId")
    List<UserRoles> findUserRolesByUserId(@Param("userId") int userId);

    @Modifying
    @Query("DELETE FROM UserRoles ur WHERE ur.id.userId = :userId AND ur.id.roleId = :roleId")
    void deleteByUserAndRole(@Param("userId") int userId ,@Param("roleId") int roleId);

    @Query("select ur.id.roleId from UserRoles ur inner join User u on u.userId = ur.id.userId where u.userName = :userName")
    List<Integer> findRoleIdsByUserName(@Param("userName") String userName);

}

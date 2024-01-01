package com.rishabh.repository;

import com.rishabh.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role ,Integer> {

    @Query("SELECT roleName FROM Role WHERE roleId IN :userRoleIds")
    List<String> findRoleNamesByRoleIdsIn(@Param("userRoleIds") List<Integer> userRoleIds);

    Role findByRoleName(String user);
}

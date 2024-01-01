package com.rishabh.repository;

import com.rishabh.entity.RolePermissionId;
import com.rishabh.entity.RolesPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RolesPermissionRepository extends JpaRepository<RolesPermission, RolePermissionId> {


    @Query("SELECT rp FROM RolesPermission rp WHERE rp.id.roleId = :roleId")
    List<RolesPermission> findPermissionIdByRoleId(@Param("roleId") int roleId);

    @Query("select distinct p.permissionName from RolesPermission rp inner join Permission p on rp.id.permissionId = p.permissionId where rp.id.roleId in :roleIds")
    Set<String> findPermissionNamesByRoleIdsIn(@Param("roleIds") List<Integer> roleIds);

    @Modifying
    @Query("DELETE FROM RolesPermission rp WHERE rp.id.roleId = :roleId AND rp.id.permissionId = :permissionId")
    void deleteByRoleAndPermission(@Param("roleId") int roleId, @Param("permissionId") int permissionId);
}

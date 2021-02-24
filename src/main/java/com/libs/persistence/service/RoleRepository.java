package com.libs.persistence.service;

import org.springframework.stereotype.Repository;

import com.libs.persistence.entity.SysRole;
import com.libs.util.repository.BaseCrudRepository;
import com.libs.util.repository.BaseRepository;

@Repository
public interface RoleRepository extends BaseCrudRepository<SysRole, String>, BaseRepository<SysRole, String>{
	SysRole findByRoleId(String roleId);
}

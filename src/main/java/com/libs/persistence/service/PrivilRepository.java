package com.libs.persistence.service;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.libs.persistence.entity.SysPrivilege;
import com.libs.util.repository.BaseCrudRepository;
import com.libs.util.repository.BaseRepository;

@Repository
public interface PrivilRepository extends BaseCrudRepository<SysPrivilege, String>, BaseRepository<SysPrivilege, String> {

	List<SysPrivilege> findAllByIdnavbarAndRoleId(String idNavbars, String roleId);
	
	List<SysPrivilege> findAllByRoleId(String roleId);
}

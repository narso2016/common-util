package com.libs.persistence.service;


import org.springframework.stereotype.Repository;

import com.libs.persistence.entity.SysBranch;
import com.libs.persistence.entity.SysUser;
import com.libs.util.repository.BaseCrudRepository;
import com.libs.util.repository.BaseRepository;

@Repository
public interface UserRepository extends BaseCrudRepository<SysUser, String>, BaseRepository<SysUser, String>{

	Iterable<SysUser> findAllBySysBranch(SysBranch sysBranch);
	
	long countBySysBranch(SysBranch sysBranch);
	
	SysUser findOneByUserNameAndPassword(String userName, String password);

	SysUser findOneByEmail(String email);
	
	Iterable<SysUser> findAllByEmail(String email);

	SysUser findOneByEmailAndUserName(String email, String userId);

	SysUser findOneByUserName(String userId);

}

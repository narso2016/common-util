package com.libs.persistence.service;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.libs.persistence.entity.SysNavbar;
import com.libs.util.repository.BaseRepository;

@Repository
public interface NavbarRepository extends BaseRepository<SysNavbar, String> {
	
	List<SysNavbar> findByIdnavbarInAndActiveOrderBySeqorderAsc(List<String> idNavbars, boolean active);

	SysNavbar findOneByIdnavbar(String idNavbars);
	
}

package com.libs.persistence.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.libs.persistence.entity.NavbarChild;
import com.libs.util.repository.BaseCrudRepository;
import com.libs.util.repository.BaseRepository;

@Repository
public interface NavbarChildRepository extends BaseCrudRepository<NavbarChild, String>, BaseRepository<NavbarChild, String> {
	NavbarChild findByIdnavchild(String idNavChild);
	List<NavbarChild> findAllByIdnavchildIn(List<String> ch);

	List<NavbarChild> findAllByIdparentAndActive(String idNavbars, boolean active);

}

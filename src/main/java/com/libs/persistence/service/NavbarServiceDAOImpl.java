package com.libs.persistence.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import com.libs.persistence.entity.NavbarChild;
import com.libs.persistence.entity.SysNavbar;
import com.libs.persistence.entity.SysPrivilege;
import com.libs.util.exception.AppException;
import com.libs.util.repository.QueryBuilder;

public class NavbarServiceDAOImpl implements NavbarServiceDAO{

	@PersistenceContext
	EntityManager em;

	@Autowired
	NavbarRepository repository;
	
	
	@Autowired
	PrivilRepository repo;
	
	@Autowired
	NavbarChildRepository rep;

	private QueryBuilder<SysNavbar> queryBuilder = new QueryBuilder<SysNavbar>();
	
	@Override
	public List<SysNavbar> getAllNavbar(Map<String, Object> criteria) throws AppException {
		List<SysNavbar> list = new ArrayList<SysNavbar>();
		@SuppressWarnings("deprecation")
		Sort sorter = new Sort("seqorder");
		repository.findAll(queryBuilder.queryBuilder(criteria), sorter).forEach((sysNavbar) -> list.add(sysNavbar));
		return list;
	}

	@Override
	public List<SysNavbar> getAllNavbarById(List<String> idNavbars) throws AppException {
		List<SysNavbar> list = new ArrayList<SysNavbar>();
		repository.findByIdnavbarInAndActiveOrderBySeqorderAsc(idNavbars, true).forEach((idnavbar) -> list.add(idnavbar));
		return list;
	}

	

	@Override
	public List<SysPrivilege> getchild(String idNavbars, String roleId) throws AppException {
		// TODO Auto-generated method stub
		return repo.findAllByIdnavbarAndRoleId(idNavbars,roleId);
	}

	@Override
	public SysNavbar getSingle(String idNavbars) {
		// TODO Auto-generated method stub
		return repository.findOneByIdnavbar(idNavbars);
	}

	@Override
	public List<NavbarChild> getchild(List<String> ch) {
		// TODO Auto-generated method stub
		return rep.findAllByIdnavchildIn(ch);
	}

	@Override
	public List<NavbarChild> getchilds(String idNavbars) throws AppException {
		// TODO Auto-generated method stub
		return rep.findAllByIdparentAndActive(idNavbars, true);
	}

}

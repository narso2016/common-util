package com.libs.persistence.service;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.libs.persistence.entity.SysBranch;
import com.libs.util.repository.BaseRepository;

@Repository
public interface BranchRepository extends BaseRepository<SysBranch, String>{
	SysBranch findOneByBranchcode(String branchcode);
	
	List<SysBranch> findAllByChain(Map<String, Object> criteria);
	
	List<SysBranch> findByChainContaining(String chain);
	
	List<SysBranch> findByBranchcodeIn(List<String> branchCodes);
}

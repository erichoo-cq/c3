package com.c3.base.model.repository.sm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.c3.base.model.entity.sm.C3SmOrg;

@Repository
public interface SmOrgRepository extends JpaRepository<C3SmOrg, String>,
	JpaSpecificationExecutor<C3SmOrg>{

}

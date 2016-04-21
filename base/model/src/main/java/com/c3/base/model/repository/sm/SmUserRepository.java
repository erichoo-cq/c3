package com.c3.base.model.repository.sm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.c3.base.model.entity.sm.C3SmUser;

@Repository
public interface SmUserRepository extends JpaRepository<C3SmUser, Integer>, 
	JpaSpecificationExecutor<C3SmUser> {

	@Query("select u from C3SmUser u where u.userName = :userName or u.email = :userName")
	public C3SmUser findFirstByUserNameOrEmail(@Param("userName") String userName);

}

package com.c3.base.model.repository.sm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.c3.base.model.entity.sm.C3SmResourceButton;

/**
 * description: 系统实际人员spring-data持久化
 *
 * @version 2016年3月21日 上午9:27:11
 * @see
 * modify content------------author------------date
 */
@Repository
public interface SmResourceButtonRepository extends JpaRepository<C3SmResourceButton, Integer>,
	JpaSpecificationExecutor<C3SmResourceButton> {

}

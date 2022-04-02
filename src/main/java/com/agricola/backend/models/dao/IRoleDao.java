package com.agricola.backend.models.dao;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agricola.backend.models.entity.Role;


@Repository
public interface IRoleDao extends CrudRepository<Role,Long> {


	
	
}

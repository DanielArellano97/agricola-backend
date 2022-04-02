package com.agricola.backend.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.agricola.backend.models.dao.IRoleDao;
import com.agricola.backend.models.entity.Role;

@Service
public class RoleServiceImpl implements IRoleService{

	@Autowired
	private IRoleDao roleDao;


	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return (List<Role>) roleDao.findAll();
	}
}

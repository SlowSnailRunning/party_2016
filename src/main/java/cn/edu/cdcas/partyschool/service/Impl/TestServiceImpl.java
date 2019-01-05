package cn.edu.cdcas.partyschool.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.cdcas.partyschool.mapper.SysUserMapper;
import cn.edu.cdcas.partyschool.model.SysUser;
import cn.edu.cdcas.partyschool.service.TestService;

@Service
public class TestServiceImpl implements TestService{
	@Autowired
	private SysUserMapper sysUserMapper;
	
	public List<SysUser> findAllUser() throws Exception {
		return sysUserMapper.selectAllUser();
	}
}

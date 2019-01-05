package cdcas.party.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdcas.party.mapper.SysUserMapper;
import cdcas.party.model.SysUser;
import cdcas.party.service.TestService;

@Service
public class TestServiceImpl implements TestService{
	@Autowired
	private SysUserMapper sysUserMapper;
	
	public List<SysUser> findAllUser() throws Exception {
		return sysUserMapper.selectAllUser();
	}
}

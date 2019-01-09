package cn.edu.cdcas.partyschool.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.cdcas.partyschool.mapper.SysUserMapperSnail;
import cn.edu.cdcas.partyschool.model.SysUserSnail;
import cn.edu.cdcas.partyschool.service.TestService;

@Service
public class TestServiceImpl implements TestService{
	@Autowired
	private SysUserMapperSnail sysUserMapper;
	
	public List<SysUserSnail> findAllUser() throws Exception {
		return sysUserMapper.selectAllUser();
	}
}

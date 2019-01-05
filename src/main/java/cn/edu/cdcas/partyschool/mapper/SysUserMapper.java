package cn.edu.cdcas.partyschool.mapper;

import java.util.List;

import cn.edu.cdcas.partyschool.model.SysUser;

public interface SysUserMapper {
	//输入中文以做测试
	public List<SysUser> selectAllUser() throws Exception;
}

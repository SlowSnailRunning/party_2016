package cn.edu.cdcas.partyschool.mapper;

import java.util.List;

import cn.edu.cdcas.partyschool.model.SysUser;

public interface SysUserMapper {
	public List<SysUser> selectAllUser() throws Exception;
}

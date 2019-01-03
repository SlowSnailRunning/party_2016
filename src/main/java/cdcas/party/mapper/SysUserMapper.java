package cdcas.party.mapper;

import java.util.List;

import cdcas.party.model.SysUser;

public interface SysUserMapper {
	public List<SysUser> selectAllUser() throws Exception;
}

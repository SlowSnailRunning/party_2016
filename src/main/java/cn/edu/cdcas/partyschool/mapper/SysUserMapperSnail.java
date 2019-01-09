package cn.edu.cdcas.partyschool.mapper;

import java.util.List;

import cn.edu.cdcas.partyschool.model.SysUserSnail;

public interface SysUserMapperSnail {
	//输入中文以做测试
	public List<SysUserSnail> selectAllUser() throws Exception;
}

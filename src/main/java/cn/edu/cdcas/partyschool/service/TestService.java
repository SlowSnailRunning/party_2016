package cn.edu.cdcas.partyschool.service;

import java.util.List;
import cn.edu.cdcas.partyschool.model.SysUser;

public interface TestService {
	public List<SysUser> findAllUser() throws Exception;
}

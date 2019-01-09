package cn.edu.cdcas.partyschool.service;

import java.util.List;
import cn.edu.cdcas.partyschool.model.SysUserSnail;

public interface TestService {
	public List<SysUserSnail> findAllUser() throws Exception;
}

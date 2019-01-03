package cdcas.party.service;

import java.util.List;
import cdcas.party.model.SysUser;

public interface TestService {
	public List<SysUser> findAllUser() throws Exception;
}

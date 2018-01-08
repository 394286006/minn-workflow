package p.minn.workflow.service;

import java.util.List;
import java.util.Map;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import p.minn.common.utils.MyGsonMap;
import p.minn.common.utils.Page;
import p.minn.privilege.entity.Globalization;
import p.minn.privilege.repository.GlobalizationDao;
import p.minn.common.utils.UtilCommon;
import p.minn.security.cas.springsecurity.auth.User;
import p.minn.workflow.entity.ProcessAudit;
import p.minn.workflow.repository.ProcessAuditDao;
import p.minn.workflow.repository.ProcessAuditStatusDao;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment
 *
 */
@Service
public class ProcessAuditService {

	@Autowired
	private ProcessAuditDao processAuditDao;
	
	@Autowired
    private ProcessAuditStatusDao processAuditStatusDao;
	 
	@Autowired
	private GlobalizationDao globalizationDao;
	

	public Object query(String messageBody, String lang) {
		// TODO Auto-generated method stub
		Page page=(Page) UtilCommon.gson2T(messageBody, Page.class);
		Map<String,String> condition=UtilCommon.getCondition(page);
		
		List<Map<String,Object>> list=processAuditStatusDao.query(lang,page,condition);
	    page.setResult(list);
	
		return page;
	}





	public void save(User user,String messageBody, String lang) {
		// TODO Auto-generated method stub
		MyGsonMap<Map,Map> msm=MyGsonMap.getInstance(messageBody,Map.class, Map.class); 
		ProcessAudit vobj=null;
		//vobj.setCreateid(user.getId());
		
	//	processAuditStatusDao.save(vobj);
		Map map=msm.gson2Map();
		Globalization glz=new Globalization();
		//glz.setTableid(vobj.getId().toString());
		glz.setCreateid(user.getId());
		glz.setName(map.get("name").toString());
		glz.setLanguage(map.get("language").toString());
		glz.setTablename("wf_processdefinition");
		globalizationDao.save(glz);
	}


}

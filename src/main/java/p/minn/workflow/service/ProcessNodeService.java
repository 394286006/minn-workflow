package p.minn.workflow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import p.minn.common.utils.MyGsonMap;
import p.minn.common.utils.Page;
import p.minn.privilege.entity.Globalization;
import p.minn.privilege.entity.IdEntity;
import p.minn.privilege.repository.GlobalizationDao;
import p.minn.common.utils.UtilCommon;
import p.minn.security.cas.springsecurity.auth.User;
import p.minn.workflow.entity.ProcessNode;
import p.minn.workflow.repository.ProcessNodeDao;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment
 *
 */
@Service
public class ProcessNodeService {

	@Autowired
	private ProcessNodeDao dao;
	 
	@Autowired
	private GlobalizationDao globalizationDao;
	
	   public Map<String,Object> checkCode(String code,String type) throws Exception{
	        
	        Map<String,Object>  rs=new HashMap<String, Object>();
	        int c=dao.checkCode(code);
	        rs.put("count", c);
	        
	        return rs;
	    }


	    public Object query(String messageBody, String lang) {
	        // TODO Auto-generated method stub
	        Page page=(Page) UtilCommon.gson2T(messageBody, Page.class);
	        Map<String,String> condition=UtilCommon.getCondition(page);
	        int total=dao.getTotal(lang,condition);
	        page.setPage(page.getPage()+1);
	        page.setTotal(total);
	        
	        List<Map<String,Object>> list=dao.query(lang,page,condition);
	        page.setResult(list);
	    
	        return page;
	    }
    public void update(User user,String messageBody, String lang) {
      // TODO Auto-generated method stub
      MyGsonMap<Map,ProcessNode> msm=MyGsonMap.getInstance(messageBody,Map.class, ProcessNode.class); 
      ProcessNode vobj=msm.gson2T(ProcessNode.class);
      Map map=msm.gson2Map();
      vobj.setUpdateid(user.getId());
      dao.update(vobj);
      Globalization glz=new Globalization();
      glz.setUpdateid(user.getId());
      glz.setId(Double.valueOf(map.get("gid").toString()).intValue());
      glz.setName(map.get("name").toString());
      globalizationDao.update(glz);
  }


  public void save(User user,String messageBody, String lang) {
      // TODO Auto-generated method stub
      MyGsonMap<Map,ProcessNode> msm=MyGsonMap.getInstance(messageBody,Map.class, ProcessNode.class); 
      ProcessNode vobj=msm.gson2T(ProcessNode.class);
      Map map=msm.gson2Map();
      vobj.setCreateid(user.getId());
     // if(dao.checkCode(vobj.getCode())>0)
       //   throw new RuntimeException("code:"+vobj.getCode()+", exists!");
      dao.save(vobj);
      
      Globalization glz=new Globalization();
      glz.setTableid(vobj.getId().toString());
      glz.setCreateid(user.getId());
      glz.setName(map.get("name").toString());
      glz.setLanguage(lang);
      glz.setTablecolumn("name");
      glz.setTablename("wf_processnode");
      globalizationDao.save(glz);
      
  }


  public void delete(String messageBody) {
      // TODO Auto-generated method stub
      IdEntity idEntity=(IdEntity) UtilCommon.gson2T(messageBody,IdEntity.class);
      globalizationDao.deleteByTableId(idEntity.getId(),"wf_processnode");
      dao.delete(idEntity);
  }


public List<Map<String,Object>> queryTree(String messageBody, String lang) {
  // TODO Auto-generated method stub
    Map<String,Object> condition=(Map<String, Object>) UtilCommon.gson2Map(messageBody);
    List<Map<String,Object>> list=dao.queryTree(lang,condition);
    
    return UtilCommon.createTreeMenu(list,"-1");
}
}

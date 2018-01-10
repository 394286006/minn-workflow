package p.minn.workflow.service;

import java.util.HashMap;
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
import p.minn.workflow.entity.ProcessDefinition;
import p.minn.workflow.entity.ProcessModel;
import p.minn.workflow.repository.ProcessDefinitionDao;
import p.minn.workflow.repository.ProcessModelDao;
import p.minn.workflow.utils.GojsTransform;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment
 *
 */
@Service
public class ProcessDefinitionService {

	@Autowired
	private ProcessDefinitionDao dao;
	
	@Autowired
	private ProcessModelDao modelDao;
	 
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
		MyGsonMap<Map,ProcessDefinition> msm=MyGsonMap.getInstance(messageBody,Map.class, ProcessDefinition.class); 
		ProcessDefinition vobj=msm.gson2T(ProcessDefinition.class);
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
		MyGsonMap<Map,ProcessDefinition> msm=MyGsonMap.getInstance(messageBody,Map.class, ProcessDefinition.class); 
		ProcessDefinition vobj=msm.gson2T(ProcessDefinition.class);
		Map map=msm.gson2Map();
		if(map.containsKey("processId")) {
		  dao.deleteByProcessId(map.get("processId").toString());
		}
		ProcessDefinition pobj=null;
		if(map.containsKey("modelExists")) {
			List<ProcessDefinition> list=GojsTransform.transform(user.getId(),map.get("processId").toString(), map.get("model").toString());
			for(ProcessDefinition pd:list) {
	        	  dao.save(pd);
	        }
			saveModel(map);
	     }else {
	    	   if(vobj.getIds()!=null){
	             String ids[]=vobj.getIds().split(",");
	             String names[]=vobj.getName().split(",");
	             String pIds[]=vobj.getPIds().split(",");
	             String sorts[]=vobj.getSorts().split(",");
	             String pnIds[]=vobj.getPnIds().split(",");
	             String codes[]=UtilCommon.split(vobj.getCodes (), ids.length);
	             String gIds[]=UtilCommon.split(vobj.getGIds (), ids.length);
	             String actives[]=UtilCommon.split(vobj.getActives (), ids.length,"1");
	             
	             for(int i=0;i<ids.length;i++){
	                 if(pIds[i].equals("-2")){
	                   continue;
	                 }
	                 pobj= dao.findEntityById(ids[i]);
	                 if(pobj==null){
	                   pobj=new ProcessDefinition();
	                   pobj.setId(ids[i]);
	                   pobj.setCreateid(user.getId());
	                   pobj.setPId(pIds[i]);
	                   pobj.setPnId(Integer.valueOf(pnIds[i]));
	                   pobj.setSort(Integer.valueOf(sorts[i]));
	                   pobj.setActive(Integer.valueOf(actives[i]));
	                   pobj.setCode(codes[i]);
	                   dao.save(pobj);
	                   
	                   Globalization glz=new Globalization();
	                   glz.setTableid(pobj.getId());
	                   glz.setCreateid(user.getId());
	                   glz.setName(names[i].toString());
	                   glz.setLanguage(lang);
	                   glz.setTablecolumn("name");
	                   glz.setTablename("wf_processdefinition");
	                   globalizationDao.save(glz);
	                     
	                 }else{
	                   pobj.setPId(pIds[i]);
	                   pobj.setSort(Integer.valueOf(sorts[i]));
	                   pobj.setActive(Integer.valueOf(actives[i]));
	                   pobj.setUpdateid(user.getId());
	                   pobj.setCode(codes[i]);
	                   dao.update(pobj);
	                   
	                   Globalization glz=new Globalization();
	                   glz.setUpdateid(user.getId());
	                   glz.setId(Integer.valueOf(gIds[i]));
	                   glz.setName(names[i].toString());
	                   glz.setLanguage(lang);
	                   globalizationDao.update(glz);
	                 }
	             }
	             
	         }else{
	           vobj.setCreateid(user.getId());
	           if(dao.checkCode(vobj.getCode())>0)
	               throw new RuntimeException("code:"+vobj.getCode()+", exists!");
	           vobj.setPnId(-2);
	           String id=dao.getMaxId(vobj.getPId());
	           vobj.setId(vobj.getPId()+"_"+id);
	           dao.save(vobj);
	           
	           Globalization glz=new Globalization();
	           glz.setTableid(vobj.getId().toString());
	           glz.setCreateid(user.getId());
	           glz.setName(map.get("name").toString());
	           glz.setLanguage(lang);
	           glz.setTablecolumn("name");
	           glz.setTablename("wf_processdefinition");
	           globalizationDao.save(glz);
	         }
	     }
	}

	public void saveModel(Map map) {
		// TODO Auto-generated method stub
		ProcessModel vobj=new ProcessModel(map.get("processId").toString(),map.get("model").toString());
		if(map.get("modelExists").equals("true")) {
			modelDao.updateModel(vobj);
		}else {
		    modelDao.saveModel(vobj);
		}
	}
	
	public ProcessModel getModel(String messageBody, String lang) {
		// TODO Auto-generated method stub
		ProcessModel data=null;
		 Map<String,Object> condition=(Map<String, Object>) UtilCommon.gson2Map(messageBody);
		 data=modelDao.getModelByProcessId(condition.get("processId").toString());
		return data;
	}


	public void delete(String messageBody) {
		// TODO Auto-generated method stub
		Map<String,Object> condition=(Map<String, Object>) UtilCommon.gson2Map(messageBody);
		globalizationDao.deleteByTableId(condition.get("id").toString(),"wf_processdefinition");
		dao.delete(condition.get("id").toString());
		modelDao.deleteByProcessId(condition.get("id").toString());
	}


  public List<Map<String,Object>> queryTree(String messageBody, String lang) {
    // TODO Auto-generated method stub
      Map<String,Object> condition=(Map<String, Object>) UtilCommon.gson2Map(messageBody);
      List<Map<String,Object>> list=dao.queryTree(lang,condition);
      return UtilCommon.createTreeMenu(list,"-2");
  }
}

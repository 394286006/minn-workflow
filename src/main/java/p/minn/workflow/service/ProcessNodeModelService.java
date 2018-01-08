package p.minn.workflow.service;

import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import p.minn.common.utils.MyGsonMap;
import p.minn.privilege.entity.Globalization;
import p.minn.privilege.repository.GlobalizationDao;
import p.minn.common.utils.UtilCommon;
import p.minn.security.cas.springsecurity.auth.User;
import p.minn.workflow.entity.ProcessNodeModel;
import p.minn.workflow.repository.ProcessNodeModelDao;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment
 *
 */
@Service
public class ProcessNodeModelService {

	@Autowired
	private ProcessNodeModelDao dao;
	 
	@Autowired
	private GlobalizationDao globalizationDao;
	
	/**
	 * 获取菜单
	 * @throws Exception 
	 *//*
	public List<Map<String,Object>> getPrivateMenu(User user,String lang) throws Exception{
		
		List<Map<String,Object>> list=null;//processDefinitionDao.getPrivateMenu(lang,user);
		List<Map<String,Object>> target=createTreeMenu(list,"-1");
		list=target;
		return list;
	}
	
	
	*//**
	 * 获取菜单
	 *//*
	public List<Map<String,Object>> getResource(User user,String lang) throws Exception{

		List<Map<String,Object>> list=menuDao.getResource(lang);
			List<Map<String,Object>> target=createTreeMenu(list,"-2");
			list=target;
		return list;
	}
	
	private List<Map<String,Object>> createTreeMenu(List<Map<String,Object>> source,String parent){
		List<Map<String,Object>> children=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:source){
			if(map.get("pid").toString().equals(parent)){
				if(map.get("type_v")!=null&&map.get("type_v").toString().equals("-1"))
				map.put("children", createTreeMenu(source,map.get("id").toString()));
				children.add(map);
			}
		}
		return children;
	}
	*/
	
    public void update(User user,String messageBody, String lang) {
      // TODO Auto-generated method stub
      MyGsonMap<Map,ProcessNodeModel> msm=MyGsonMap.getInstance(messageBody,Map.class, ProcessNodeModel.class); 
      ProcessNodeModel vobj=msm.gson2T(ProcessNodeModel.class);
      Map map=msm.gson2Map();
      dao.update(vobj);
      Globalization glz=new Globalization();
      glz.setUpdateid(user.getId());
      glz.setId(Double.valueOf(map.get("gid").toString()).intValue());
      glz.setName(map.get("name").toString());
      globalizationDao.update(glz);
  }


  public void save(User user,String messageBody, String lang) {
      // TODO Auto-generated method stub
      MyGsonMap<Map,ProcessNodeModel> msm=MyGsonMap.getInstance(messageBody,Map.class, ProcessNodeModel.class); 
      ProcessNodeModel vobj=msm.gson2T(ProcessNodeModel.class);
      Map map=msm.gson2Map();
      dao.deleteByPnId(vobj.getPnId());
      
      String ids[]=vobj.getIds().split(",");
      String pIds[]=vobj.getPIds().split(",");
      String types[]=UtilCommon.split(vobj.getTypes(), ids.length);
      ProcessNodeModel pobj=null;
      for(int i=0;i<ids.length;i++){
           if(pIds[i].equals("-1")){
             continue;
           }
            pobj=new ProcessNodeModel();
            pobj.setId(ids[i]);
            pobj.setPId(pIds[i]);
            pobj.setPnId(vobj.getPnId());
            pobj.setType(types[i]);
            String deptIds[]=ids[i].split("_");
            pobj.setDeptId(Integer.valueOf(deptIds[deptIds.length-1]));
            dao.save(pobj);
      }
      

      
  }


public List<Map<String,Object>> queryTree(String messageBody, String lang) {
  // TODO Auto-generated method stub
    Map<String,Object> condition=(Map<String, Object>) UtilCommon.gson2Map(messageBody);
    List<Map<String,Object>> list=dao.queryTree(lang,condition);
    
    return UtilCommon.createTreeMenu(list,"-1");
}
}

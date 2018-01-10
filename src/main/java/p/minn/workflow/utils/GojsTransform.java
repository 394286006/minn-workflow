package p.minn.workflow.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import p.minn.common.utils.UtilCommon;
import p.minn.workflow.entity.ProcessDefinition;

/**
 * 
 * @author minn 
 * @QQ:3942986006
 * @comment 
 * 
 */
public class GojsTransform {
	
	public static List<ProcessDefinition> transform(Integer uid,String pid,String model){
		List<ProcessDefinition> rs=new ArrayList<ProcessDefinition>();
		Map<String,Object>  data=(Map<String, Object>) UtilCommon.gson2Map(model);
		Map keys=new HashMap();

		Map<String,ProcessDefinition> paths=new HashMap<String,ProcessDefinition>();
		List nodes=(List) data.get("nodeDataArray");
		for(int i=0;i<nodes.size();i++) {
			Map node=(Map) nodes.get(i);
			if(!keys.containsKey(node.get("code").toString())) {
				keys.put(node.get("code").toString(), node);
			}	
			if(node.get("code").toString().indexOf("N")==0) {
				String k=node.get("id")+"@"+node.get("code")+"@";
				ProcessDefinition pd=new ProcessDefinition();
				pd.setCreateid(uid);
				pd.setCode(node.get("code").toString());
				pd.setActive(1);
				pd.setId(pid+"_"+Double.valueOf(node.get("id").toString()).intValue());
				pd.setPnId(Double.valueOf(node.get("id").toString()).intValue());
				pd.setPId(pid);
				pd.setSort(Double.valueOf(node.get("sort").toString()).intValue());
				pd.setName(node.get("text").toString());
				rs.add(pd);
				paths.put(k, pd);
			}
		}
		
		List list=(List) data.get("linkDataArray");
		Iterator it=paths.keySet().iterator();
		while(it.hasNext()) {
			String k=(String) it.next();
			ProcessDefinition node=paths.get(k);
			findPath(uid,list,node.getPnId()+"@"+node.getCode()+"@",node,rs,keys);
		}
		return rs;
	}

	public static void findPath(Integer uid,List datas,String nd,ProcessDefinition node,List<ProcessDefinition> rs,Map key) {
		for(int i=0;i<datas.size();i++) {
			Map data=(Map) datas.get(i);
			if(data.get("to").toString().equals("JS")||data.get("from").toString().equals("JS")) {
				continue;
			}
			String tos[]=data.get("to").toString().split("@");
			
			if(data.get("to").toString().equals(nd)) {
				String[] froms=data.get("from").toString().split("@");
				if(tos[1].indexOf("N")==0&&froms[1].indexOf("N")==0) {
					continue;
				}
				Map n=(Map) key.get(froms[1]);
				ProcessDefinition pd=new ProcessDefinition();
				pd.setCreateid(uid);
				pd.setCode(froms[1]);
				pd.setActive(1);
				pd.setId(node.getId()+"_"+Double.valueOf(n.get("id").toString()).intValue());
				pd.setPnId(Double.valueOf(n.get("id").toString()).intValue());
				pd.setPId(node.getId());
				pd.setSort(Double.valueOf(n.get("sort").toString()).intValue());
				pd.setName(n.get("text").toString());
				rs.add(pd);
				findPath(uid,datas,data.get("from").toString(),pd,rs,key);
			}

		}
	}

}

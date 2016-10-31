package p.minn.workflow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import p.minn.common.utils.ConstantCommon;
import p.minn.common.utils.MyGsonMap;
import p.minn.common.utils.UtilCommon;
import p.minn.security.cas.springsecurity.auth.User;
import p.minn.workflow.entity.ProcessAudit;
import p.minn.workflow.entity.ProcessAuditStatus;
import p.minn.workflow.repository.ProcessAuditDao;
import p.minn.workflow.repository.ProcessAuditStatusDao;
import p.minn.workflow.repository.ProcessDefinitionDao;
import p.minn.workflow.repository.WorkFlowDao;



/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment
 *
 */
@Service
public class WorkFlowService {
	
    @Autowired
	private WorkFlowDao dao;
    
    @Autowired
    private ProcessDefinitionDao processDefinitionDao;
    
    @Autowired
    private ProcessAuditDao processAuditDao;
    
    @Autowired
    private ProcessAuditStatusDao processAuditStatusDao;

  public Object queryTree(String messageBody, String lang) {
    // TODO Auto-generated method stub
    Map<String,Object> condition=(Map<String, Object>) UtilCommon.gson2Map(messageBody);
    List<Map<String,Object>> list=dao.queryTree(lang,condition);
    
    return UtilCommon.createTreeMenu(list,"-1");
  }
  
  public Object queryModelTree(String messageBody, String lang) {
    // TODO Auto-generated method stub
    Map<String,Object> condition=(Map<String, Object>) UtilCommon.gson2Map(messageBody);
    List<Map<String,Object>> list=dao.queryModelTree(lang,condition);
    return UtilCommon.createTreeMenu(list,condition.get("id").toString());
  }
  
  public void saveAudit(User user,String messageBody,String lang){
    try{
    MyGsonMap<Map,ProcessAuditStatus> msm=MyGsonMap.getInstance(messageBody,Map.class, ProcessAuditStatus.class); 
    ProcessAuditStatus processAuditStatus=msm.gson2T(ProcessAuditStatus.class);
    Map condition=msm.gson2Map();
    
    String lpId=condition.get("lpId").toString();
    String pdId=condition.get("pdId").toString();
    String pId=condition.get("pId").toString();
    ProcessAuditStatus dbStatus=processAuditStatusDao.getMaxAuditStatus(lpId,pdId,user.getDepartments().get(0).getId());
    if(dbStatus==null){
      throw new RuntimeException(ConstantCommon.ERROR_CODE_10001);
    }
    String audittime=UtilCommon.currentDateTime();
    dbStatus.setStatus(processAuditStatus.getStatus());
    dbStatus.setAuditId(user.getId());
    dbStatus.setAuditDeptid(user.getDepartments().get(0).getId());
    dbStatus.setAuditdate(audittime);
    dbStatus.setComment(processAuditStatus.getComment());
    
    dbStatus.setProcessStatus(1);
  
    processAuditStatusDao.update(dbStatus);
    int maxActive=dbStatus.getMaxActive();
   
    if(processAuditStatus.getStatus()==1){
     int num= dao.getNodeStepCompleteStatus(pdId,lpId,dbStatus.getId(),dbStatus.getMaxActive(),dbStatus.getStep());
     if(num==0)
      nextProcessStep(user,pId,lpId,pdId,dbStatus,"audit");
    }else{
      maxActive+=1;
      List<Integer> maxactives= processAuditStatusDao.getNoNeedUpdateMaxActive(lpId,pdId,dbStatus.getId());
      StringBuffer ids=new StringBuffer();
      ids.append("-1");
      for(Integer id:maxactives){
        if(ids.length()>0){
          ids.append(",");
        }
        ids.append(id);
      }
      processAuditStatusDao.updateMaxActive(lpId,ids.toString(), maxActive);
      dbStatus.setMaxActive(maxActive);
      preProcessStep(user,pId,lpId,pdId,dbStatus);
    }
    
    
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  private void nextProcessNodeStep(User user,String pId,String lpId,String pdId,ProcessAuditStatus status){
    List<Map<String,Object>> models=null;
    int maxActive=status.getMaxActive();
    models=dao.getNextStep(lpId,pdId,maxActive);
    for(Map<String,Object> model:models){
      String fromnode=model.get("pdId").toString();
      ProcessAudit audit=new ProcessAudit();
      audit.setCreateid(user.getId());
      audit.setFromnode(fromnode);
      audit.setTonode(fromnode.substring(0, fromnode.lastIndexOf("_")));
      audit.setLpId(Integer.valueOf(lpId));
      audit.setPasId(status.getId());
      audit.setPdId(fromnode);
      processAuditDao.save(audit);
    
    }
  }

  public void nextProcessStep(User user,String pId,String lpId,String pdId,ProcessAuditStatus status,String method){
    String pdIds[]=pdId.split("_");
   
    List<Map<String,Object>> models=null;
    int maxActive=1;
    Integer step=1;
    if(status!=null)
      maxActive=status.getMaxActive();
    int num=dao.getNodeCompleteStatus(lpId, pdId);
    if(num==0){
      models=dao.getNextNode(lpId,pId,maxActive);
    }else{
      
      models=dao.getNextNodeStep(lpId,status.getId(), pdId,maxActive);
      step=processAuditStatusDao.getMaxStep(lpId, pdId,1);
    }
   
    for(Map<String,Object> model:models){
      String auditdate=UtilCommon.currentDateTime();
      ProcessAuditStatus processAuditStatus=new ProcessAuditStatus();
      processAuditStatus.setLpId(Integer.valueOf(lpId));
      processAuditStatus.setAudit_name(user.getUsername());
      processAuditStatus.setMaxActive(maxActive);
      processAuditStatus.setAuditId(user.getId());
      processAuditStatus.setAuditDeptid(user.getDepartments().get(0).getId());
      processAuditStatus.setCreateid(user.getId());
      processAuditStatus.setAuditdate(auditdate);
      processAuditStatus.setComment("process start");
      processAuditStatus.setProcessStatus(-1);
      processAuditStatus.setPdId(model.get("pdId").toString());
      processAuditStatus.setStep(step.toString());
      processAuditStatus.setStatus(-1);
      processAuditStatus.setNodeStatus(0);
      processAuditStatusDao.save(processAuditStatus);
      
      if(method.equals("audit")&&num>0){
        String fromnode=model.get("pdId").toString();
        ProcessAudit audit=new ProcessAudit();
        audit.setCreateid(user.getId());
        audit.setFromnode(fromnode);
        audit.setTonode(fromnode.substring(0, fromnode.lastIndexOf("_")));
        audit.setLpId(Integer.valueOf(lpId));
        audit.setPasId(processAuditStatus.getId());
        audit.setPdId(fromnode);
        processAuditDao.save(audit);
      }else{
        nextProcessNodeStep(user,pId,lpId,model.get("pdId").toString(),processAuditStatus);
      }
      
      
      
    }
  }
  
  
  public void preProcessStep(User user,String pId,String lpId,String pdId,ProcessAuditStatus status){
    String pdIds[]=pdId.split("_");
   
    List<Map<String,Object>> models=null;
    int maxActive=status.getMaxActive();
    String temppdId=pdId;
    int num=dao.getPreNodeCompleteStatus(lpId, pdId,status.getId());
    if(num==0){
      models=dao.getPreNode(pId,pdId);
      temppdId=models.get(0).get("pdId").toString();
    }else{
      models=dao.getPreNodeStep(lpId, status.getId());
    }
   
    Integer step=processAuditStatusDao.getMaxStep(lpId, temppdId,1);
    String deptId=null;
    int stats=models.size();
    for(Map<String,Object> model:models){
      String nodes[]=model.get("pdId").toString().split("_");
      if(deptId==null){
        deptId=nodes[nodes.length-1];
      }else{
        if(deptId.equals(nodes[nodes.length-1])){
          stats=1;
        }
      }
       
    }
    ProcessAuditStatus processAuditStatus=null;
    for(int i=0;i<models.size();i++){
      Map<String,Object> model=models.get(i);
     if(i<stats){
       processAuditStatusDao.updateMaxActiveBypdId(model.get("pdId").toString(),maxActive-1);
      String auditdate=UtilCommon.currentDateTime();
      processAuditStatus=new ProcessAuditStatus();
      processAuditStatus.setLpId(Integer.valueOf(lpId));
      processAuditStatus.setAudit_name(user.getUsername());
      processAuditStatus.setMaxActive(maxActive);
      processAuditStatus.setAuditId(user.getId());
      processAuditStatus.setAuditDeptid(user.getDepartments().get(0).getId());
      processAuditStatus.setCreateid(user.getId());
      processAuditStatus.setAuditdate(auditdate);
      processAuditStatus.setComment("process start");
      processAuditStatus.setProcessStatus(-1);
      processAuditStatus.setPdId(model.get("pdId").toString());
      processAuditStatus.setStep(step.toString());
      processAuditStatus.setStatus(-1);
      processAuditStatus.setNodeStatus(0);
      processAuditStatusDao.save(processAuditStatus);
     }
     
        String fromnode=model.get("pdId").toString();
        ProcessAudit audit=new ProcessAudit();
        audit.setCreateid(user.getId());
        audit.setFromnode(fromnode);
        audit.setTonode(fromnode.substring(0, fromnode.lastIndexOf("_")));
        audit.setLpId(Integer.valueOf(lpId));
        audit.setPasId(processAuditStatus.getId());
        audit.setPdId(fromnode);
        processAuditDao.save(audit);
      
    }
  }
  
}

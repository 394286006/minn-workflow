package p.minn.workflow.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import p.minn.common.annotation.MyParam;
import p.minn.common.exception.WebPrivilegeException;
import p.minn.common.utils.ConstantCommon;
import p.minn.vo.User;
import p.minn.workflow.service.WorkFlowService;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 
 *
 */
@Controller
@RequestMapping("/workFlow")
@SessionAttributes(ConstantCommon.LOGINUSER)
public class WorkFlowController {

    @Autowired
	private WorkFlowService service;
	
	 @RequestMapping(params="method=queryTree")
     public Object queryTree(@RequestParam("messageBody") String messageBody,@MyParam("language") String lang){
         Object entity = null;
         try {
             entity=service.queryTree(messageBody,lang);
          } catch (Exception e) {
                 entity = new WebPrivilegeException(e.getMessage());
          }
         return entity;
     }
	 
	   @RequestMapping(params="method=queryModelTree")
	     public Object queryModelTree(@RequestParam("messageBody") String messageBody,@MyParam("language") String lang){
	         Object entity = null;
	         try {
	             entity=service.queryModelTree(messageBody,lang);
	          } catch (Exception e) {
	                 entity = new WebPrivilegeException(e.getMessage());
	          }
	         return entity;
	     }
   
       @RequestMapping(params = "method=saveAudit")
       @ResponseBody
       public Object saveAudit(@ModelAttribute(ConstantCommon.LOGINUSER) User user,@RequestParam("messageBody") String messageBody,@MyParam("language") String lang) {
            Object entity = null;
            try {
                 service.saveAudit(user,messageBody,lang);
             } catch (Exception e) {
                entity = new WebPrivilegeException(e.getMessage());
              }
           return entity;
        }

}

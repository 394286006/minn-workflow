package p.minn.workflow.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.LocaleResolver;

import p.minn.common.annotation.MyParam;
import p.minn.common.utils.ConstantCommon;
import p.minn.common.exception.WebPrivilegeException;
import p.minn.security.cas.springsecurity.auth.User;
import p.minn.workflow.service.ProcessNodeModelService;

/**
 * 
 * @author minn 
 * @QQ:3942986006
 * @comment 
 * 
 */
@Controller
@RequestMapping("/processNM")
@SessionAttributes(ConstantCommon.LOGINUSER)
public class ProcessNodeModelController {

	@Autowired
	private ProcessNodeModelService service;
	
	@Autowired
    private LocaleResolver localeResolver;
	
	@RequestMapping(params = "method=save")
	@ResponseBody
	public Object save(@ModelAttribute(ConstantCommon.LOGINUSER) User user,@RequestParam("messageBody") String messageBody,@MyParam("language") String lang) {
		Object entity = null;
		try {
		  service.save(user,messageBody,lang);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}
	
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
}

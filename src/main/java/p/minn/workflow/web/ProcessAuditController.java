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
import p.minn.oauth.vo.User;
import p.minn.common.exception.WebPrivilegeException;
import p.minn.workflow.service.ProcessAuditService;

/**
 * 
 * @author minn 
 * @QQ:3942986006
 * @comment 
 * 
 */
@Controller
@RequestMapping("/processAudit")
@SessionAttributes(ConstantCommon.LOGINUSER)
public class ProcessAuditController {

	@Autowired
	private ProcessAuditService service;
	
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
		
	@RequestMapping(params="method=query")
	public Object query(@RequestParam("messageBody") String messageBody,@MyParam("language") String lang){
		Object entity = null;
		try {
			entity=service.query(messageBody,lang);
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
		 }
		return entity;
	}

	
	 
	
}

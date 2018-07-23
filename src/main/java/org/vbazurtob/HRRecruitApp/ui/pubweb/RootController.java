package org.vbazurtob.HRRecruitApp.ui.pubweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vbazurtob.HRRecruitApp.conf.ControllerEndpoints;

@Controller
public class RootController implements ControllerEndpoints {

	@RequestMapping({ ROOT_PAGE, INDEX_PAGE })
	public String rootRedirect() {
		return "redirect:/public/home";
	}
	
}
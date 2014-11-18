package jetbrick.template.online.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import jetbrick.ioc.annotation.Inject;
import jetbrick.template.online.service.TemplateContext;
import jetbrick.template.online.service.TemplateService;
import jetbrick.web.mvc.action.Action;
import jetbrick.web.mvc.action.Controller;
import jetbrick.web.mvc.action.annotation.RequestParam;
import com.alibaba.fastjson.*;

@Controller
public final class IndexController {

    @Action("/")
    public String index() {
        return "redirect:/online.html";
    }

}

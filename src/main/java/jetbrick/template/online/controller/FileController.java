package jetbrick.template.online.controller;

import javax.servlet.ServletContext;
import jetbrick.ioc.annotation.Inject;
import jetbrick.template.online.service.CaseService;
import jetbrick.web.mvc.action.Action;
import jetbrick.web.mvc.action.Controller;
import jetbrick.web.mvc.action.annotation.PathVariable;
import com.alibaba.fastjson.JSONAware;

@Controller
public final class FileController {

    @Inject
    private CaseService caseService;

    @Action("/files")
    public JSONAware files(ServletContext sc) {
        return caseService.getCaseList();
    }

    @Action("/files/{id}")
    public JSONAware file(ServletContext sc, @PathVariable String id) {
        return caseService.getCase(id);
    }

}

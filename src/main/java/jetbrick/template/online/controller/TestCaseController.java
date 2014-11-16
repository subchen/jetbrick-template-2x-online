package jetbrick.template.online.controller;

import javax.servlet.ServletContext;
import jetbrick.ioc.annotation.Inject;
import jetbrick.template.online.service.TestCaseService;
import jetbrick.web.mvc.action.Action;
import jetbrick.web.mvc.action.Controller;
import jetbrick.web.mvc.action.annotation.PathVariable;
import com.alibaba.fastjson.JSONAware;

@Controller
public final class TestCaseController {

    @Inject
    private TestCaseService testCaseService;

    @Action("/testcases")
    public JSONAware testcaseList(ServletContext sc) {
        return testCaseService.getTestCaseList();
    }

    @Action("/testcases/{id}")
    public JSONAware testcaseGet(ServletContext sc, @PathVariable String id) {
        return testCaseService.getTestCase(id);
    }

}

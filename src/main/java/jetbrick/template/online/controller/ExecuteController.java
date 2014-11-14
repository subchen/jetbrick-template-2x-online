package jetbrick.template.online.controller;

import java.io.*;
import java.util.*;

import jetbrick.io.*;
import jetbrick.util.*;
import jetbrick.ioc.annotation.*;
import jetbrick.web.mvc.*;
import jetbrick.web.mvc.action.*;
import jetbrick.web.mvc.action.annotation.*;
import jetbrick.ioc.annotation.*;
import jetbrick.template.online.service.*;

import com.alibaba.fastjson.*;

@Controller
public final class ExecuteController {

    @Inject
    private TemplateService templateService;

    @Action("/execute")
    public JSONAware execute(@RequestParam String source, @RequestParam String model) {
        Map<String, Object> ctx = JSON.parseObject(model);
        
        JSONObject json = new JSONObject();
        try {
            String result = templateService.execute(source, ctx);
            json.put("succ", true);
            json.put("result", result);
        } catch(Throwable e) {
            String error;
            
            if (e instanceof java.util.concurrent.TimeoutException) {
                error = "Template Execute Timeout";    
            } else {
                while (e.getCause() != null) {
                    e = e.getCause();
                    if (e.getClass().getName().startsWith("jetbrick.")) {
                        break;
                    }
                }
                StringWriter out = new StringWriter();
                PrintWriter writer = new PrintWriter(out);
                e.printStackTrace(writer);
                error = out.toString();
            }
            
            json.put("succ", false);
            json.put("error", error);
        }
        return json;
    }

    @Action("/share")
    public JSONAware share(@RequestParam String source, @RequestParam String model) {
        return null;
    }

}

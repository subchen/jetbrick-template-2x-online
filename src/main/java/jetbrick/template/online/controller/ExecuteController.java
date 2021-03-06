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
public final class ExecuteController {

    @Inject
    private TemplateService templateService;

    @Action("/execute")
    public JSONAware execute(@RequestParam String files, @RequestParam String model) {
        JSONObject json = new JSONObject();
        try {
            JSONArray jsonFiles = JSON.parseArray(files);
            JSONObject jsonModel = JSON.parseObject(model);

            TemplateContext ctx = new TemplateContext();
            for (int i = 0; i < jsonFiles.size(); i++) {
                JSONObject file = jsonFiles.getJSONObject(i);
                ctx.addFileSource(file.getString("name"), file.getString("source"));
            }
            ctx.setContext(jsonModel);

            String result = templateService.execute(ctx);
            json.put("succ", true);
            json.put("result", result);
        } catch (Throwable e) {
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
            json.put("result", error);
        }
        return json;
    }

    @Action("/share")
    public JSONAware share(@RequestParam String source, @RequestParam String model) {
        return null;
    }

}

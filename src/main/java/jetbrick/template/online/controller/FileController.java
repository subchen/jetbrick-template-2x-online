package jetbrick.template.online.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;

import jetbrick.io.*;
import jetbrick.util.*;
import jetbrick.web.mvc.*;
import jetbrick.web.mvc.action.*;
import jetbrick.web.mvc.action.annotation.*;

import com.alibaba.fastjson.*;

@Controller
public final class FileController {

    @Action("/files")
    public JSONAware files() {
        JSONArray json = new JSONArray();
        json.add("01. Hello World!");
        return json;
    }

    @Action("/files/{id}")
    public JSONAware file(ServletContext sc, @PathVariable String id) {
        InputStream is = sc.getResourceAsStream("/sources/" + id + ".jetx");
        String source = IoUtils.toString(is, "utf-8");

        is = sc.getResourceAsStream("/sources/" + id + ".json");
        String model = IoUtils.toString(is, "utf-8");
        
        JSONObject json = new JSONObject();
        json.put("source", source.trim());
        json.put("model", model.trim());
        return json;
    }

}

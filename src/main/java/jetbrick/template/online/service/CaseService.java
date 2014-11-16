package jetbrick.template.online.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import jetbrick.io.IoUtils;
import jetbrick.ioc.annotation.Inject;
import jetbrick.ioc.annotation.IocBean;
import jetbrick.util.CharsetUtils;
import com.alibaba.fastjson.*;

@IocBean
public final class CaseService {
    private final ServletContext sc;
    private final JSONArray caseList;
    private final Map<String, JSONObject> caseMap;

    @Inject
    public CaseService(ServletContext sc) {
        this.sc = sc;

        this.caseList = JSON.parseArray(getSource("meta.json"));
        this.caseMap = new HashMap<String, JSONObject>();
        for (int i = 0; i < caseList.size(); i++) {
            JSONObject obj = caseList.getJSONObject(i);
            caseMap.put(obj.getString("id"), obj);
        }
    }

    public JSONArray getCaseList() {
        return caseList;
    }

    public JSONObject getCase(String id) {
        JSONObject json = caseMap.get(id);

        JSONArray files = json.getJSONArray("files");
        JSONArray sources = new JSONArray();
        for (int i = 0; i < files.size(); i++) {
            String file = files.getString(i);
            String source = getSource(id + "-" + file);
            sources.add(source);
        }
        json.put("sources", sources);
        json.put("model", getSource(id + ".json"));
        return json;
    }

    private String getSource(String file) {
        InputStream is = sc.getResourceAsStream("/sources/" + file);
        return IoUtils.toString(is, CharsetUtils.UTF_8);
    }
}

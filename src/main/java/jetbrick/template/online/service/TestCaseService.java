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
public final class TestCaseService {
    private final ServletContext sc;
    private final JSONArray testcaseList;
    private final Map<String, JSONObject> testcaseMap;

    @Inject
    public TestCaseService(ServletContext sc) {
        this.sc = sc;

        this.testcaseList = JSON.parseArray(getSource("testcases.json"));
        this.testcaseMap = new HashMap<String, JSONObject>();
    }

    public JSONArray getTestCaseList() {
        return testcaseList;
    }

    public JSONObject getTestCase(String id) {
        JSONObject json = testcaseMap.get(id);
        if (json != null) {
            return json;
        }

        json = new JSONObject();
        testcaseMap.put(id, json); // cache

        JSONArray fileObjectList = new JSONArray();
        for (Object testcase: testcaseList) {
            JSONObject tc = (JSONObject)testcase;
            if (id.equals(tc.getString("id"))) {
                for (Object file : tc.getJSONArray("files")) {
                    JSONObject fileObject = new JSONObject();
                    fileObject.put("name", file);
                    fileObject.put("source", getSource(id + "-" + file));
                    fileObjectList.add(fileObject);
                }
            }
        }

        json.put("files", fileObjectList);
        json.put("model", getSource(id + ".json"));
        return json;
    }

    private String getSource(String file) {
        InputStream is = sc.getResourceAsStream("/sources/" + file);
        return IoUtils.toString(is, CharsetUtils.UTF_8);
    }
}

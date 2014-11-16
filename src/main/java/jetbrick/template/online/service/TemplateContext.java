package jetbrick.template.online.service;

import java.util.*;

public final class TemplateContext {
    private List<String> files = new ArrayList<String>();
    private List<String> sources = new ArrayList<String>();
    private Map<String, Object> context;

    public void addFileSource(String file, String source) {
        files.add(file);
        sources.add(source);
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public int getFileSize() {
        return files.size();
    }

    public String getFile(int index) {
        return files.get(index);
    }

    public String getSource(int index) {
        return sources.get(index);
    }

    public String getMainFile() {
        return files.get(0);
    }

    public Map<String, Object> getContext() {
        return context;
    }

}

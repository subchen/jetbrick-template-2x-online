package jetbrick.template.online.service;

import java.util.*;

public final class TemplateContext {
    private List<String> files = new ArrayList<String>();
    private List<String> sources = new ArrayList<String>();
    private int entryIndex = 0;
    private Map<String, Object> context;

    public void addFileSource(String file, String source) {
        files.add(file);
        sources.add(source);
    }

    public void setEntryIndex(int entryIndex) {
        this.entryIndex = entryIndex;
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

    public String getEntryFile() {
        return files.get(entryIndex);
    }

    public Map<String, Object> getContext() {
        return context;
    }

}

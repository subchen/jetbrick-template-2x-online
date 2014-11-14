package jetbrick.template.online.service;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import jetbrick.ioc.annotation.*;
import jetbrick.template.*;

@IocBean
public final class TemplateService {
    private final ExecutorService pool;
    private final JetEngine engine;

    public TemplateService() {
        this.pool = Executors.newFixedThreadPool(20);
        this.engine = JetEngine.create();
    }

    public String execute(final String source, final Map<String, Object> context) {
        Future<String> future = pool.submit(new Callable<String>() {
            public String call() {
                SandboxJetEngine engine = new SandboxJetEngine(TemplateService.this.engine);
                engine.set("/main.jetx", source);
                JetTemplate template = engine.getTemplate("/main.jetx");
                StringWriter writer = new StringWriter();
                template.render(context, writer);
                return writer.toString();
            }
        });

        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

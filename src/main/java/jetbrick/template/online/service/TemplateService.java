package jetbrick.template.online.service;

import java.io.StringWriter;
import java.util.concurrent.*;
import jetbrick.ioc.annotation.IocBean;
import jetbrick.template.*;

@IocBean
public final class TemplateService {
    private final ExecutorService pool;
    private final JetEngine engine;

    public TemplateService() {
        this.pool = Executors.newFixedThreadPool(20);
        this.engine = JetEngine.create();
    }

    public String execute(final TemplateContext ctx) {
        Future<String> future = pool.submit(new Callable<String>() {
            @Override
            public String call() {
                SandboxJetEngine engine = new SandboxJetEngine(TemplateService.this.engine);
                for (int i = 0; i < ctx.getFileSize(); i++) {
                    engine.set(ctx.getFile(i), ctx.getSource(i));
                }
                JetTemplate template = engine.getTemplate(ctx.getMainFile());
                StringWriter writer = new StringWriter();
                template.render(ctx.getContext(), writer);
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

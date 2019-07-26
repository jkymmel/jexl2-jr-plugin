package io.github.jkymmel.jr.jexl2plugin;


import org.zeroturnaround.javarebel.Logger;
import org.zeroturnaround.javarebel.LoggerFactory;
import org.zeroturnaround.javarebel.ReloaderFactory;
import org.zeroturnaround.javarebel.integration.generic.ClassEventListenerAdapter;

import java.util.concurrent.atomic.AtomicBoolean;

public class ReloadHelper {

    private static final Logger log = LoggerFactory.getLogger(Jexl2Plugin.LOGGER_PREFIX);

    private AtomicBoolean dirty = new AtomicBoolean(false);

    public final ClassEventListenerAdapter classEventListenerAdapter = new ClassEventListenerAdapter(0) {
        @Override
        public void onClassEvent(int eventType, Class<?> klass) throws Exception {
            log.debug("Setting dirty to true.");
            dirty.set(true);
        }
    };

    public ReloadHelper() {
        ReloaderFactory.getInstance().addClassReloadListener(classEventListenerAdapter.weak);
    }

    public boolean getAndResetDirty() {
        return dirty.getAndSet(false);
    }

    public Logger getLogger() {
        return log;
    }
}

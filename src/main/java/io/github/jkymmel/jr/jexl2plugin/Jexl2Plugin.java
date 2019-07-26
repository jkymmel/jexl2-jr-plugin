package io.github.jkymmel.jr.jexl2plugin;

import org.zeroturnaround.javarebel.ClassResourceSource;
import org.zeroturnaround.javarebel.Integration;
import org.zeroturnaround.javarebel.IntegrationFactory;
import org.zeroturnaround.javarebel.Plugin;

public class Jexl2Plugin implements Plugin {

    public static final String LOGGER_PREFIX = "Jexl2";

    @Override
    public void preinit() {
        Integration integration = IntegrationFactory.getInstance();
        ClassLoader cl = getClass().getClassLoader();

        integration.addIntegrationProcessor(cl,
                "org.apache.commons.jexl2.internal.introspection.IntrospectorBase",
                new IntrospectorBaseCBP());

        // add more processors here
    }

    @Override
    public boolean checkDependencies(ClassLoader cl, ClassResourceSource crs) {
        // check if plugin should be enabled in classloader cl
        return crs.getClassResource("org.apache.commons.jexl2.internal.introspection.IntrospectorBase") != null;
    }

    @Override
    public String getId() {
        return "jk_jexl2_plugin";
    }

    @Override
    public String getName() {
        return "Jexl2 Plugin";
    }

    @Override
    public String getDescription() {
        return "<li>Provides Jexl2 integration (or atleast should for some parts of it :P)</li>";
    }

    @Override
    public String getAuthor() {
        return "Johannes Kümmel";
    }

    @Override
    public String getWebsite() {
        return "https://github.com/jkymmel";
    }

    @Override
    public String getSupportedVersions() {
        return "2.1.1";
    }

    @Override
    public String getTestedVersions() {
        return "2.1.1";
    }
}

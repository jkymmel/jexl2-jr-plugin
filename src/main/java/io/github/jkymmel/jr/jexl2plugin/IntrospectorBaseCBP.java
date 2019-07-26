package io.github.jkymmel.jr.jexl2plugin;

import org.zeroturnaround.bundled.javassist.ClassPool;
import org.zeroturnaround.bundled.javassist.CtClass;
import org.zeroturnaround.bundled.javassist.CtField;
import org.zeroturnaround.bundled.javassist.CtMethod;
import org.zeroturnaround.javarebel.integration.support.JavassistClassBytecodeProcessor;

public class IntrospectorBaseCBP extends JavassistClassBytecodeProcessor {

    public IntrospectorBaseCBP(String asd) {
        System.out.println(asd);
    }

    public IntrospectorBaseCBP() {
        this("ef");
    }

    @Override
    public void process(ClassPool cp, ClassLoader cl, CtClass ctClass) throws Exception {
        cp.importPackage("org.zeroturnaround.javarebel");
        cp.importPackage("io.github.jkymmel.jr.jexl2plugin");

        ctClass.addField(CtField.make("private ReloadHelper reloadHelper = new ReloadHelper();", ctClass));

        ctClass.addMethod(CtMethod.make("" +
                "private void reinitialize() {" +
                "  if (reloadHelper.getAndResetDirty()) {" +
                "    reloadHelper.getLogger().info(\"Clearing IntrospectorBase cache.\");" +
                "    this.classMethodMaps.clear();" +
                "    this.constructorsMap.clear();" +
                "    this.constructibleClasses.clear();" +
                "  }" +
                "}", ctClass));
        for (CtMethod declaredMethod : ctClass.getDeclaredMethods()) {
            if (declaredMethod.getName().equals("setLoader") ||
                    declaredMethod.getName().equals("getConstructor") ||
                    declaredMethod.getName().equals("getMap")) {
                declaredMethod.insertBefore("reinitialize();");
            }
        }

    }
}

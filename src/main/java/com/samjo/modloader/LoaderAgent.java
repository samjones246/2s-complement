package com.samjo.modloader;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LoaderAgent extends ClassVisitor implements ClassFileTransformer{
    private static final String MAINCLASS = "main/Main";
    public static List<Mod> loadedMods = new ArrayList<>();

    public static void premain(
        String agentArgs,
        Instrumentation inst
    ) {
        inst.addTransformer(new LoaderAgent());
    }

    public LoaderAgent() {
        super(Opcodes.ASM5);
        LoadAllMods();
    }

    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer
    ) {
        if (!className.equals(MAINCLASS)) {
            return null;
        }
        System.out.println("Transforming main.Main");
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, 0);
        synchronized(this) {
            super.cv = cw;
            try { cr.accept(this, ClassReader.EXPAND_FRAMES); }
            catch (Exception e) { e.printStackTrace(); }
            finally { super.cv = null; }
        }
        return cw.toByteArray();
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (name.equals("<init>")) {
            System.out.println("Transforming constructor");
            return new AdviceAdapter(Opcodes.ASM5, mv, access, name, descriptor) {
                @Override
                protected void onMethodExit(int opcode) {
                    super.visitTypeInsn(
                            Opcodes.NEW,
                            "com/samjo/modloader/LoaderEntity"
                    );
                    super.visitInsn(Opcodes.DUP);
                    super.visitMethodInsn(
                            INVOKESPECIAL,
                            "com/samjo/modloader/LoaderEntity",
                            "<init>",
                            "()V",
                            false
                    );
                    super.onMethodExit(opcode);
                }
            };
        }
        return mv;
    }

    private void LoadAllMods() {
        // Get all mod files
        File[] modFilesArray = new File(".\\mods").listFiles();
        if (modFilesArray == null) {
            System.out.println("Mods folder not found");
            return;
        }
        List<File> modZips = Stream.of(modFilesArray)
                .filter(file ->
                        file.isFile() &&
                            file.getName().endsWith(".zip") ||
                            file.getName().endsWith(".jar")
                )
                .collect(Collectors.toList());

        System.out.println("Attempting to load " + modZips.size() + " mods...");

        for(File file : modZips) {
            ZipFile zipFile = null;
            String zipPath = null;
            try {
                zipFile = new ZipFile(file);
                zipPath = file.getCanonicalPath();
            } catch (IOException ignored) { }
            Mod mod = LoadMod(zipFile, zipPath);
            if (mod != null) {
                loadedMods.add(mod);
                mod.onLoad();
            }

            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    System.err.println("A resource failed to close: " + file.getName());
                }
            }
        }
    }

    private Mod LoadMod(ZipFile file, String zipPath) {
        String[] zipPathParts = zipPath.split("\\\\");
        String zipName = zipPathParts[zipPathParts.length - 1];
        String errorPrefix = "Error loading mod file " + zipName + ": ";
        InputStreamReader reader = null;
        if(file == null) {
            System.err.println(errorPrefix + "Unable to read");
            return null;
        }

        ZipEntry propertiesEntry = file.getEntry("mod.properties");
        if (propertiesEntry == null) {
            System.err.println(errorPrefix + "Missing mod.properties");
            return null;
        }
        try {
            reader = new InputStreamReader(file.getInputStream(propertiesEntry));
        } catch (IOException e) {
            System.err.println(errorPrefix + "Unable to read mod.properties");
            return null;
        }
        Properties props = new Properties();
        try {
            props.load(reader);
        } catch (IOException e) {
            System.err.println(errorPrefix + "Unable to parse mod.properties");
            return null;
        }
        String name = props.getProperty("name", "<untitled>");
        String author = props.getProperty("author", "<unspecified>");
        String modClassName = props.getProperty("modclass");
        if (modClassName == null) {
            System.err.println(errorPrefix + "mod.properties missing required key modclass");
            return null;
        }
        List<String> classList = classesInZip(file);
        Mod mod;
        try {
            mod = LoadModClasses(zipPath, modClassName, classList);
        } catch (Exception e) {
            System.err.println(errorPrefix + "Could not interpet " + modClassName + " as a Mod object");
            return null;
        }

        System.out.println("MOD LOADED");
        System.out.println(" - Name: " + name);
        System.out.println(" - Author: " + author);

        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("A resource failed to close: " + file.getName());
        }
        return mod;
    }

    private Mod LoadModClasses(String zipPath, String modClassName, List<String> classList) throws Exception {
        URL modUrl = new File(zipPath).toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{ modUrl });
        Mod mod = null;
        for (String className : classList) {
            Class<?> cls = classLoader.loadClass(className);
            if (className.equals(modClassName)){
                mod = (Mod)cls.getConstructor().newInstance();
            }
        }
        classLoader.close();
        return mod;
    }

    private List<String> classesInZip(ZipFile file) {
        ArrayList<String> classPaths = new ArrayList<>();
        Enumeration<? extends ZipEntry> entries = file.entries();
        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                classPaths.add(
                    entry
                        .getName()
                        .replaceFirst("\\..*", "")
                        .replace('/', '.')
                );
            }
        }
        return classPaths;
    }
}

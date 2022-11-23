package modding;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import main.Entity;

public class Loader extends Entity {
    List<Mod> loadedMods = new ArrayList<>();

    public Loader(String modsDir){
        super(0.0, 0.0);
        this.persistent = true;
        // Get all mod files
        List<File> modZips = Stream.of(new File(modsDir).listFiles())
            .filter(file -> 
                file.isFile() && file.getName().endsWith(".zip")
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
                mod.init();
                loadedMods.add(mod);
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
        String className = props.getProperty("modclass");
        if (className == null) {
            System.err.println(errorPrefix + "mod.properties missing required key modclass");
            return null;
        }
        Mod mod = null;
        try {
            mod = LoadModClass(zipPath, className);
        } catch (Exception e) {
            System.err.println(errorPrefix + "Could not interpet " + className + " as a Mod object");
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

    private Mod LoadModClass(String zipPath, String className) throws Exception {
        URL modUrl = new File(zipPath).toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{ modUrl });
        Class<?> cls = classLoader.loadClass(className);
        Mod mod = (Mod)cls.getConstructor().newInstance();
        classLoader.close();
        return mod;
    }

    @Override
    public void step() {
        for(Mod mod : loadedMods) {
            mod.step();
        }
    }
}

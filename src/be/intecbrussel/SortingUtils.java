package be.intecbrussel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SortingUtils {


    public SortingUtils() {
    }

    //Create a list of all files in the directory
    public static void list(File map, List<File> files) {


        File[] arrayMap = map.listFiles();
        if (arrayMap != null) {
            for (File file : arrayMap) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    list(file.getAbsoluteFile(), files);
                }
            }
        }
    }

    //method to show the extension of the files
    public static String getExtension(String file) {
        String ext = null;
        try {
            if (file != null) {
                String name = file;

                ext = name.substring(name.lastIndexOf('.') + 1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ext;
    }

    //put all the extensions in a Hashset to create new maps
    //put all matching files in the matching map
    public static void newDirectory(List<File> files, File dir) throws IOException {
        Set<String> newMaps = new HashSet<>();
        for (File file : files) {
            newMaps.add(getExtension(file.getName()));

        }
        //creating the directories
        for (String s : newMaps) {
            if (!new File(dir + "/" + s).exists()) {
                new File(dir + "/" + s).mkdir();
            }
        }

        //Check if the destination already exists, if not, it's being created
        for (File file : files) {
            if (getExtension(file.getName()) != null) {
                Path source = file.toPath();
                Path destination = dir.toPath().resolve(getExtension(file.getName())).resolve(file.getName());
                if (!destination.toFile().exists()) {
                    Files.copy(source, destination);
                }
            }
        }
    }

    //Create a summary file to show if the files are hidden/readable and writable
    public static void createSummary(File dir) throws IOException {

        //create a list with the sorted files showing the directory
        List<File> sortedFiles = new ArrayList<>();
        SortingUtils.list(dir, sortedFiles);
        Set<String> ext = new HashSet<>();
        String sum = "/summary.txt";

        for (File file : sortedFiles) {
            ext.add(getExtension(file.getName()));
        }

        //Create a format to display the summary.txt
        List<String> summary = new ArrayList<>();
        summary.add(String.format("%-50s%13s%13s%13s", "name", "readable", "writeable", "hidden"));

        for (String s : ext) {
            summary.add("\n");
            summary.add(s + ":");
            summary.add("----------");
            for (File file : sortedFiles) {
                if (s.equals(file.getParentFile().getName())) {
                    summary.add(String.format("%-50s%13s%13s%13s", file.getName(), file.canRead() ? "Y" : "N",
                            file.canWrite() ? "Y" : "N", file.isHidden() ? "Y" : "N"));
                }
            }
        }
        //check if the summarty file already exists, if not, it's being created and written
        if (!Paths.get(dir + sum).toFile().exists()) {
            Files.createFile(Paths.get(dir + sum));
        }
        Files.write(Paths.get(dir + sum), summary);
    }


}

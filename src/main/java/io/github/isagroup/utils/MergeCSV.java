package io.github.isagroup.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MergeCSV {

    private final static Path CSV_FILES_PATH = Path.of("src", "test", "resources", "parsing", "negative");
    private final static Path AUTOGENERATED_FILE_PATH = Path.of("src", "test", "resources", "negative-cases.csv");

    public static void main(String[] args) {

        if (!CSV_FILES_PATH.toFile().exists()) {
            System.out.println("Path: " + CSV_FILES_PATH + " does not exist");
            return;
        }

        deleteAutogeneratedFile();

        if (!CSV_FILES_PATH.toFile().isDirectory()) {
            System.err.println("Specified path " + CSV_FILES_PATH + " is not a directory");
            return;
        }

        createAutogeneratedFile();
        mergeCSVFiles();

    }

    private static void mergeCSVFiles() {

        try {
            List<Path> csvFiles = getCSVFiles();
            if (csvFiles == null || csvFiles.size() == 0) {
                System.out.println("No files with .csv extension were found");
                return;
            }
            for (Path path : csvFiles) {
                String headerCommentary = "# " + path.getFileName() + "\n";
                try {
                    Files.writeString(AUTOGENERATED_FILE_PATH, headerCommentary, StandardOpenOption.APPEND);
                    Files.write(AUTOGENERATED_FILE_PATH, Files.readAllBytes(path), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void deleteAutogeneratedFile() {
        try {
            Files.delete(AUTOGENERATED_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createAutogeneratedFile() {
        try {
            String header = "# This file is autogenerated by MergeCSV.java script\n# FILENAME;EXPECTED_ERROR_MESSAGE\n";
            Files.createFile(AUTOGENERATED_FILE_PATH);
            Files.writeString(AUTOGENERATED_FILE_PATH, header);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Path> getCSVFiles() throws IOException {
        return Files.walk(CSV_FILES_PATH).filter((path) -> path.getFileName().toString().toLowerCase().endsWith(".csv"))
                .toList();
    }

}

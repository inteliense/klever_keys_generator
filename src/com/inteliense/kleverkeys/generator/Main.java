package com.inteliense.kleverkeys.generator;

import com.inteliense.kleverkeys.generator.utils.FileGenerator;
import com.inteliense.kleverkeys.generator.utils.KeyGenerator;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner scnr;

    public static void main(String[] args) throws Exception {

        scnr = new Scanner(System.in);
        promptUser();

    }

    private static void promptUser() throws Exception {

        System.out.print("Enter the number of 256 bit keys: ");
        int bit256Keys = scnr.nextInt();
        System.out.print("Enter the number of 128 bit keys: ");
        int bit128Keys = scnr.nextInt();
        System.out.print("Enter the number of 256 bit IVs: ");
        int bit256Ivs = scnr.nextInt();
        System.out.print("Enter the number of 128 bit IVs: ");
        int bit128Ivs = scnr.nextInt();

        System.out.println();
        System.out.print("Enter the filename for the class (without extension): ");
        String temp = scnr.nextLine();
        String filename = scnr.nextLine();
        System.out.print("Enter the path for the files: ");
        String path = scnr.nextLine();

        String txtFilePath = fixPath(path, filename);
        String classFilePath = fixPath(path, filename);

        if(txtFilePath == null || classFilePath == null) {
            System.err.println("Invalid path or filename.");
            System.exit(1);
        }
        
        txtFilePath += ".txt";
        classFilePath += ".cpp";

        ArrayList<String[]> generated = new ArrayList<>();

        for(int i=0; i<bit256Keys; i++) {

            generated.add(KeyGenerator.generateKey(32));

        }

        for(int i=0; i<bit256Ivs; i++) {

            generated.add(KeyGenerator.generateIv(16));

        }

        for(int i=0; i<bit256Ivs; i++) {

            generated.add(KeyGenerator.generateKey(16));

        }

        for(int i=0; i<bit128Ivs; i++) {

            generated.add(KeyGenerator.generateIv(8));

        }

        new FileGenerator(txtFilePath, classFilePath, filename + ".cpp", generated);

        System.out.println();
        System.out.println("Class: " + classFilePath);
        System.out.println("Header: " + classFilePath.replace(".cpp", ".h"));
        System.out.println("Reference: " + txtFilePath);

    }

    private static String fixPath(String path, String filename) {

        if(path.contains("/")) {

            if(filename.contains("/")) {
                return null;
            }

            if(path.charAt(0) == '/') {

                if(path.charAt(path.length() - 1) == '/') {
                    return path + filename;
                } else {
                    return path + "/" + filename;
                }

            }

        //WINDOWS does not actually correct the path.
            
        } else if(path.contains("\\")) {

            if(filename.contains("\\")) {
                return null;
            }

            if(path.charAt(0) == '\\') {

                if(path.charAt(path.length() - 1) == '\\') {
                    return path + filename;
                } else {
                    return path + "\\" + filename;
                }

            }

        }

        return null;

    }

}

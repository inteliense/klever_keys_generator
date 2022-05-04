package com.inteliense.kleverkeys.generator.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class FileGenerator {

    public FileGenerator(String txtFilePath, String classPath, String className, ArrayList<String[]> generated) throws IOException {

        generateReference(txtFilePath, classPath, generated);
        generateClass(classPath, className, generated);
        generateHeader(classPath.replace(".cpp", ".h"), className);

    }

    private void generateHeader(String path, String className) throws IOException {

        String headerName = className.replace(".cpp", "");

        File file = new File(path);

        if(!file.exists()) {
            file.createNewFile();
        }

        PrintWriter pw = new PrintWriter(file);

        pw.println("// Generated by kleverkeys-generator");
        pw.println();
        pw.println("#ifndef " + headerName.toUpperCase() + "_H");
        pw.println("#define " + headerName.toUpperCase() + "_H");
        pw.println();
        pw.println("#include <string>");
        pw.println("#include \"HideString.h\"");
        pw.println();

        pw.println("class " + headerName + " {");
        pw.println();

        pw.println("public:");
        pw.println("\tstatic std::string get(std::string id);");
        pw.println("};");

        pw.println("\n");
        pw.println("#endif");

        pw.close();
        pw.flush();

    }

    private void generateClass(String path, String className, ArrayList<String[]> generated) throws IOException {

        File file = new File(path);

        if(!file.exists()) {
            file.createNewFile();
        }

        PrintWriter pw = new PrintWriter(file);

        pw.println("// Generated by kleverkeys-generator");
        pw.println();
        pw.println("#include \"" + className.replace(".cpp", ".h") + "\"");
        pw.println("#include \"easy_encrypt/EasyEncrypt.h\"");
        pw.println();

        Random rand = new Random(System.nanoTime() + 182045);

        for(int i=0; i<generated.size(); i++) {

            pw.println("//" + generated.get(i)[0]);

            String hex = generated.get(i)[0];
            int id = i + 1;
            int biteVal = rand.nextInt(128);

            String biteHex = SHA.getHex(new byte[]{(byte) biteVal});

            pw.print("DEFINE_HIDDEN_STRING(" + "String" + id + ", 0x" + biteHex);

            for(int x=0; x<hex.length(); x++) {

                String _c = "" + hex.charAt(x);
                String c = _c.toUpperCase();

                pw.print( ((x == 0) ? ", " : "") + "(\'" + c + "\')");

            }

            pw.print(")\n");

        }

        pw.println();

        pw.println("std::string " + className.replace(".cpp", "") + "::get(std::string id) {");
        pw.println();

        pw.println("\tstd::string upperId = EasyEncrypt::Utils::toUpperCase(id);");
        pw.println("\tstd::string hashedId = EasyEncrypt::Utils::toUpperCase(EasyEncrypt::SHA::Hex::hash256((char*) upperId.c_str()));");
        pw.println();

        pw.println("");

        for(int i=0; i<generated.size(); i++) {

            String strId = generated.get(i)[2];
            int id = i + 1;

            if(i == 0 ) {

                pw.println("\tif(hashedId == \"" + SHA.get256(strId.toUpperCase()).toUpperCase() + "\") {");
                pw.println("\t\treturn GetString" + id + "();");

            } else {

                pw.println("\t} else if(hashedId == \"" + SHA.get256(strId.toUpperCase()).toUpperCase() + "\") {");
                pw.println("\t\treturn GetString" + id + "();");

            }


        }

        pw.println("\t}\n\n");
        pw.println("\t//return a random hex value, disguised as a 32 bit hex key.");
        pw.println("\treturn EasyEncrypt::Utils::toUpperCase(" +
                "EasyEncrypt::Random::secureEncoded(EasyEncrypt::HEX, 32).c_str());");
        pw.println();
        pw.println();
        pw.println("}");

        pw.close();
        pw.flush();

    }

    private void generateReference(String path, String otherPath, ArrayList<String[]> generated) throws IOException {

        File file = new File(path);

        if(!file.exists()) {
            file.createNewFile();
        }

        PrintWriter pw = new PrintWriter(file);

        pw.println("## KEY & IV REFERENCE ##");
        pw.println();
        pw.println("Class file: " + otherPath);
        pw.println("\n");

        for(int i=0; i<generated.size(); i++) {

            int x = i+1;

            pw.println("Generated #" + x + ":  " + generated.get(i)[3]);
            pw.println();
            pw.println("ID\n" + generated.get(i)[2].toUpperCase());
            pw.println("Hex\n" + generated.get(i)[0].toUpperCase());
            pw.println("Base64\n" + generated.get(i)[1]);
            pw.println();
            pw.println();

        }

        pw.close();
        pw.flush();

    }

}

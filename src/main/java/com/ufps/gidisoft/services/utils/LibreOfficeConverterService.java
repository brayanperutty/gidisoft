package com.ufps.gidisoft.services.utils;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class LibreOfficeConverterService {

    public static File convertToPdf(File inputDocx, File outputDir) throws IOException, InterruptedException {
        if (!inputDocx.exists()) {
            throw new IllegalArgumentException("Archivo DOCX no existe: " + inputDocx.getAbsolutePath());
        }

        ProcessBuilder pb = new ProcessBuilder(
                "soffice",
                "--headless",
                "--convert-to", "pdf",
                "--outdir", outputDir.getAbsolutePath(),
                inputDocx.getAbsolutePath()
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // Leer salida para detectar errores
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("[LibreOffice] " + line);
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("LibreOffice falló al convertir. Código: " + exitCode);
        }

        // Construir nombre PDF robusto
        String docName = inputDocx.getName();
        String pdfName = docName.substring(0, docName.lastIndexOf('.')) + ".pdf";
        File outputPdf = new File(outputDir, pdfName);

        if (!outputPdf.exists()) {
            throw new RuntimeException("No se encontró el archivo PDF generado.");
        }
        if (outputPdf.length() == 0) {
            throw new IOException("Archivo PDF generado está vacío.");
        }

        return outputPdf;
    }

}

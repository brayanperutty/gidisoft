package com.ufps.gidisoft.utils;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class GenerateWordFormat {

    /**
     * Genera el documento de Informe de Gestión como un array de bytes (.docx)
     * @return byte[] del archivo Word generado
     */
    public byte[] generateInformeAsBytes() {
        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // Título principal centrado
            createTitle(document, "INFORME DE GESTIÓN DE GRUPOS DE INVESTIGACIÓN");

            // Tabla de cabecera
            createInfoHeader(document);

            // Tabla de información general
            createInfoTable(document);

            // Secciones de productos con tablas
            createSectionWithTable(document, "1. Proyectos de Investigación", "Proyecto", "Actividades", "% de Cumplimiento");
            createSectionWithTable(document, "2. Dirección de Trabajo de Grado y/o Tesis", "Título del Proyecto", "Director", "% de Cumplimiento");
            createSectionWithTable(document, "3. Organización de Eventos", "Nombre de Evento", "Fecha de realización", "% de Cumplimiento");
            createSectionWithTable(document, "4. Otras Actividades de Investigación (*)", "Nombre", "Tipo de Actividad", "% de Cumplimiento");

            // Productos obtenidos
            createSubtitle(document, "Productos obtenidos durante el semestre actual");
            String[] productos = {
                    "Actualización GrupLAC / CGIS",
                    "Convocatoria de reconocimiento Minciencias",
                    "Proyectos con financiación interna/externa",
                    "Artículo publicado o remitido",
                    "Participación en propuesta de investigación",
                    "Ponencias en eventos académicos",
                    "Dirección de trabajos de grado",
                    "Otros productos"
            };

            for (String producto : productos) {
                createSubSectionTable(document, producto);
            }

            // Firmas
            createSignatureSection(document, "ELABORADO POR");
            createSignatureSection(document, "REVISADO POR");

            document.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error generando el documento", e);
        }
    }

    private static void createTitle(XWPFDocument doc, String text) {
        XWPFParagraph p = doc.createParagraph();
        p.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = p.createRun();
        run.setBold(true);
        run.setFontSize(16);
        run.setText(text);
        run.addBreak();
    }

    private static void createSubtitle(XWPFDocument doc, String text) {
        XWPFParagraph p = doc.createParagraph();
        p.setSpacingBefore(300);
        p.setSpacingAfter(150);
        XWPFRun run = p.createRun();
        run.setBold(true);
        run.setFontSize(12);
        run.setText(text);
    }

    private static void createInfoHeader(XWPFDocument doc) {
        XWPFTable table = doc.createTable(1, 3);
        XWPFTableRow row = table.getRow(0);
        row.getCell(0).setText("Código: FO-IN-13");
        row.getCell(1).setText("Versión: 01");
        row.getCell(2).setText("Fecha: 17/02/2025");
    }

    private static void createInfoTable(XWPFDocument doc) {
        XWPFTable table = doc.createTable(4, 2);
        table.getRow(0).getCell(0).setText("GRUPO DE INVESTIGACIÓN");
        table.getRow(0).getCell(1).setText("         ");
        table.getRow(1).getCell(0).setText("DIRECTOR");
        table.getRow(1).getCell(1).setText("     ");
        table.getRow(2).getCell(0).setText("DEPARTAMENTO");
        table.getRow(2).getCell(1).setText("         ");
        table.getRow(3).getCell(0).setText("FACULTAD");
        table.getRow(3).getCell(1).setText("      ");
    }

    private static void createSectionWithTable(XWPFDocument doc, String sectionTitle, String col1, String col2, String col3) {
        createSubtitle(doc, sectionTitle);
        XWPFTable table = doc.createTable(5, 3);
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText(col1);
        header.getCell(1).setText(col2);
        header.getCell(2).setText(col3);
        for (int i = 1; i < 5; i++) {
            table.getRow(i).getCell(0).setText("     ");
            table.getRow(i).getCell(1).setText("     ");
            table.getRow(i).getCell(2).setText("     ");
        }
    }

    private static void createSubSectionTable(XWPFDocument doc, String title) {
        XWPFParagraph p = doc.createParagraph();
        p.setSpacingBefore(200);
        XWPFRun r = p.createRun();
        r.setItalic(true);
        r.setText(title);

        XWPFTable t = doc.createTable(2, 3);
        t.getRow(0).getCell(0).setText("Descripción");
        t.getRow(0).getCell(1).setText("Responsable");
        t.getRow(0).getCell(2).setText("Fecha");
        t.getRow(1).getCell(0).setText("     ");
        t.getRow(1).getCell(1).setText("     ");
        t.getRow(1).getCell(2).setText("     ");
    }

    private static void createSignatureSection(XWPFDocument doc, String title) {
        createSubtitle(doc, title);
        XWPFTable table = doc.createTable(2, 2);
        table.getRow(0).getCell(0).setText("NOMBRE:");
        table.getRow(0).getCell(1).setText("     ");
        table.getRow(1).getCell(0).setText("FIRMA:");
        table.getRow(1).getCell(1).setText("     ");
    }
}


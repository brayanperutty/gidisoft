package com.ufps.gidisoft.utils;

import com.ufps.gidisoft.responses.format.FormatDto;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.impl.values.XmlValueDisconnectedException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

@Service
public class GenerateWordFormat {

    private static final int BORDER_THICKNESS = 12;
    private static final int MARGIN_2CM_TWIPS = 1134;
    private static final int MARGIN_2_5CM_TWIPS = 1417;

    public byte[] generateInformeAsBytes(FormatDto format) {
        try (XWPFDocument document = new XWPFDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            setDocumentMargins(document);
            XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
            XWPFTable table = header.createTable(6, 9);

            setTableColumnWidths(table);
            setTableBorders(table);
            centerTable(table);
            setRowsHeight06CmPrecise(table);
            this.createGeneralInfoTitleFixed(table, format);
            this.createImageBlock(table);
            this.createTypeBlock(table);
            this.createNamesFormatBlock(table);
            this.createRow4Blocks(table, format);
            this.createRow5Blocks(table, format);

            XWPFTable tableGeneralInfo = document.createTable(3,3);
            setTableBorders(tableGeneralInfo);
            centerTable(tableGeneralInfo);
            setRowsHeight06CmPrecise(tableGeneralInfo);

            // Aplicar bordes negros a cada celda individualmente
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    this.validateInexistsCell(cell);
                }
            }

            document.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error generando el documento", e);
        }
    }

    private void createGeneralInfoTitleFixed(XWPFTable table, FormatDto format) {
        String[] labels = {"CÓDIGO", "VERSIÓN", "FECHA", "PÁGINA"};

        for (int rowIndex = 0; rowIndex < 4; rowIndex++) {
            XWPFTableRow row = table.getRow(rowIndex);

            if (row.getTableCells().size() >= 9) {

                XWPFTableCell labelCell = row.getCell(7);
                addTextToCell(labelCell, labels[rowIndex], ParagraphAlignment.LEFT, true);

                XWPFTableCell valueCell = row.getCell(8);

                switch (rowIndex) {
                    case 0: addTextToCell(valueCell, format.getCode(),  ParagraphAlignment.CENTER, false); break;
                    case 1: addTextToCell(valueCell, format.getVersion(),  ParagraphAlignment.CENTER, false); break;
                    case 2: addTextToCell(valueCell, format.getDateFormatter(),  ParagraphAlignment.CENTER, false); break;
                    default: break;
                }

            }
        }
    }

    private void createImageBlock(XWPFTable table) {
        XWPFTableCell firstCell = null;

        for (int rowIndex = 0; rowIndex <= 3; rowIndex++) {
            XWPFTableRow row = table.getRow(rowIndex);
            XWPFTableCell cell = row.getCell(0);
            CTTcPr tcPr = getCellCTTcPr(cell);

            // Combinar horizontalmente (0 y 1)
            tcPr.addNewGridSpan().setVal(BigInteger.valueOf(2));

            // Combinar verticalmente
            CTVMerge vmerge = tcPr.addNewVMerge();
            if (rowIndex == 0) {
                vmerge.setVal(STMerge.RESTART);
                firstCell = cell;
            } else {
                vmerge.setVal(STMerge.CONTINUE);
                cell.removeParagraph(0);
            }

            // Eliminar visualmente celda (rowIndex,1) tapada
            XWPFTableCell rightCell = row.getCell(1);
            if (rightCell != null) {
                try (XmlCursor cursor = rightCell.getCTTc().newCursor()) {
                    cursor.removeXml();
                }
            }
        }

        if (firstCell != null) {
            addImageToCell(firstCell);
        }
    }

    // NUEVA FUNCIÓN: Procesar toda la fila 4 de una vez
    private void createRow4Blocks(XWPFTable table, FormatDto format) {
        XWPFTableRow row = table.getRow(4);

        this.createTableCell(row, 4, format);

        this.deleteCellsSixToNineBlock(row);
        this.deleteCellsThreeToFiveBlock(row);
        this.deleteCellsZeroToTwoBlock(row);
    }

    private void createRow5Blocks(XWPFTable table, FormatDto format) {
        XWPFTableRow row = table.getRow(5);

        this.createTableCell(row, 5, format);

        this.deleteCellsSixToNineBlock(row);
        this.deleteCellsThreeToFiveBlock(row);
        this.deleteCellsZeroToTwoBlock(row);
    }

    private void createTableCell(XWPFTableRow row, int indexRow, FormatDto format) {
        // BLOQUE 1: CreatedBy (columnas 0-2)
        XWPFTableCell cell1 = row.getCell(0);
        CTTcPr tcPr1 = getCellCTTcPr(cell1);
        tcPr1.addNewGridSpan().setVal(BigInteger.valueOf(3));

        // BLOQUE 2: ReviewBy (columnas 3-5)
        XWPFTableCell cell2 = row.getCell(3);
        CTTcPr tcPr2 = getCellCTTcPr(cell2);
        tcPr2.addNewGridSpan().setVal(BigInteger.valueOf(3));

        // BLOQUE 3: ApproveBy (columnas 6-8)
        XWPFTableCell cell3 = row.getCell(6);
        CTTcPr tcPr3 = getCellCTTcPr(cell3);
        tcPr3.addNewGridSpan().setVal(BigInteger.valueOf(3));

        if(indexRow == 4){
            addTextToCell(cell1, "ELABORÓ", ParagraphAlignment.CENTER, true);
            addTextToCell(cell2, "REVISÓ", ParagraphAlignment.CENTER, true);
            addTextToCell(cell3, "APROBÓ",  ParagraphAlignment.CENTER, true);
        }

        if(indexRow == 5){
            addTextToCell(cell1, format.getManagerUsers().getCreatedBy(), ParagraphAlignment.CENTER, false);
            addTextToCell(cell2, format.getManagerUsers().getReviewBy(), ParagraphAlignment.CENTER, false);
            addTextToCell(cell3, format.getManagerUsers().getApproveBy(),  ParagraphAlignment.CENTER, false);
        }
    }

    private void deleteCellsZeroToTwoBlock(XWPFTableRow row){
        // Eliminar celdas 1, 2 (del bloque CreatedBy)
        for (int colIndex = 2; colIndex >= 1; colIndex--) {
            if (colIndex < row.getTableCells().size()) {
                XWPFTableCell extraCell = row.getCell(colIndex);
                if (extraCell != null) {
                    try (XmlCursor cursor = extraCell.getCTTc().newCursor()) {
                        cursor.removeXml();
                    }
                }
            }
        }
    }

    private void deleteCellsThreeToFiveBlock(XWPFTableRow row){
        // Eliminar celdas 4, 5 (del bloque ReviewBy)
        for (int colIndex = 5; colIndex >= 4; colIndex--) {
            if (colIndex < row.getTableCells().size()) {
                XWPFTableCell extraCell = row.getCell(colIndex);
                if (extraCell != null) {
                    try (XmlCursor cursor = extraCell.getCTTc().newCursor()) {
                        cursor.removeXml();
                    }
                }
            }
        }
    }

    private void deleteCellsSixToNineBlock(XWPFTableRow row){
        // Ahora eliminar las celdas sobrantes de derecha a izquierda
        // Eliminar celdas 7, 8 (del bloque ApproveBy)
        for (int colIndex = 8; colIndex >= 7; colIndex--) {
            if (colIndex < row.getTableCells().size()) {
                XWPFTableCell extraCell = row.getCell(colIndex);
                if (extraCell != null) {
                    try (XmlCursor cursor = extraCell.getCTTc().newCursor()) {
                        cursor.removeXml();
                    }
                }
            }
        }
    }

    private void createTypeBlock(XWPFTable table) {

        XWPFTableCell firstCell = null;
        for (int rowIndex = 0; rowIndex <= 1; rowIndex++) {
            XWPFTableRow row = table.getRow(rowIndex);
            XWPFTableCell cell = row.getCell(2);
            CTTcPr tcPr = getCellCTTcPr(cell);

            // Combinar horizontalmente (columnas 2 hasta 6)
            tcPr.addNewGridSpan().setVal(BigInteger.valueOf(5));

            // Combinar verticalmente
            CTVMerge vmerge = tcPr.addNewVMerge();
            if (rowIndex == 0) {
                vmerge.setVal(STMerge.RESTART);
                firstCell = cell;
            } else {
                vmerge.setVal(STMerge.CONTINUE);
                cell.removeParagraph(0);
            }
            this.deleteCellsSixToThree(row);

        }
        if (firstCell != null) {
            addTextToCell(firstCell, "INVESTIGACIÓN", ParagraphAlignment.CENTER, true);
        }
    }

    private void createNamesFormatBlock(XWPFTable table) {
        XWPFTableCell firstCell = null;
        for (int rowIndex = 2; rowIndex <= 3; rowIndex++) {
            XWPFTableRow row = table.getRow(rowIndex);
            XWPFTableCell cell = row.getCell(2);
            CTTcPr tcPr = getCellCTTcPr(cell);

            // Combinar horizontalmente (columnas 2 hasta 6)
            tcPr.addNewGridSpan().setVal(BigInteger.valueOf(5));

            // Combinar verticalmente
            CTVMerge vmerge = tcPr.addNewVMerge();
            if (rowIndex == 2) {
                vmerge.setVal(STMerge.RESTART);
                firstCell = cell;
            } else {
                vmerge.setVal(STMerge.CONTINUE);
                cell.removeParagraph(0);
            }

            this.deleteCellsSixToThree(row);
        }

        if (firstCell != null) {
            CTTcPr tcPr = getCellCTTcPr(firstCell);
            CTShd shd = tcPr.isSetShd() ? tcPr.getShd() : tcPr.addNewShd();
            shd.setVal(STShd.CLEAR);
            shd.setColor("auto");
            shd.setFill("C10000");
            addTextToCell(firstCell, "INFORME DE GESTIÓN DE GRUPOS DE INVESTIGACIÓN",
                    ParagraphAlignment.CENTER, true);
        }
    }

    private void deleteCellsSixToThree(XWPFTableRow row){
        // SOLUCIÓN: Eliminar de derecha a izquierda para evitar reindexación
        for (int colIndex = 6; colIndex >= 3; colIndex--) {
            if (colIndex < row.getTableCells().size()) {
                XWPFTableCell extraCell = row.getCell(colIndex);
                if (extraCell != null) {
                    try (XmlCursor cursor = extraCell.getCTTc().newCursor()) {
                        cursor.removeXml();
                    }
                }
            }
        }
    }

    private void validateInexistsCell(XWPFTableCell cell) {
        try {
            if (cell.getCTTc().getDomNode() != null && !cell.getCTTc().getDomNode().isSameNode(null)) {
                setCellBordersBlack(cell);
            }
        } catch (XmlValueDisconnectedException e) {
            // Ignorar celda eliminada
        }
    }

    private CTTcPr getCellCTTcPr(XWPFTableCell cell) {
        CTTc cttc = cell.getCTTc();
        return cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
    }

    private void setTableBorders(XWPFTable table) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders() : tblPr.addNewTblBorders();

        CTBorder hBorder = CTBorder.Factory.newInstance();
        hBorder.setVal(STBorder.SINGLE);
        hBorder.setSz(BigInteger.valueOf(BORDER_THICKNESS));
        hBorder.setColor("000000");

        borders.setBottom(hBorder);
        borders.setTop(hBorder);
        borders.setLeft(hBorder);
        borders.setRight(hBorder);
        borders.setInsideH(hBorder);
        borders.setInsideV(hBorder);
    }

    private void setCellBordersBlack(XWPFTableCell cell) {
        CTTcPr tcPr = getCellCTTcPr(cell);
        CTTcBorders borders = tcPr.isSetTcBorders() ? tcPr.getTcBorders() : tcPr.addNewTcBorders();

        CTBorder border = CTBorder.Factory.newInstance();
        border.setVal(STBorder.SINGLE);
        border.setSz(BigInteger.valueOf(BORDER_THICKNESS));
        border.setColor("000000");

        borders.setTop(border);
        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setInsideH(border);
        borders.setInsideV(border);
    }

    private void setTableColumnWidths(XWPFTable table) {
        CTTblGrid tblGrid = table.getCTTbl().getTblGrid();
        if (tblGrid == null) {
            tblGrid = table.getCTTbl().addNewTblGrid();
        }

        tblGrid.getGridColList().clear();

        int[] columnWidths = {
                760,760,1846,
                1122,1122,1122,
                1102,1112,1152};

        for (int width : columnWidths) {
            CTTblGridCol gridCol = tblGrid.addNewGridCol();
            gridCol.setW(BigInteger.valueOf(width));
        }

        // También establecer el ancho total de la tabla
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (tblPr == null) {
            tblPr = table.getCTTbl().addNewTblPr();
        }

        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblWidth.setType(STTblWidth.DXA);
        tblWidth.setW(BigInteger.valueOf(10100)); // Suma total aproximada
    }

    private void centerTable(XWPFTable table) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (tblPr == null) {
            tblPr = table.getCTTbl().addNewTblPr();
        }

        // Establecer la alineación de la tabla al centro
        CTJcTable jc;
        if (tblPr.isSetJc()) {
            jc = tblPr.getJc();
        } else {
            jc = tblPr.addNewJc();
        }
        jc.setVal(STJcTable.CENTER);
    }

    private void addImageToCell(XWPFTableCell cell) {
        try {
            // Cargar la imagen desde los recursos
            ClassPathResource imageResource = new ClassPathResource("static/img/Logo-vertical.jpg");

            if (!imageResource.exists()) {
                return;
            }

            try (InputStream imageStream = imageResource.getInputStream()) {
                // Limpiar paragrafos existentes en la celda
                while (!cell.getParagraphs().isEmpty()) {
                    cell.removeParagraph(0);
                }

                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

                // Crear un nuevo párrafo para la imagen
                XWPFParagraph paragraph = cell.addParagraph();
                paragraph.setAlignment(ParagraphAlignment.CENTER);

                XWPFRun run = paragraph.createRun();

                // Insertar la imagen
                run.addPicture(
                        imageStream,
                        Document.PICTURE_TYPE_JPEG,
                        "Logo-vertical.jpg",
                        Units.toEMU(60), // Ancho en EMU (120 pixeles aprox.)
                        Units.toEMU(50)   // Alto en EMU (80 pixeles aprox.)
                );
            }
        } catch (Exception e) {
            // En caso de error, agregar texto alternativo
            XWPFParagraph paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.setText("Logo");
            run.setBold(true);
        }
    }

    private void addTextToCell(XWPFTableCell cell, String text, ParagraphAlignment alignment, boolean bold) {
        try {
            // Limpiar paragrafos existentes en la celda
            while (!cell.getParagraphs().isEmpty()) {
                cell.removeParagraph(0);
            }

            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

            // Crear un nuevo párrafo para el texto
            XWPFParagraph paragraph = cell.addParagraph();
            paragraph.setAlignment(alignment);

            XWPFRun run = paragraph.createRun();
            run.setText(text);
            run.setBold(bold);
            run.setFontSize(9);
            run.setFontFamily("Arial");

        } catch (Exception e) {
            //
        }
    }

    private void setRowsHeight06CmPrecise(XWPFTable table) {
        for (int i = 0; i < table.getRows().size(); i++) {
            if(i < 4) table.getRows().get(i).setHeight(257);
            else table.getRows().get(i).setHeight(312);
        }
    }

    private void setDocumentMargins(XWPFDocument document) {
        CTDocument1 doc = document.getDocument();
        CTBody body = doc.getBody();

        CTSectPr sectPr = body.isSetSectPr() ? body.getSectPr() : body.addNewSectPr();
        CTPageMar pageMar = sectPr.isSetPgMar() ? sectPr.getPgMar() : sectPr.addNewPgMar();

        pageMar.setTop(BigInteger.valueOf(MARGIN_2_5CM_TWIPS));
        pageMar.setBottom(BigInteger.valueOf(MARGIN_2CM_TWIPS));
        pageMar.setLeft(BigInteger.valueOf(MARGIN_2CM_TWIPS));
        pageMar.setRight(BigInteger.valueOf(MARGIN_2CM_TWIPS));

        pageMar.setHeader(BigInteger.valueOf(708));
        pageMar.setFooter(BigInteger.valueOf(708));
    }

}


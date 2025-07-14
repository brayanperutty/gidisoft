package com.ufps.gidisoft.utils;

import com.ufps.gidisoft.exceptions.BadRequestException;
import com.ufps.gidisoft.responses.exceptions.ErrorResponse;
import com.ufps.gidisoft.responses.format.*;
import com.ufps.gidisoft.services.formats.directions.DirectionService;
import com.ufps.gidisoft.services.formats.events.EventService;
import com.ufps.gidisoft.services.formats.others.OtherActivityService;
import com.ufps.gidisoft.services.formats.products.ProductService;
import com.ufps.gidisoft.services.formats.projects.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateWordFormat {

    /*
     * Services
     */
    private final ProjectService  projectService;
    private final DirectionService  directionService;
    private final EventService eventService;
    private final OtherActivityService otherActivityService;
    private final ProductService productService;

    private static final int BORDER_THICKNESS = 9;
    private static final int MARGIN_2CM_TWIPS = 1134;
    private static final int MARGIN_2_5CM_TWIPS = 1417;
    private static final String GREY_COLOR_CODE = "D9D9D9";
    private static final String RED_COLOR_CODE = "C10000";
    private static final String CURUBA_COLOR_CODE = "FFEAD9";
    private static final String COMPLIANCE_PERCENTAGE_TEXT = "% de Cumplimiento";

    public byte[] generateInformeAsBytes(FormatDto format) {
        try (XWPFDocument document = new XWPFDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            setDocumentMargins(document);
            XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
            XWPFParagraph spacer3 = document.createParagraph();
            spacer3.setSpacingBefore(200);
            XWPFTable table = header.createTable(6, 9);
            int[] columnWidthsTableHeader = {
                    760,760,1846,
                    1122,1122,1122,
                    1102,1112,1152};

            setTableColumnWidths(table, columnWidthsTableHeader);
            setTableBorders(table);
            centerTable(table);
            setRowsHeight06CmPrecise(table);
            this.createGeneralInfoTitleFixed(table, format);
            this.createImageBlock(table);
            this.createTypeBlock(table);
            this.createNamesFormatBlock(table);
            this.createRow4Blocks(table, format);
            this.createRow5Blocks(table, format);


            XWPFTable tableGeneralInfo = document.createTable(4,12);
            int[] columnWidthsTableGeneralInfo = {
                    842,842,842,842,
                    842,842,842,842,
                    842,842,842,842};
            setTableColumnWidths(tableGeneralInfo, columnWidthsTableGeneralInfo);
            setTableBorders(tableGeneralInfo);
            centerTable(tableGeneralInfo);
            setRowsHeight06CmPrecise(tableGeneralInfo);

            this.createTableGeneralInfo(tableGeneralInfo, format);
            this.createSpacer(document, 1);

            this.createTableProjects(this.projectService.findByFormatId(format.getFormatId()), document);
            this.createSpacer(document, 1);

            this.createTableDirections(this.directionService.findByFormatId(format.getFormatId()), document);
            this.createSpacer(document, 1);

            this.createTableEvents(this.eventService.findByFormatId(format.getFormatId()), document);
            this.createSpacer(document, 1);

            this.createTableOtherActivities(this.otherActivityService.findByFormatId(format.getFormatId()), document);
            this.createSpacer(document, 1);

            this.createProductTable(document, this.productService.findByFormatIdGrouped(format.getFormatId()));
            this.createSpacer(document, 1);

            // Aplicar bordes negros a cada celda individualmente
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    this.validateInexistsCell(cell);
                }
            }

            document.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new BadRequestException(new ErrorResponse());
        }
    }

    private void createSpacer(XWPFDocument document, int spacer){
        XWPFParagraph spacer3 = document.createParagraph();
        spacer3.setSpacingBefore(spacer);
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

    private void createTableGeneralInfo(XWPFTable table, FormatDto format) {

        //Primera fila
        XWPFTableRow rowGroup = table.getRow(0);
        XWPFTableCell cell1 = rowGroup.getCell(0);
        CTTcPr tcPr1 = getCellCTTcPr(cell1);
        tcPr1.addNewGridSpan().setVal(BigInteger.valueOf(5));
        CTShd shd1 = tcPr1.isSetShd() ? tcPr1.getShd() : tcPr1.addNewShd();
        shd1.setVal(STShd.CLEAR);
        shd1.setColor("auto");
        shd1.setFill(GREY_COLOR_CODE);
        rowGroup.setHeight(850);

        XWPFTableCell cell2 = rowGroup.getCell(5);
        CTTcPr tcPr2 = getCellCTTcPr(cell2);
        tcPr2.addNewGridSpan().setVal(BigInteger.valueOf(7));

        //Segunda fila
        XWPFTableRow rowDirector = table.getRow(1);
        XWPFTableCell cell3 = rowDirector.getCell(0);
        CTTcPr tcPr3 = getCellCTTcPr(cell3);
        tcPr3.addNewGridSpan().setVal(BigInteger.valueOf(2));
        CTShd shd2 = tcPr3.isSetShd() ? tcPr3.getShd() : tcPr3.addNewShd();
        shd2.setVal(STShd.CLEAR);
        shd2.setColor("auto");
        shd2.setFill(GREY_COLOR_CODE);
        rowDirector.setHeight(315);

        XWPFTableCell cell4 = rowDirector.getCell(2);
        CTTcPr tcPr4 = getCellCTTcPr(cell4);
        tcPr4.addNewGridSpan().setVal(BigInteger.valueOf(10));

        //Tercera fila
        XWPFTableRow rowDepartment = table.getRow(2);
        XWPFTableCell cell5 = rowDepartment.getCell(0);
        CTTcPr tcPr5 = getCellCTTcPr(cell5);
        tcPr5.addNewGridSpan().setVal(BigInteger.valueOf(3));
        CTShd shd3 = tcPr5.isSetShd() ? tcPr5.getShd() : tcPr5.addNewShd();
        shd3.setVal(STShd.CLEAR);
        shd3.setColor("auto");
        shd3.setFill(GREY_COLOR_CODE);
        rowDepartment.setHeight(315);

        XWPFTableCell cell6 = rowDepartment.getCell(3);
        CTTcPr tcPr6 = getCellCTTcPr(cell6);
        tcPr6.addNewGridSpan().setVal(BigInteger.valueOf(4));

        XWPFTableCell cell7 = rowDepartment.getCell(7);
        CTTcPr tcPr7 = getCellCTTcPr(cell7);
        tcPr7.addNewGridSpan().setVal(BigInteger.valueOf(2));
        CTShd shd4 = tcPr7.isSetShd() ? tcPr7.getShd() : tcPr7.addNewShd();
        shd4.setVal(STShd.CLEAR);
        shd4.setColor("auto");
        shd4.setFill(GREY_COLOR_CODE);

        XWPFTableCell cell8 = rowDepartment.getCell(9);
        CTTcPr tcPr8 = getCellCTTcPr(cell8);
        tcPr8.addNewGridSpan().setVal(BigInteger.valueOf(3));

        //Cuarta fila
        XWPFTableRow rowAcademicPeriod = table.getRow(3);
        XWPFTableCell cell9 = rowAcademicPeriod.getCell(0);
        CTTcPr tcPr9 = getCellCTTcPr(cell9);
        tcPr9.addNewGridSpan().setVal(BigInteger.valueOf(4));
        CTShd shd5 = tcPr9.isSetShd() ? tcPr9.getShd() : tcPr9.addNewShd();
        shd5.setVal(STShd.CLEAR);
        shd5.setColor("auto");
        shd5.setFill(GREY_COLOR_CODE);
        rowAcademicPeriod.setHeight(315);

        XWPFTableCell cell10 = rowAcademicPeriod.getCell(4);
        CTTcPr tcPr10 = getCellCTTcPr(cell10);
        tcPr10.addNewGridSpan().setVal(BigInteger.valueOf(4));

        XWPFTableCell cell11 = rowAcademicPeriod.getCell(8);
        CTTcPr tcPr11 = getCellCTTcPr(cell11);
        tcPr11.addNewGridSpan().setVal(BigInteger.valueOf(2));
        CTShd shd6 = tcPr11.isSetShd() ? tcPr11.getShd() : tcPr11.addNewShd();
        shd6.setVal(STShd.CLEAR);
        shd6.setColor("auto");
        shd6.setFill(GREY_COLOR_CODE);

        XWPFTableCell cell12 = rowAcademicPeriod.getCell(10);
        CTTcPr tcPr12 = getCellCTTcPr(cell12);
        tcPr12.addNewGridSpan().setVal(BigInteger.valueOf(2));

        //ELiminar celdas extra fila 1
        this.deleteCells(rowGroup, 6, 11);
        this.deleteCells(rowGroup, 1, 4);

        //ELiminar celdas extra fila 2
        this.deleteCells(rowDirector, 3, 11);
        this.deleteCells(rowDirector, 1, 1);

        //ELiminar celdas extra fila 3
        this.deleteCells(rowDepartment, 10, 11);
        this.deleteCells(rowDepartment, 8, 8);
        this.deleteCells(rowDepartment, 4, 6);
        this.deleteCells(rowDepartment, 1, 2);

        //ELiminar celdas extra fila 4
        this.deleteCells(rowAcademicPeriod, 11, 11);
        this.deleteCells(rowAcademicPeriod, 9, 9);
        this.deleteCells(rowAcademicPeriod, 5, 7);
        this.deleteCells(rowAcademicPeriod, 1, 3);

        addTextToCell(cell1, "GRUPO DE INVESTIGACIÓN", ParagraphAlignment.CENTER, true);
        addTextToCell(cell2, format.getFormat().getGroup().getName(), ParagraphAlignment.CENTER, false);

        addTextToCell(cell3, "DIRECTOR", ParagraphAlignment.CENTER, true);
        addTextToCell(cell4, format.getFormat().getDirector().getName(), ParagraphAlignment.CENTER, false);

        addTextToCell(cell5, "DEPARTAMENTO", ParagraphAlignment.CENTER, true);
        addTextToCell(cell6, format.getFormat().getDepartment(), ParagraphAlignment.CENTER, false);

        addTextToCell(cell7, "FACULTAD", ParagraphAlignment.CENTER, true);
        addTextToCell(cell8, format.getFormat().getFaculty().getName(), ParagraphAlignment.CENTER, false);

        addTextToCell(cell9, "SEMESTRE ACADÉMICO", ParagraphAlignment.CENTER, true);
        addTextToCell(cell10, format.getFormat().getAcademicPeriod().getPeriod(), ParagraphAlignment.CENTER, false);

        addTextToCell(cell11, "AÑO", ParagraphAlignment.CENTER, true);
        addTextToCell(cell12, String.valueOf(format.getFormat().getAcademicPeriod().getYear()), ParagraphAlignment.CENTER, false);

    }

    private void deleteCells(XWPFTableRow row, int colIndexStart, int colIndexEnd) {
        // Eliminar celdas 1, 2 (del bloque CreatedBy)
        for (int colIndex = colIndexEnd; colIndex >= colIndexStart; colIndex--) {
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

    private void createTableProjects(List<ProjectDto> projects, XWPFDocument document){

        XWPFTable table;
        if(projects.isEmpty()){
            table = document.createTable(5, 3);
        } else {
            table = document.createTable(projects.size() + 2, 3);
        }

        int[] columnWidthsTable= {5000,3600,1500};
        setTableColumnWidths(table, columnWidthsTable);
        setTableBorders(table);
        centerTable(table);
        setRowsHeight06CmPrecise(table);

        //Section name
        XWPFTableRow rowSectionName = table.getRow(0);
        XWPFTableCell cell1 = rowSectionName.getCell(0);
        CTTcPr tcPr1 = getCellCTTcPr(cell1);
        CTShd shd1 = tcPr1.isSetShd() ? tcPr1.getShd() : tcPr1.addNewShd();
        shd1.setVal(STShd.CLEAR);
        shd1.setColor("auto");
        shd1.setFill(RED_COLOR_CODE);
        rowSectionName.setHeight(330);

        this.deleteCells(rowSectionName, 1, 2);

        //Titles column
        XWPFTableRow rowTitles = table.getRow(1);
        XWPFTableCell cell2 = rowTitles.getCell(0);
        CTTcPr tcPr2 = getCellCTTcPr(cell2);
        CTShd shd2 = tcPr2.isSetShd() ? tcPr2.getShd() : tcPr2.addNewShd();
        shd2.setVal(STShd.CLEAR);
        shd2.setColor("auto");
        shd2.setFill(GREY_COLOR_CODE);
        rowTitles.setHeight(380);

        XWPFTableCell cell3 = rowTitles.getCell(1);
        CTTcPr tcPr3 = getCellCTTcPr(cell3);
        CTShd shd3 = tcPr3.isSetShd() ? tcPr3.getShd() : tcPr3.addNewShd();
        shd3.setVal(STShd.CLEAR);
        shd3.setColor("auto");
        shd3.setFill(GREY_COLOR_CODE);

        XWPFTableCell cell4 = rowTitles.getCell(2);
        CTTcPr tcPr4 = getCellCTTcPr(cell4);
        CTShd shd4 = tcPr4.isSetShd() ? tcPr4.getShd() : tcPr4.addNewShd();
        shd4.setVal(STShd.CLEAR);
        shd4.setColor("auto");
        shd4.setFill(GREY_COLOR_CODE);

        addTextToCell(cell1, "1. Proyectos de Investigación", ParagraphAlignment.CENTER, true);
        addTextToCell(cell2, "Proyecto", ParagraphAlignment.CENTER, true);
        addTextToCell(cell3, "Actividades", ParagraphAlignment.CENTER, true);
        addTextToCell(cell4, COMPLIANCE_PERCENTAGE_TEXT, ParagraphAlignment.CENTER, true);

        for (int i = 0; i < projects.size(); i++) {
            XWPFTableRow rowProject = table.getRow(2 + i);
            rowSectionName.setHeight(330);

            XWPFTableCell cellProjectName = rowProject.getCell(0);
            addTextToCell(cellProjectName, projects.get(i).getName(), ParagraphAlignment.CENTER, false);

            XWPFTableCell cellActivities = rowProject.getCell(1);
            addTextToCell(cellActivities, projects.get(i).getActivities(), ParagraphAlignment.CENTER, false);

            XWPFTableCell cellCompliancePercentage = rowProject.getCell(2);
            addTextToCell(cellCompliancePercentage, String.valueOf(projects.get(i).getCompliancePercentage()),
                    ParagraphAlignment.CENTER, false);
        }
    }

    private void createTableDirections(List<DirectionDto> directions, XWPFDocument document){

        XWPFTable table;
        if(directions.isEmpty()){
            table = document.createTable(5, 3);
        } else {
            table = document.createTable(directions.size() + 2, 3);
        }

        int[] columnWidthsTable= {5400,3200,1500};
        setTableColumnWidths(table, columnWidthsTable);
        setTableBorders(table);
        centerTable(table);
        setRowsHeight06CmPrecise(table);

        //Section name
        XWPFTableRow rowSectionName = table.getRow(0);
        XWPFTableCell cell1 = rowSectionName.getCell(0);
        CTTcPr tcPr1 = getCellCTTcPr(cell1);
        CTShd shd1 = tcPr1.isSetShd() ? tcPr1.getShd() : tcPr1.addNewShd();
        shd1.setVal(STShd.CLEAR);
        shd1.setColor("auto");
        shd1.setFill(RED_COLOR_CODE);
        rowSectionName.setHeight(330);

        this.deleteCells(rowSectionName, 1, 2);

        //Titles column
        XWPFTableRow rowTitles = table.getRow(1);
        XWPFTableCell cell2 = rowTitles.getCell(0);
        CTTcPr tcPr2 = getCellCTTcPr(cell2);
        CTShd shd2 = tcPr2.isSetShd() ? tcPr2.getShd() : tcPr2.addNewShd();
        shd2.setVal(STShd.CLEAR);
        shd2.setColor("auto");
        shd2.setFill(GREY_COLOR_CODE);
        rowTitles.setHeight(380);

        XWPFTableCell cell3 = rowTitles.getCell(1);
        CTTcPr tcPr3 = getCellCTTcPr(cell3);
        CTShd shd3 = tcPr3.isSetShd() ? tcPr3.getShd() : tcPr3.addNewShd();
        shd3.setVal(STShd.CLEAR);
        shd3.setColor("auto");
        shd3.setFill(GREY_COLOR_CODE);

        XWPFTableCell cell4 = rowTitles.getCell(2);
        CTTcPr tcPr4 = getCellCTTcPr(cell4);
        CTShd shd4 = tcPr4.isSetShd() ? tcPr4.getShd() : tcPr4.addNewShd();
        shd4.setVal(STShd.CLEAR);
        shd4.setColor("auto");
        shd4.setFill(GREY_COLOR_CODE);

        addTextToCell(cell1, "2. Participación en Dirección de Trabajo de Grado y/o Tesis", ParagraphAlignment.CENTER, true);
        addTextToCell(cell2, "Título del proyecto", ParagraphAlignment.CENTER, true);
        addTextToCell(cell3, "Director", ParagraphAlignment.CENTER, true);
        addTextToCell(cell4, COMPLIANCE_PERCENTAGE_TEXT, ParagraphAlignment.CENTER, true);

        for (int i = 0; i < directions.size(); i++) {
            XWPFTableRow rowProject = table.getRow(2 + i);
            rowProject.setHeight(330);

            XWPFTableCell cellProjectName = rowProject.getCell(0);
            addTextToCell(cellProjectName, directions.get(i).getName(), ParagraphAlignment.CENTER, false);

            XWPFTableCell cellActivities = rowProject.getCell(1);
            addTextToCell(cellActivities, directions.get(i).getDirectorName(), ParagraphAlignment.CENTER, false);

            XWPFTableCell cellCompliancePercentage = rowProject.getCell(2);
            addTextToCell(cellCompliancePercentage, String.valueOf(directions.get(i).getCompliancePercentage()),
                    ParagraphAlignment.CENTER, false);
        }
    }

    private void createTableEvents(List<EventDto> events, XWPFDocument document){

        XWPFTable table;
        if(events.isEmpty()){
            table = document.createTable(5, 3);
        } else {
            table = document.createTable(events.size() + 2, 3);
        }

        int[] columnWidthsTable= {5700,2900,1500};
        setTableColumnWidths(table, columnWidthsTable);
        setTableBorders(table);
        centerTable(table);
        setRowsHeight06CmPrecise(table);

        //Section name
        XWPFTableRow rowSectionName = table.getRow(0);
        XWPFTableCell cell1 = rowSectionName.getCell(0);
        CTTcPr tcPr1 = getCellCTTcPr(cell1);
        CTShd shd1 = tcPr1.isSetShd() ? tcPr1.getShd() : tcPr1.addNewShd();
        shd1.setVal(STShd.CLEAR);
        shd1.setColor("auto");
        shd1.setFill(RED_COLOR_CODE);
        rowSectionName.setHeight(330);

        this.deleteCells(rowSectionName, 1, 2);

        //Titles column
        XWPFTableRow rowTitles = table.getRow(1);
        XWPFTableCell cell2 = rowTitles.getCell(0);
        CTTcPr tcPr2 = getCellCTTcPr(cell2);
        CTShd shd2 = tcPr2.isSetShd() ? tcPr2.getShd() : tcPr2.addNewShd();
        shd2.setVal(STShd.CLEAR);
        shd2.setColor("auto");
        shd2.setFill(GREY_COLOR_CODE);
        rowTitles.setHeight(380);

        XWPFTableCell cell3 = rowTitles.getCell(1);
        CTTcPr tcPr3 = getCellCTTcPr(cell3);
        CTShd shd3 = tcPr3.isSetShd() ? tcPr3.getShd() : tcPr3.addNewShd();
        shd3.setVal(STShd.CLEAR);
        shd3.setColor("auto");
        shd3.setFill(GREY_COLOR_CODE);

        XWPFTableCell cell4 = rowTitles.getCell(2);
        CTTcPr tcPr4 = getCellCTTcPr(cell4);
        CTShd shd4 = tcPr4.isSetShd() ? tcPr4.getShd() : tcPr4.addNewShd();
        shd4.setVal(STShd.CLEAR);
        shd4.setColor("auto");
        shd4.setFill(GREY_COLOR_CODE);

        addTextToCell(cell1, "3. Organización de Eventos de Investigación / Científicos", ParagraphAlignment.CENTER, true);
        addTextToCell(cell2, "Nombre del evento", ParagraphAlignment.CENTER, true);
        addTextToCell(cell3, "Fecha de realización", ParagraphAlignment.CENTER, true);
        addTextToCell(cell4, COMPLIANCE_PERCENTAGE_TEXT, ParagraphAlignment.CENTER, true);

        for (int i = 0; i < events.size(); i++) {
            XWPFTableRow rowProject = table.getRow(2 + i);
            rowProject.setHeight(330);

            XWPFTableCell cellProjectName = rowProject.getCell(0);
            addTextToCell(cellProjectName, events.get(i).getName(), ParagraphAlignment.CENTER, false);

            XWPFTableCell cellActivities = rowProject.getCell(1);
            addTextToCell(cellActivities, events.get(i).getCreatedAtFormatted(), ParagraphAlignment.CENTER, false);

            XWPFTableCell cellCompliancePercentage = rowProject.getCell(2);
            addTextToCell(cellCompliancePercentage, String.valueOf(events.get(i).getCompliancePercentage()),
                    ParagraphAlignment.CENTER, false);
        }
    }

    private void createTableOtherActivities(List<OtherActivityDto> otherActivities, XWPFDocument document){

        XWPFTable table;
        if(otherActivities.isEmpty()){
            table = document.createTable(5, 3);
        } else {
            table = document.createTable(otherActivities.size() + 2, 3);
        }

        int[] columnWidthsTable= {6000,2600,1500};
        setTableColumnWidths(table, columnWidthsTable);
        setTableBorders(table);
        centerTable(table);
        setRowsHeight06CmPrecise(table);

        //Section name
        XWPFTableRow rowSectionName = table.getRow(0);
        XWPFTableCell cell1 = rowSectionName.getCell(0);
        CTTcPr tcPr1 = getCellCTTcPr(cell1);
        CTShd shd1 = tcPr1.isSetShd() ? tcPr1.getShd() : tcPr1.addNewShd();
        shd1.setVal(STShd.CLEAR);
        shd1.setColor("auto");
        shd1.setFill(RED_COLOR_CODE);
        rowSectionName.setHeight(330);

        this.deleteCells(rowSectionName, 1, 2);

        //Titles column
        XWPFTableRow rowTitles = table.getRow(1);
        XWPFTableCell cell2 = rowTitles.getCell(0);
        CTTcPr tcPr2 = getCellCTTcPr(cell2);
        CTShd shd2 = tcPr2.isSetShd() ? tcPr2.getShd() : tcPr2.addNewShd();
        shd2.setVal(STShd.CLEAR);
        shd2.setColor("auto");
        shd2.setFill(GREY_COLOR_CODE);
        rowTitles.setHeight(380);

        XWPFTableCell cell3 = rowTitles.getCell(1);
        CTTcPr tcPr3 = getCellCTTcPr(cell3);
        CTShd shd3 = tcPr3.isSetShd() ? tcPr3.getShd() : tcPr3.addNewShd();
        shd3.setVal(STShd.CLEAR);
        shd3.setColor("auto");
        shd3.setFill(GREY_COLOR_CODE);

        XWPFTableCell cell4 = rowTitles.getCell(2);
        CTTcPr tcPr4 = getCellCTTcPr(cell4);
        CTShd shd4 = tcPr4.isSetShd() ? tcPr4.getShd() : tcPr4.addNewShd();
        shd4.setVal(STShd.CLEAR);
        shd4.setColor("auto");
        shd4.setFill(GREY_COLOR_CODE);

        addTextToCell(cell1, "4. Otras actividades de investigación (*)", ParagraphAlignment.CENTER, true);
        addTextToCell(cell2, "Nombre", ParagraphAlignment.CENTER, true);
        addTextToCell(cell3, "Tipo de actividad", ParagraphAlignment.CENTER, true);
        addTextToCell(cell4, COMPLIANCE_PERCENTAGE_TEXT, ParagraphAlignment.CENTER, true);

        for (int i = 0; i < otherActivities.size(); i++) {
            XWPFTableRow rowProject = table.getRow(2 + i);
            rowProject.setHeight(330);

            XWPFTableCell cellProjectName = rowProject.getCell(0);
            addTextToCell(cellProjectName, otherActivities.get(i).getName(), ParagraphAlignment.CENTER, false);

            XWPFTableCell cellActivities = rowProject.getCell(1);
            addTextToCell(cellActivities, otherActivities.get(i).getType(), ParagraphAlignment.CENTER, false);

            XWPFTableCell cellCompliancePercentage = rowProject.getCell(2);
            addTextToCell(cellCompliancePercentage, String.valueOf(otherActivities.get(i).getCompliancePercentage()),
                    ParagraphAlignment.CENTER, false);
        }
    }

    private void createProductTable(XWPFDocument document, Map<String, List<ProductDto>> products){

        XWPFTable tableTitle;
        tableTitle = document.createTable(1, 4);
        int[] columnWidthsTable= {2325, 3575, 2600, 1600};
        setTableColumnWidths(tableTitle, columnWidthsTable);
        setTableBorders(tableTitle);
        centerTable(tableTitle);
        setRowsHeight06CmPrecise(tableTitle);

        XWPFTableRow rowSectionName = tableTitle.getRow(0);
        rowSectionName.setHeight(370);

        XWPFTableCell cell1 = rowSectionName.getCell(0);
        CTTcPr tcPr1 = getCellCTTcPr(cell1);
        CTShd shd1 = tcPr1.isSetShd() ? tcPr1.getShd() : tcPr1.addNewShd();
        shd1.setVal(STShd.CLEAR);
        shd1.setColor("auto");
        shd1.setFill(GREY_COLOR_CODE);

        XWPFTableCell cell2 = rowSectionName.getCell(1);
        CTTcPr tcPr2 = getCellCTTcPr(cell2);
        CTShd shd2 = tcPr2.isSetShd() ? tcPr2.getShd() : tcPr2.addNewShd();
        shd2.setVal(STShd.CLEAR);
        shd2.setColor("auto");
        shd2.setFill(GREY_COLOR_CODE);

        XWPFTableCell cell3 = rowSectionName.getCell(2);
        CTTcPr tcPr3 = getCellCTTcPr(cell3);
        CTShd shd3 = tcPr3.isSetShd() ? tcPr3.getShd() : tcPr3.addNewShd();
        shd3.setVal(STShd.CLEAR);
        shd3.setColor("auto");
        shd3.setFill(GREY_COLOR_CODE);

        XWPFTableCell cell4 = rowSectionName.getCell(3);
        CTTcPr tcPr4 = getCellCTTcPr(cell4);
        CTShd shd4 = tcPr4.isSetShd() ? tcPr4.getShd() : tcPr4.addNewShd();
        shd4.setVal(STShd.CLEAR);
        shd4.setColor("auto");
        shd4.setFill(GREY_COLOR_CODE);

        addTextToCell(cell1, "PRODUCTO", ParagraphAlignment.CENTER, true);
        addTextToCell(cell2, "DESCRIPCIÓN", ParagraphAlignment.CENTER, true);
        addTextToCell(cell3, "RESPONSABLE", ParagraphAlignment.CENTER, true);
        addTextToCell(cell4, "FECHA", ParagraphAlignment.CENTER, true);

        XWPFTable tableProducts;
        tableProducts = document.createTable(8, 4);
        int[] tableProductsColumnsWidth = {2325, 3575, 2600, 1600};
        setTableColumnWidths(tableProducts, tableProductsColumnsWidth);
        setTableBorders(tableProducts);
        centerTable(tableProducts);
        setRowsHeight06CmPrecise(tableProducts);

        XWPFTableRow rowProduct1 = tableProducts.getRow(0);
        XWPFTableRow rowProduct2 = tableProducts.getRow(1);
        XWPFTableRow rowProduct3 = tableProducts.getRow(2);
        XWPFTableRow rowProduct4 = tableProducts.getRow(3);
        XWPFTableRow rowProduct5 = tableProducts.getRow(4);
        XWPFTableRow rowProduct6 = tableProducts.getRow(5);
        XWPFTableRow rowProduct7 = tableProducts.getRow(6);
        XWPFTableRow rowProduct8 = tableProducts.getRow(7);

        XWPFTableCell cell5 = rowProduct1.getCell(0);
        CTTcPr tcPr5 = getCellCTTcPr(cell5);
        CTShd shd5 = tcPr5.isSetShd() ? tcPr5.getShd() : tcPr5.addNewShd();
        shd5.setVal(STShd.CLEAR);
        shd5.setColor("auto");
        shd5.setFill(CURUBA_COLOR_CODE);

        XWPFTableCell cell6 = rowProduct2.getCell(0);
        CTTcPr tcPr6 = getCellCTTcPr(cell6);
        CTShd shd6 = tcPr6.isSetShd() ? tcPr6.getShd() : tcPr6.addNewShd();
        shd6.setVal(STShd.CLEAR);
        shd6.setColor("auto");
        shd6.setFill(CURUBA_COLOR_CODE);

        XWPFTableCell cell7 = rowProduct3.getCell(0);
        CTTcPr tcPr7 = getCellCTTcPr(cell7);
        CTShd shd7 = tcPr7.isSetShd() ? tcPr7.getShd() : tcPr7.addNewShd();
        shd7.setVal(STShd.CLEAR);
        shd7.setColor("auto");
        shd7.setFill(CURUBA_COLOR_CODE);

        XWPFTableCell cell8 = rowProduct4.getCell(0);
        CTTcPr tcPr8 = getCellCTTcPr(cell8);
        CTShd shd8 = tcPr8.isSetShd() ? tcPr8.getShd() : tcPr8.addNewShd();
        shd8.setVal(STShd.CLEAR);
        shd8.setColor("auto");
        shd8.setFill(CURUBA_COLOR_CODE);

        XWPFTableCell cell9 = rowProduct5.getCell(0);
        CTTcPr tcPr9 = getCellCTTcPr(cell9);
        CTShd shd9 = tcPr9.isSetShd() ? tcPr9.getShd() : tcPr9.addNewShd();
        shd9.setVal(STShd.CLEAR);
        shd9.setColor("auto");
        shd9.setFill(CURUBA_COLOR_CODE);

        XWPFTableCell cell10 = rowProduct6.getCell(0);
        CTTcPr tcPr10 = getCellCTTcPr(cell10);
        CTShd shd10 = tcPr10.isSetShd() ? tcPr10.getShd() : tcPr10.addNewShd();
        shd10.setVal(STShd.CLEAR);
        shd10.setColor("auto");
        shd10.setFill(CURUBA_COLOR_CODE);

        XWPFTableCell cell11 = rowProduct7.getCell(0);
        CTTcPr tcPr11 = getCellCTTcPr(cell11);
        CTShd shd11 = tcPr11.isSetShd() ? tcPr11.getShd() : tcPr11.addNewShd();
        shd11.setVal(STShd.CLEAR);
        shd11.setColor("auto");
        shd11.setFill(CURUBA_COLOR_CODE);

        XWPFTableCell cell12 = rowProduct8.getCell(0);
        CTTcPr tcPr12 = getCellCTTcPr(cell12);
        CTShd shd12 = tcPr12.isSetShd() ? tcPr12.getShd() : tcPr12.addNewShd();
        shd12.setVal(STShd.CLEAR);
        shd12.setColor("auto");
        shd12.setFill(CURUBA_COLOR_CODE);

        addTextToCellProducts(cell5, "Actualización GrupLAC Actualización CGIS", ParagraphAlignment.LEFT, false);
        addTextToCellProducts(cell6, "Participación convocatoria de reconocimiento Minciencias", ParagraphAlignment.LEFT, false);
        addTextToCellProducts(cell7, "Proyectos terminados y/o ejecución, avalados con financiación interna (FINU) o externa", ParagraphAlignment.LEFT, false);
        addTextToCellProducts(cell8, "Artículo publicado o remitido revista científica", ParagraphAlignment.LEFT, false);

        addTextToCellProducts(cell9, "Participación propuesta investigación en convocatoria interna o externa", ParagraphAlignment.LEFT, false);
        addTextToCellProducts(cell10, "Ponencia evento académico regional nacional o internacional", ParagraphAlignment.LEFT, false);
        addTextToCellProducts(cell11, "Dirección trabajo de grado (post-grado, maestría)", ParagraphAlignment.LEFT, false);
        addTextToCellProducts(cell12, "Otros productos", ParagraphAlignment.LEFT, false);

        rowProduct1.setHeight(rowProduct1.getHeight()*3);
        rowProduct2.setHeight(rowProduct2.getHeight()*3);
        rowProduct3.setHeight(rowProduct3.getHeight()*3);
        rowProduct4.setHeight(rowProduct4.getHeight()*3);
        rowProduct5.setHeight(rowProduct5.getHeight()*3);
        rowProduct6.setHeight(rowProduct6.getHeight()*3);
        rowProduct7.setHeight(rowProduct7.getHeight()*3);
        rowProduct8.setHeight(rowProduct8.getHeight()*3);
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
            shd.setFill(RED_COLOR_CODE);
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

    private void setTableColumnWidths(XWPFTable table, int[] columnWidths) {
        CTTblGrid tblGrid = table.getCTTbl().getTblGrid();
        if (tblGrid == null) {
            tblGrid = table.getCTTbl().addNewTblGrid();
        }

        tblGrid.getGridColList().clear();

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

    private void addTextToCellProducts(XWPFTableCell cell, String text, ParagraphAlignment alignment, boolean bold) {
        try {
            // Limpiar paragrafos existentes en la celda
            while (!cell.getParagraphs().isEmpty()) {
                cell.removeParagraph(0);
            }

            // Crear un nuevo párrafo para el texto
            XWPFParagraph paragraph = cell.addParagraph();
            paragraph.setAlignment(alignment);
            paragraph.setSpacingBeforeLines(50);
            paragraph.setSpacingAfterLines(50);

            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

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


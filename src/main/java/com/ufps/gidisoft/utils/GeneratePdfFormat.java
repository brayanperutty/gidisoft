package com.ufps.gidisoft.utils;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.ufps.gidisoft.responses.format.*;
import com.ufps.gidisoft.services.formats.directions.DirectionService;
import com.ufps.gidisoft.services.formats.events.EventService;
import com.ufps.gidisoft.services.formats.others.OtherActivityService;
import com.ufps.gidisoft.services.formats.products.ProductService;
import com.ufps.gidisoft.services.formats.projects.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratePdfFormat {

    private final ProjectService projectService;
    private final DirectionService directionService;
    private final EventService eventService;
    private final OtherActivityService otherActivityService;
    private final ProductService productService;

    private static final float MARGIN_TOP = 50f;
    private static final float MARGIN_BOTTOM = 40f;
    private static final float MARGIN_LEFT = 36f;
    private static final float MARGIN_RIGHT = 36f;

    private static final float[] COLUMN_PROJECTS = {300f, 200f, 100f};
    private static final float[] COLUMN_DIRECTIONS = {320f, 200f, 100f};
    private static final float[] COLUMN_EVENTS = {330f, 190f, 100f};
    private static final float[] COLUMN_OTHERS = {340f, 180f, 100f};
    private static final float[] COLUMN_PRODUCTS = {200f, 250f, 200f, 100f};

    private static final String GREY_COLOR_CODE = "#D9D9D9";
    private static final String RED_COLOR_CODE = "#C10000";
    private static final String CURUBA_COLOR_CODE = "#FFEAD9";

    private static final String COMPLIANCE = "% de Cumplimiento";

    public byte[] generateInformeAsBytes(FormatDto format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.LETTER);
        document.setMargins(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT);

        addHeaderBlock(document, format);
        addGeneralInfoBlock(document, format);

        addProjectsTable(document, this.projectService.findByFormatId(format.getFormatId()));
        addDirectionsTable(document, this.directionService.findByFormatId(format.getFormatId()));
        addEventsTable(document, this.eventService.findByFormatId(format.getFormatId()));
        addOthersActivitiesTable(document, this.otherActivityService.findByFormatId(format.getFormatId()));

        document.close();
        return baos.toByteArray();
    }

    private void addHeaderBlock(Document document, FormatDto format) throws IOException {

        try {
            Table header = new Table(UnitValue.createPercentArray(new float[]{
                    1f, 1.5f, 2.5f, 1f, 2f, 2f, 2f, 1.5f, 1.5f}))
                    .useAllAvailableWidth()
                    .setBorder(new SolidBorder(1));

            // === Fila 1: Logo + INVESTIGACIÓN + CÓDIGO ===
            // Logo (rowspan=3)
            Cell logoCell = new Cell(4, 2);
            var image = new ClassPathResource("static/img/Logo-vertical.jpg");
            var imageData = ImageDataFactory.create(image.getInputStream().readAllBytes());
            Image logo = new Image(imageData).scaleToFit(70, 70);
            logo.setTextAlignment(TextAlignment.CENTER);
            logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
            logoCell.add(logo);
            logoCell.setTextAlignment(TextAlignment.CENTER);
            logoCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
            logoCell.setBorder(new SolidBorder(1));
            header.addCell(logoCell);


            header.addCell(this.createCellWithBold(2, 5, "INVESTIGACIÓN", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, null));

            header.addCell(this.createCellWithBold(1, 1, "CÓDIGO", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, null));
            header.addCell(this.createCell(1, 1, format.getCode(), TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));

            header.addCell(this.createCellWithBold(1, 1, "VERSIÓN", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, null));
            header.addCell(this.createCell(1, 1, format.getVersion(), TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));

            header.addCell(this.createCellWithBold(2, 5, "INFORME DE GESTIÓN DE GRUPOS DE INVESTIGACIÓN", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(RED_COLOR_CODE)));

            header.addCell(this.createCellWithBold(1, 1, "FECHA", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, null));
            header.addCell(this.createCell(1, 1, format.getDateFormatter(), TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));

            header.addCell(this.createCellWithBold(1, 1, "PÁGINA", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, null));
            header.addCell(this.createCell(1, 1, "1 de 2", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));

            header.addCell(this.createCellWithBold(1, 3, "ELABORÓ", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, null));
            header.addCell(this.createCellWithBold(1, 3, "REVISÓ", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, null));
            header.addCell(this.createCellWithBold(1, 3, "APROBÓ", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, null));

            header.addCell(this.createCell(1, 3, format.getManagerUsers().getCreatedBy(), TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));
            header.addCell(this.createCell(1, 3, format.getManagerUsers().getReviewBy(), TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));
            header.addCell(this.createCell(1, 3, format.getManagerUsers().getApproveBy(), TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));

            document.add(header);
            document.add(new Paragraph("\n"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private void addGeneralInfoBlock(Document document, FormatDto format) {
        try {

            Table generalInfo = new Table(UnitValue.createPercentArray(new float[]{
                    2f, 2f, 2f, 2f, 3f, 1f, 2f, 2f, 2f, 2f, 2f, 2f}))
                    .useAllAvailableWidth()
                    .setBorder(new SolidBorder(1));

            generalInfo.addCell(this.createCellWithBold(2, 5, "GRUPO DE INVESTIGACIÓN", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)).setHeight(40));
            generalInfo.addCell(this.createCell(2, 7, format.getFormat().getGroup().getName(), TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9).setHeight(40));

            generalInfo.addCell(this.createCellWithBold(1, 2, "DIRECTOR", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            generalInfo.addCell(this.createCell(1, 10, format.getFormat().getDirector().getName(), TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));

            generalInfo.addCell(this.createCellWithBold(1, 3, "DEPARTAMENTO", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            generalInfo.addCell(this.createCell(1, 4, format.getFormat().getDepartment(), TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));
            generalInfo.addCell(this.createCellWithBold(1, 2, "FACULTAD", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            generalInfo.addCell(this.createCell(1, 3, format.getFormat().getFaculty().getName(), TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));

            generalInfo.addCell(this.createCellWithBold(1, 4, "SEMESTRE ACADÉMICO", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            generalInfo.addCell(this.createCell(2, 4, format.getFormat().getAcademicPeriod().getPeriod(), TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));
            generalInfo.addCell(this.createCellWithBold(1, 2, "AÑO", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            generalInfo.addCell(this.createCell(2, 2, String.valueOf(format.getFormat().getAcademicPeriod().getYear()), TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));

            document.add(generalInfo);
            document.add(new Paragraph("\n"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private void addProjectsTable(Document document, List<ProjectDto> projects) {

        try {

            Table productsTitle = new Table(UnitValue.createPercentArray(new float[]{
                    2.5f, 2.5f, 1f}))
                    .useAllAvailableWidth();

            productsTitle.addCell(this.createCellWithBold(1, 1, "1. Proyectos de Investigación", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(RED_COLOR_CODE)).setBorder(new SolidBorder(1)));
            productsTitle.addCell(this.createInvisibleCell(1, 1, "", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));
            productsTitle.addCell(this.createInvisibleCell(1, 1, "", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));

            productsTitle.addCell(this.createCellWithBold(1, 1, "Proyecto", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            productsTitle.addCell(this.createCellWithBold(1, 1, "Actividades", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            productsTitle.addCell(this.createCellWithBold(1, 1, COMPLIANCE, TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));

            for (ProjectDto project : projects) {
                productsTitle.addCell(this.createCell(1, 1, project.getName(), TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE, 9));

                productsTitle.addCell(this.createCell(1, 1, project.getActivities(), TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE, 9));

                productsTitle.addCell(this.createCell(1, 1, String.valueOf(project.getCompliancePercentage()),
                        TextAlignment.CENTER, VerticalAlignment.MIDDLE, 9));
            }

            this.fillEmptyRows(projects.size(), productsTitle);

            document.add(productsTitle);
            document.add(new Paragraph("\n"));

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void addDirectionsTable(Document document, List<DirectionDto> directions) {

        try {

            Table directionTitle = new Table(UnitValue.createPercentArray(new float[]{
                    2.7f, 2.3f, 1f}))
                    .useAllAvailableWidth();

            directionTitle.addCell(this.createCellWithBold(1, 1, "2. Participación de Dirección de trabajo de Grado y/o Tesis", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(RED_COLOR_CODE)).setBorder(new SolidBorder(1)));
            directionTitle.addCell(this.createInvisibleCell(1, 1, "", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));
            directionTitle.addCell(this.createInvisibleCell(1, 1, "", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));

            Table directionHeader = new Table(UnitValue.createPercentArray(new float[]{
                    2.7f, 2.3f, 1f}))
                    .useAllAvailableWidth()
                    .setBorder(new SolidBorder(1));

            directionHeader.addCell(this.createCellWithBold(1, 1, "Título del proyecto", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            directionHeader.addCell(this.createCellWithBold(1, 1, "Director", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            directionHeader.addCell(this.createCellWithBold(1, 1, COMPLIANCE, TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));

            for (DirectionDto direction : directions) {
                directionHeader.addCell(this.createCell(1, 1, direction.getName(), TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE, 9));

                directionHeader.addCell(this.createCell(1, 1, direction.getDirectorName(), TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE, 9));

                directionHeader.addCell(this.createCell(1, 1, String.valueOf(direction.getCompliancePercentage()),
                        TextAlignment.CENTER, VerticalAlignment.MIDDLE, 9));
            }

            this.fillEmptyRows(directions.size(), directionHeader);

            document.add(directionTitle);
            document.add(directionHeader);
            document.add(new Paragraph("\n"));

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void addEventsTable(Document document, List<EventDto> events) {

        try {

            Table eventTitle = new Table(UnitValue.createPercentArray(new float[]{
                    3f, 2f, 1f}))
                    .useAllAvailableWidth();

            eventTitle.addCell(this.createCellWithBold(1, 1, "3. Organización de Eventos de Investigación / Científicos", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(RED_COLOR_CODE)).setBorder(new SolidBorder(1)));
            eventTitle.addCell(this.createInvisibleCell(1, 1, "", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));
            eventTitle.addCell(this.createInvisibleCell(1, 1, "", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));

            Table eventHeader = new Table(UnitValue.createPercentArray(new float[]{
                    3f, 2f, 1f}))
                    .useAllAvailableWidth()
                    .setBorder(new SolidBorder(1));

            eventHeader.addCell(this.createCellWithBold(1, 1, "Nombre del evento", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            eventHeader.addCell(this.createCellWithBold(1, 1, "Fecha de realización", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            eventHeader.addCell(this.createCellWithBold(1, 1, COMPLIANCE, TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));

            for (EventDto event : events) {
                eventHeader.addCell(this.createCell(1, 1, event.getName(), TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE, 9));

                eventHeader.addCell(this.createCell(1, 1, event.getCreatedAtFormatted(), TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE, 9));

                eventHeader.addCell(this.createCell(1, 1, String.valueOf(event.getCompliancePercentage()),
                        TextAlignment.CENTER, VerticalAlignment.MIDDLE, 9));
            }

            this.fillEmptyRows(events.size(), eventHeader);

            document.add(eventTitle);
            document.add(eventHeader);
            document.add(new Paragraph("\n"));

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void addOthersActivitiesTable(Document document, List<OtherActivityDto> otherActivities) {

        try {

            Table otherActivitiesTitle = new Table(UnitValue.createPercentArray(new float[]{
                    3.2f, 1.8f, 1f}))
                    .useAllAvailableWidth();

            otherActivitiesTitle.addCell(this.createCellWithBold(1, 1, "4. Otras actividades de investigación (*)", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(RED_COLOR_CODE)).setBorder(new SolidBorder(1)));
            otherActivitiesTitle.addCell(this.createInvisibleCell(1, 1, "", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));
            otherActivitiesTitle.addCell(this.createInvisibleCell(1, 1, "", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9));

            Table otherActivitiesHeader = new Table(UnitValue.createPercentArray(new float[]{
                    3.2f, 1.8f, 1f}))
                    .useAllAvailableWidth()
                    .setBorder(new SolidBorder(1));

            otherActivitiesHeader.addCell(this.createCellWithBold(1, 1, "Nombre", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            otherActivitiesHeader.addCell(this.createCellWithBold(1, 1, "Tipo de actividad", TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));
            otherActivitiesHeader.addCell(this.createCellWithBold(1, 1, COMPLIANCE, TextAlignment.CENTER,
                    VerticalAlignment.MIDDLE, 9, hexColor(GREY_COLOR_CODE)));

            for (OtherActivityDto otherActivity : otherActivities) {
                otherActivitiesHeader.addCell(this.createCell(1, 1, otherActivity.getName(), TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE, 9));

                otherActivitiesHeader.addCell(this.createCell(1, 1, otherActivity.getType(), TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE, 9));

                otherActivitiesHeader.addCell(this.createCell(1, 1, String.valueOf(otherActivity.getCompliancePercentage()),
                        TextAlignment.CENTER, VerticalAlignment.MIDDLE, 9));
            }

            this.fillEmptyRows(otherActivities.size(), otherActivitiesHeader);

            document.add(otherActivitiesTitle);
            document.add(otherActivitiesHeader);
            document.add(new Paragraph("\n"));

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private Cell createCellWithBold(int rows, int cols, String text, TextAlignment alignment, VerticalAlignment verticalAlignment,
                                    int fontSize, DeviceRgb deviceRgb) throws IOException {

        ClassPathResource fontRes = new ClassPathResource("fonts/arial.ttf");
        PdfFont arial = PdfFontFactory.createFont(fontRes.getFile().getAbsolutePath());

        Cell cell = new Cell(rows, cols)
                .add(new Paragraph(text))
                .setTextAlignment(alignment)
                .setBold()
                .setVerticalAlignment(verticalAlignment)
                .setFontSize(fontSize)
                .setBorder(new SolidBorder(1))
                .setFont(arial);

        if (deviceRgb != null) {
            cell.setBackgroundColor(deviceRgb);
        }

        return cell;
    }

    private Cell createCell(int rows, int cols, String text, TextAlignment alignment, VerticalAlignment verticalAlignment,
                            int fontSize) throws IOException {

        ClassPathResource fontRes = new ClassPathResource("fonts/arial.ttf");
        PdfFont arial = PdfFontFactory.createFont(fontRes.getFile().getAbsolutePath());

        return new Cell(rows, cols)
                .add(new Paragraph(text))
                .setTextAlignment(alignment)
                .setVerticalAlignment(verticalAlignment)
                .setFontSize(fontSize)
                .setBorder(new SolidBorder(1))
                .setFont(arial);
    }

    private Cell createEmptyCell(int rows, int cols, String text, TextAlignment alignment, VerticalAlignment verticalAlignment,
                                 int fontSize) {

        return new Cell(rows, cols)
                .add(new Paragraph(text))
                .setTextAlignment(alignment)
                .setVerticalAlignment(verticalAlignment)
                .setFontSize(fontSize)
                .setBorder(new SolidBorder(1));
    }

    private Cell createInvisibleCell(int rows, int cols, String text, TextAlignment alignment, VerticalAlignment verticalAlignment,
                                 int fontSize) {
        return new Cell(rows, cols)
                .add(new Paragraph(text))
                .setTextAlignment(alignment)
                .setVerticalAlignment(verticalAlignment)
                .setFontSize(fontSize)
                .setBorder(Border.NO_BORDER);
    }

    private void fillEmptyRows(int size, Table table) {
        switch (size) {
            case 0: {
                for (int i = 0; i < 3; i++) {
                    table.addCell(this.createEmptyCell(1, 1, "", TextAlignment.CENTER,
                            VerticalAlignment.MIDDLE, 9).setHeight(15));

                    table.addCell(this.createEmptyCell(1, 1, "", TextAlignment.CENTER,
                            VerticalAlignment.MIDDLE, 9).setHeight(15));

                    table.addCell(this.createEmptyCell(1, 1, "", TextAlignment.CENTER,
                            VerticalAlignment.MIDDLE, 9).setHeight(15));
                }
            }
            break;
            case 1: {
                for (int i = 0; i < 2; i++) {
                    table.addCell(this.createEmptyCell(1, 1, "", TextAlignment.CENTER,
                            VerticalAlignment.MIDDLE, 9).setHeight(15));

                    table.addCell(this.createEmptyCell(1, 1, "", TextAlignment.CENTER,
                            VerticalAlignment.MIDDLE, 9).setHeight(15));

                    table.addCell(this.createEmptyCell(1, 1, "", TextAlignment.CENTER,
                            VerticalAlignment.MIDDLE, 9).setHeight(15));
                }
            }
            break;
            case 2: {
                table.addCell(this.createEmptyCell(1, 1, "", TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE, 9).setHeight(15));

                table.addCell(this.createEmptyCell(1, 1, "", TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE, 9).setHeight(15));

                table.addCell(this.createEmptyCell(1, 1, "", TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE, 9).setHeight(15));
            }
            break;
            default:
                break;
        }
    }

    public static DeviceRgb hexColor(String hex) {
        Color color = Color.decode(hex); // Usa java.awt.Color
        return new DeviceRgb(color.getRed(), color.getGreen(), color.getBlue());
    }
}

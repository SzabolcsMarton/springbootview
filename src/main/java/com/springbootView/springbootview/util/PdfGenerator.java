package com.springbootView.springbootview.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.springbootView.springbootview.model.Cart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class PdfGenerator {
    private static Font COURIER = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
    private static Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 16, Font.BOLD);
    private static Font COURIER_SMALL_FOOTER = new Font(Font.FontFamily.COURIER, 10, Font.BOLD);

    private String filePath = "C:/Users/A200133716/Downloads/SpringbootMakiBurger/springbootview/src/main/resources/pdfstore/";
    private String logoPath = "C:/Users/A200133716/Downloads/SpringbootMakiBurger/springbootview/src/main/resources/static/img/logo.png";

    private String reportName = "Order-Report";
    private List<String> columnNames = List.of("Név","Cím", "Dátum", "Vip", "Összeg");
    private Integer columnNumber = columnNames.size();





    public void generatePdfReport(List<Cart> carts) throws URISyntaxException {
        Document document = new Document(PageSize.A4);
        try {
            FileOutputStream stream =  new FileOutputStream(getFileNameWithDate());
            PdfWriter.getInstance(document,stream);
            document.open();
            addLogo(document);
            addDocTitle(document);
            addText(document);
            createANdFillTable(document, carts);
            addFooter(document);
            document.close();
            System.out.println("------------------Your PDF Report is ready!-------------------------");

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addLogo(Document document) {
        try {
            Image img = Image.getInstance(logoPath);
            img.setAlignment(Element.ALIGN_RIGHT);
            document.add(img);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addDocTitle(Document document) throws DocumentException {
        String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy MMMM dd HH:mm:ss"));
        Paragraph p1 = new Paragraph();
        leaveEmptyLine(p1, 1);
        p1.add(new Paragraph(reportName, COURIER));
        p1.setAlignment(Element.ALIGN_CENTER);
        leaveEmptyLine(p1, 1);
        p1.add(new Paragraph("Generated on: " + localDateString, COURIER_SMALL_FOOTER));
        document.add(p1);
    }

    private void addText(Document document) throws DocumentException {
        Paragraph p1 = new Paragraph();
        leaveEmptyLine(p1, 2);
        Chunk chunk = new Chunk("Ez egy teszt riport text része", COURIER_SMALL);
        Paragraph p2 = new Paragraph();
        leaveEmptyLine(p2, 2);
        try {
            document.add(p1);
            document.add(chunk);
            document.add(p2);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private void createANdFillTable(Document document, List<Cart> carts) throws DocumentException {
        Paragraph p1 = new Paragraph();
        leaveEmptyLine(p1, 2);
        Paragraph p2 = new Paragraph();
        leaveEmptyLine(p2, 1);
        document.add(p1);

        PdfPTable table = new PdfPTable(columnNumber);

        for(int i=0; i<columnNumber; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.ORANGE);
            table.addCell(cell);
        }

        table.setHeaderRows(1);
        getDbDataFillTable(table, carts);
        document.add(table);
        document.add(p2);
    }

    private void addFooter(Document document) throws DocumentException {
        Paragraph footerParagraph = new Paragraph();
        footerParagraph.setAlignment(Element.ALIGN_MIDDLE);
        footerParagraph.add(new Paragraph(
                "------------------------End Of " +reportName+"------------------------",
                COURIER_SMALL_FOOTER));
        document.add(footerParagraph);
    }

    private void getDbDataFillTable(PdfPTable table, List<Cart > carts) {

        for (Cart cart : carts) {

            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(cart.getName());
            table.addCell(cart.getAddress());
            String time = cart.getTimeOfOrder().format(DateTimeFormatter.ofPattern("yyyy MMMM dd HH:mm:ss"));
            table.addCell(time);
            table.addCell(cart.isVipUser() ? "Igen" : "Nem");
            table.addCell(String.valueOf(cart.getSumOfAllItemPrices()));
        }
    }

    private String getFileNameWithDate(){
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MMMM-dd-HH-mm-ss"));
        String name = reportName + " " + datePart;
        return filePath + name + ".pdf";

    }

    private static void leaveEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

}

package com.personnel.auction.pdf;

import com.personnel.auction.entity.Property;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PdfThingy {

    private final PropertyService propertyService;

    public PdfThingy(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    public void doYourThing(String filePath) {

        System.out.println("FilePath - " + filePath);

        String[] splits = filePath.split("\\\\");

        String fileName = splits[splits.length - 1];

        System.out.println("FileName - " + fileName);

        PdfDocument pdf = new PdfDocument(filePath);

        PdfTableExtractor extractor = new PdfTableExtractor(pdf);

        List<Property> properties = new ArrayList<>();

        String headerRow = extractor.extractTable(0)[0].getText(0, 0);

        LocalDate auctionDate = getAuctionDate(headerRow);

        for (int pageIndex = 0; pageIndex < pdf.getPages()
                .getCount(); pageIndex++) {
            //Extract tables from the current page into a PdfTable array
            PdfTable[] tableLists = extractor.extractTable(pageIndex);

            //If any tables are found
            if (tableLists != null && tableLists.length > 0) {

                //Loop through the tables in the array
                for (PdfTable table : tableLists) {
                    //Loop through the rows in the current table

                    for (int i = 0; i < table.getRowCount(); i++) {
                        //Loop through the columns in the current table

                        if (isNumber(table.getText(i, 0))) {

                            properties.add(Property.builder()
                                    .serialNumber(table.getText(i, 0))
                                    .estate(table.getText(i, 1))
                                    .plotNumber(table.getText(i, 2))
                                    .categoryAndSize(table.getText(i, 3))
                                    .area(table.getText(i, 4))
                                    .numberOfPlots(table.getText(i, 5))
                                    .far(table.getText(i, 6))
                                    .reservePrice(table.getText(i, 7))
                                    .emd(table.getText(i, 8))
                                    .timestamp(LocalDateTime.now())
                                    .filename(fileName)
                                    .auctionDate(auctionDate)
                                    .priceByArea(number(table.getText(i, 7)) / number(table.getText(i, 4)))
                                    .build());
                        }
                    }
                }
            }
        }

        propertyService.saveAll(properties);
    }

    private LocalDate getAuctionDate(String headerRow) {

        try {
            return LocalDate.parse(headerRow.split("Dated- ")[1], DateTimeFormatter.ofPattern("dd.MMM.yyyy", Locale.US));
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException ex) {
            return getOldAuctionDate(headerRow);
        }
    }

    private LocalDate getOldAuctionDate(String headerRow) {
        return LocalDate.parse(headerRow.toLowerCase(Locale.ROOT)
                .split("dated ")[1], DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.US));
    }

    private boolean isNumber(String str) {

        try {
            Integer.parseInt(str.strip());
            return Boolean.TRUE;
        } catch (NumberFormatException ex) {

        }

        return Boolean.FALSE;
    }

    private int number(String str) {
        try {
            return Integer.parseInt(str.strip());
        } catch (NumberFormatException ex) {

        }

        return 1;
    }
}

package com.personnel.auction.pdf;

import com.personnel.auction.utils.Constants;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Service
public class PdfFacade {

    private final PdfThingy pdfThingy;

    public PdfFacade(PdfThingy pdfThingy) {
        this.pdfThingy = pdfThingy;
    }

    void parse() {

        File file = new File(Constants.DOWNLOAD_PREFIX);

        Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .map(File::getAbsolutePath)
                .forEach(pdfThingy::doYourThing);
    }
}

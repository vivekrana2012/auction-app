package com.personnel.auction.pdf;

import com.personnel.auction.pdf.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/parse")
@RestController
public class PdfController {

    private final PdfFacade pdfFacade;

    public PdfController(PdfFacade pdfFacade) {
        this.pdfFacade = pdfFacade;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDto> parse() {

        pdfFacade.parse();

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status("DONE")
                        .build());
    }
}

package com.personnel.auction.scraper;

import com.personnel.auction.scraper.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/scraper")
@RestController
public class WebScraperController {

    private final WebScraper webScraper;

    public WebScraperController(WebScraper webScraper) {
        this.webScraper = webScraper;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDto> scrape() {

        webScraper.scrape();

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status("DONE")
                        .build());
    }
}

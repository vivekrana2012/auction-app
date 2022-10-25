package com.personnel.auction.scraper;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.personnel.auction.entity.PropertyFile;
import com.personnel.auction.utils.Constants;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WebScraper {

    private final PropertyFileService propertyFileService;

    public WebScraper(PropertyFileService propertyFileService) {
        this.propertyFileService = propertyFileService;
    }

    void scrape() {

        try (final WebClient webClient = new WebClient()) {

            final HtmlPage page = webClient.getPage(Constants.HUDA_URL);

            List<HtmlAnchor> anchors = page.getAnchors()
                    .stream()
                    .filter(anchor -> anchor.getHrefAttribute()
                            .contains(Constants.ANCHOR_PREFIX))
                    .filter(anchor -> anchor.getParentNode()
                            .getPreviousSibling()
                            .getPreviousSibling()
                            .getTextContent()
                            .contains(Constants.CITY_PLACEHOLDER))
                    .toList();

            anchors.forEach(anchor -> {
                try {
                    Page clickedPage = anchor.openLinkInNewWindow();

                    final String pageAsText = clickedPage.getWebResponse()
                            .getContentAsStream()
                            .toString();

                    System.out.println(pageAsText);

                    String[] splits = anchor.getHrefAttribute()
                            .split("/");

                    String fileName = splits[splits.length - 1];

                    File file = new File(Constants.DOWNLOAD_PREFIX + fileName);

                    try (InputStream downloadedContent = clickedPage.getWebResponse()
                            .getContentAsStream(); FileOutputStream outputStream = new FileOutputStream(file)) {

                        int read;
                        byte[] bytes = new byte[8192];
                        while ((read = downloadedContent.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, read);
                        }

                    }

                    propertyFileService.save(PropertyFile.builder()
                            .filename(fileName)
                            .timestamp(LocalDateTime.now())
                            .build());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

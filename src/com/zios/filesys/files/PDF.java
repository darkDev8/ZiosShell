package com.zios.filesys.files;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PDF extends FileSYS {
    private String pages;
    private String lines;
    private String characters;

    public PDF(String path) {
        super(path);

        if (exist()) {
            try {
                File pdfFile = new File(path);
                String [] lines = null;

                try (PDDocument doc = PDDocument.load(pdfFile)) {
                    PDFTextStripper stripper = new PDFTextStripper();
                    String text = stripper.getText(doc);

                    this.characters = String.valueOf(text.length());
                    this.pages = String.valueOf(doc.getDocumentCatalog().getPages().getCount());
                    lines = text.split(stripper.getLineSeparator());
                }

                this.lines = String.valueOf(lines.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getPages() {
        return pages;
    }

    public String getLines() {
        return lines;
    }

    public String getCharacters() {
        return characters;
    }
}

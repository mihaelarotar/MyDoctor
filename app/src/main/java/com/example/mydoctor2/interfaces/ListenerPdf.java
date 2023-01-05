package com.example.mydoctor2.interfaces;

import com.example.mydoctor2.data.PdfModel;

public interface ListenerPdf {
    void onPdfClick(PdfModel pdfModel, int position);
}

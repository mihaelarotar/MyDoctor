package com.example.mydoctor2.interfaces;

import com.example.mydoctor2.data.PdfModel;
import com.example.mydoctor2.other.AdapterPdf;

public interface ListenerPdf {
    void onPdfClick(PdfModel pdfModel, int position);
    void onPdfMoreClick(PdfModel pdfModel, int position, AdapterPdf.HolderPdf holder);
}

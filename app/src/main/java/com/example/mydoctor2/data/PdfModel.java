package com.example.mydoctor2.data;

import android.net.Uri;

import java.io.File;

public class PdfModel {
    File file;
    Uri uri;

    public PdfModel(File file, Uri uri) {
        this.file = file;
        this.uri = uri;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}

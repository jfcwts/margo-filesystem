package com.weets.filesystem.dto;

import java.io.File;

public class FileDto {
    private String filename;
    private String appendedText;

    private String filenameOfConcatenatedFile;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAppendedText() {
        return appendedText;
    }

    public void setAppendedText(String appendedText) {
        this.appendedText = appendedText;
    }

    public String getFilenameOfConcatenatedFile() {
        return filenameOfConcatenatedFile;
    }

    public void setFilenameOfConcatenatedFile(String filenameOfConcatenatedFile) {
        this.filenameOfConcatenatedFile = filenameOfConcatenatedFile;
    }
}
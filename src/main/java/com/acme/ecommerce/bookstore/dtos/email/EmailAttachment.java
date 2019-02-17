package com.acme.ecommerce.bookstore.dtos.email;

import java.io.Serializable;

public class EmailAttachment implements Serializable {

    private static final long serialVersionUID = -5719618610024038882L;
    private String filename;
    private byte[] data;
    private String mimeType;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "filename='" + filename + '\'' +
                ", mimeType='" + mimeType + '\'' +
                '}';
    }
}

package com.collections.genesys.repository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class CustomResponseWrapper extends HttpServletResponseWrapper {

    private final CharArrayWriter charArrayWriter = new CharArrayWriter();

    public CustomResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(charArrayWriter);
    }

    public String getContent() {
        return charArrayWriter.toString();
    }
}
package com.wt.springboot.common;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

/**
 * @author Administrator
 * @date 2019-08-14 上午 10:18
 * PROJECT_NAME sand-demo
 */
public class PdfEditor {

    public static void unlock(InputStream is, OutputStream out) throws IOException, NoSuchFieldException, IllegalAccessException, DocumentException {
        PdfReader pdfReader = new PdfReader(is);
        modifiedAccess(pdfReader);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, out);
        pdfStamper.close();
    }

    public static int readSize(InputStream is) throws IOException, NoSuchFieldException, IllegalAccessException {
        PdfReader pdfReader = new PdfReader(is);
        modifiedAccess(pdfReader);
        return pdfReader.getNumberOfPages();
    }

    private static void modifiedAccess(PdfReader pdfReader) throws NoSuchFieldException, IllegalAccessException {
        Field field = PdfReader.class.getDeclaredField("ownerPasswordUsed");
        field.setAccessible(true);
        field.set(pdfReader, Boolean.TRUE);
    }
}

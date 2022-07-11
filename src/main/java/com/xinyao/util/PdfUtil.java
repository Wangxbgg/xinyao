package com.xinyao.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;


/**
 * @author ZhangFZ
 * @date 2020/10/28 15:46
 **/
@Slf4j
public class PdfUtil {

    private static Configuration configuration;

    public PdfUtil(){
        configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("UTF-8");
    }


    public void createPdf(String content, String filePath) {
        // step 1
        Document document = new Document(PageSize.A4);
        try {
            // step 2
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
//        writer.setEncryption(null,null,PdfWriter.ALLOW_COPY,PdfWriter.STANDARD_ENCRYPTION_128); //设置pdf权限
            // step 3
            document.open();
            // step 4
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(content.getBytes()), null,  Charset.forName("UTF-8"), new AsianFontProvider());
        } catch (Exception e) {
            log.error("createPdf error:", e);
        } finally {
            // step 5
            document.close();
        }
    }

    /**
            * 用于中文显示的Provider
     */
    class AsianFontProvider extends XMLWorkerFontProvider {
        @Override
        public Font getFont(final String fontName, String encoding, float size, final int style) {
            try {
                BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                return new Font(bfChinese, size, style);
            } catch (Exception ignored) {
            }
            return super.getFont(fontName, encoding, size, style);
        }
    }

    /**
     * freemarker渲染html
     */
    public String freeMarkerRender(Map<String, Object> data, String templateName) {
        configuration.setClassForTemplateLoading(this.getClass(), "/template");
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = configuration.getTemplate(templateName, "UTF-8");
            // 合并数据模型与模板
            // 将合并后的数据和模板写入到流中，这里使用的字符流
            template.process(data, out);
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}

package com.xinyao.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;


/**
 * pdf合并
 * @author LWT
 */
public class PdfMerge {

    private final static Logger logger = LoggerFactory.getLogger(PdfMerge.class);

    /**
     * pdf合并
     *
     * @param pages                 需要合并的pdf数组
     *                              File[] pdfList = new File[2];
     *                              pdfList[0] = new File("target/test-outputs/signature/test-signedNaive(1)-复制.pdf");
     *                              pdfList[1] = new File("target/test-outputs/signature/test-signedNaive.pdf");
     * @param fileOutputPathAndName 合并之后的pdf路径和名字
     *                              File mergedPdf = new File("target/test-outputs/signature/MergedDiagram1.pdf");
     */
    public static String merge(File[] pages, String fileOutputPathAndName) {
        logger.info("-----------------------------pdf合并--------------------------------------");
        long start = System.currentTimeMillis();
        Document document = new Document();
        try {
            PdfReader pdfReader = new PdfReader(pages[0].getAbsolutePath());
            document = new Document(pdfReader.getPageSize(1));
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(new File(fileOutputPathAndName)));
            copy.close();
            document.open();
            for (File file : pages) {
                PdfReader reader = new PdfReader(file.getAbsolutePath());
                int num = reader.getNumberOfPages();
                for (int i = 1; i <= num; i++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, i);
                    copy.addPage(page);
                }
                reader.close();
            }
            pdfReader.close();
            long end = System.currentTimeMillis();
            logger.info("------------------------pdf合并完成，用时：" + (end - start) + "ms--------------------------");
        } catch (Exception e) {
            logger.error("----------------------pdf合并失败,异常信息:" + e.getMessage() + "--------------------------");
        } finally {

            document.close();
            logger.info("-------------------------------------流关闭-------------------------------------------");
        }
        return fileOutputPathAndName;
    }

    public static void main(String[] args) {
        File[] pdfList = new File[2];
        pdfList[0] = new File("D:\\upload\\contract\\contract-d51c6a60199a4130aaaca7479f1ccb0f.pdf");
        pdfList[1] = new File("D:\\upload\\loadBill\\loadBill9c819150936f494aa1b0ece4ef354b91.pdf");
        merge(pdfList, "D:\\upload\\loadBill\\new.pdf");
    }
}

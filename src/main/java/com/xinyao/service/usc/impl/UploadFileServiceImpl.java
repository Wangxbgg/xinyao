package com.xinyao.service.usc.impl;

import com.xinyao.service.usc.IUploadFileService;
import com.xinyao.util.JWTUtil;
import com.xinyao.util.R;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author bzy
 * @date 2019/11/19 10:32
 */
@Service
public class UploadFileServiceImpl implements IUploadFileService {

    /**
     * 图片存储路径
     */
    @Value("${file.imageFilePath}")
    private String imageFilePath;
    /**
     * 文档存储路径
     */
    @Value("${file.docFilePath}")
    private String docFilePath;
    /**
     * 文件限制大小
     */
    @Value("${file.maxFileSize}")
    private long maxFileSize;

    private static Logger logger = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    private final static List<String> FILE_TYPE_LIST_IMAGE = Arrays.asList(
            "image/png",
            "image/jpg",
            "image/jpeg",
            "image/bmp");

    @Override
    public R uploadImage(MultipartFile file) throws IOException {
        // 检查图片类型
        String contentType = file.getContentType();
        if (!FILE_TYPE_LIST_IMAGE.contains(contentType)) {
            return R.failed("上传失败，不允许的文件类型");
        }
        int size = (int) file.getSize();
        if (size > maxFileSize / 10) {
            return R.failed("文件过大");
        }
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        String afterName = StringUtils.substringAfterLast(fileName, ".");
        //获取文件前缀
        String prefName = StringUtils.substringBeforeLast(fileName, ".");
        //获取一个时间毫秒值作为文件名
        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + prefName + "." + afterName;
        File filePath = new File(imageFilePath, fileName);

        //判断文件是否已经存在
        if (filePath.exists()) {
            return R.failed("文件已经存在");
        }
        //判断文件父目录是否存在
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        file.transferTo(filePath);
        return R.ok(fileName);
    }

    @Override
    public R uploadFiles(MultipartFile[] files) throws IOException {
        int size = 0;
        for (MultipartFile file : files) {
            size = (int) file.getSize() + size;
        }
        if (size > maxFileSize) {
            return R.failed("文件过大");
        }
        List<Map<String, Object>> fileInfoList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            Map<String, Object> map = new HashMap<>();
            String fileName = files[i].getOriginalFilename();
            //获取文件后缀
            String afterName = StringUtils.substringAfterLast(fileName, ".");
            //获取文件前缀
            String prefName = StringUtils.substringBeforeLast(fileName, ".");

            String fileServiceName = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new Date()) + i + "_" + prefName + "." + afterName;
            File filePath = new File(docFilePath, fileServiceName);
            // 判断文件父目录是否存在
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            files[i].transferTo(filePath);
            map.put("updatedBy", JWTUtil.getUserId());
            map.put("updatedByName", JWTUtil.getUsername());
            map.put("updatedDate", new Date());
            map.put("fileName", fileName);
            map.put("filePath", filePath);
            map.put("fileServiceName", fileServiceName);
            fileInfoList.add(map);
        }
        return R.ok(fileInfoList);
    }

    /**
     * 批量删除文件
     *
     * @param fileNameArr 服务端保存的文件的名数组
     */
    public void deleteFile(String[] fileNameArr) {
        for (String fileName : fileNameArr) {
            String filePath = docFilePath + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                if (file.delete()) {
                    logger.info("文件: {} 删除成功", fileName);
                } else {
                    logger.warn("文件: {} 删除失败", fileName);
                }
            } else {
                logger.warn("文件: {} 删除失败，该文件不存在", fileName);
            }
        }
    }


    @Override
    public void downLoadFile(HttpServletResponse response, String fileName) throws Exception {
        String encodeFileName = URLDecoder.decode(fileName, "UTF-8");
        File file = new File(docFilePath + encodeFileName);
        // 下载文件
        if (!file.exists()) {
            throw new RuntimeException("文件不存在！");
        }
        FileInputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            response.reset();
            //设置响应类型	PDF文件为"application/pdf"，WORD文件为："application/msword"， EXCEL文件为："application/vnd.ms-excel"。
            response.setContentType("application/octet-stream;charset=utf-8");
            //设置响应的文件名称,并转换成中文编码
            String afterName = StringUtils.substringAfterLast(fileName, "_");
            //保存的文件名,必须和页面编码一致,否则乱码
            afterName = response.encodeURL(new String(afterName.getBytes(), "iso8859-1"));
            response.setHeader("Content-type", "application-download");
            //attachment作为附件下载；inline客户端机器有安装匹配程序，则直接打开；注意改变配置，清除缓存，否则可能不能看到效果
            response.addHeader("Content-Disposition", "attachment;filename=" + afterName);
            response.addHeader("filename", afterName);
            //将文件读入响应流
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            int length = 1024;
            int readLength = 0;
            byte[] buf = new byte[1024];
            readLength = inputStream.read(buf, 0, length);
            while (readLength != -1) {
                outputStream.write(buf, 0, readLength);
                readLength = inputStream.read(buf, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert outputStream != null;
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
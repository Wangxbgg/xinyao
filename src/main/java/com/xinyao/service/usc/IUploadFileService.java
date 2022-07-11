package com.xinyao.service.usc;

import com.xinyao.util.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: bzy
 * @Date: 2019/11/19 10:32
 * @Description:
 */

public interface IUploadFileService {

    R uploadImage(MultipartFile file) throws IOException;

    R uploadFiles(MultipartFile[] files) throws IOException;

    void downLoadFile(HttpServletResponse response, String fileName) throws Exception;
}

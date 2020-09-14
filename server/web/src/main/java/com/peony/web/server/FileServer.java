package com.peony.web.server;

import com.peony.common.web.RestErrorCode;
import com.peony.common.web.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * 文件接口
 *
 * @author 杜云山
 * @date 2019/10/29
 */
@Slf4j
@Service
public class FileServer {

    private static final String BASE_DIR = System.getProperty("user.dir") + File.separator + "file";

    public void uploadFile(MultipartFile multipartFile) throws Exception {
        if (multipartFile.isEmpty() || multipartFile.getSize() <= 0) {
            log.info("file is empty!");
            throw new RestException(RestErrorCode.FILE_EMPTY);
        }
        String contentType = multipartFile.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            log.error("上传文件失败");
            throw new RestException(RestErrorCode.FILE_TYPE_ERROR);
        }
        File dir = new File(BASE_DIR);
        if (!dir.exists() && dir.mkdir()) {
            log.info("创建文件夹成功：{}", BASE_DIR);
        }
        String originalFilename = multipartFile.getOriginalFilename();
        log.info("上传文件:name={},type={},fileSize:{}", originalFilename, contentType, multipartFile.getSize());
        File file = new File(BASE_DIR + "/" + originalFilename);
        multipartFile.transferTo(file);
    }

    public ResponseEntity<Resource> downloadFile(String fileName, MediaType mediaType) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "max-age=2592000");
        headers.add("charset", "utf-8");
        headers.add("Content-Type", "image/png");

        String filepath = BASE_DIR + "/" + fileName;
        File file = new File(filepath);

        if (StringUtils.isEmpty(filepath) || !file.exists()) {
            throw new RestException(RestErrorCode.FILE_NOT_EXIST);
        }
        Resource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok().headers(headers).contentType(mediaType).body(resource);
    }

}

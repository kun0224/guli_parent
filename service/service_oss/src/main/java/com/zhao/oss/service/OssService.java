package com.zhao.oss.service;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;


public interface OssService {
    String uploadFileOss(MultipartFile file);
}

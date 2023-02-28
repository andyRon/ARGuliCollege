package com.andyron.vod.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface VodService {
    String uploadAlyiVideo(MultipartFile file);
}

package com.andyron.vod.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface VodService {
    String uploadAlyiVideo(MultipartFile file);

    void removeMoreAliVideo(List videoIdList);
}

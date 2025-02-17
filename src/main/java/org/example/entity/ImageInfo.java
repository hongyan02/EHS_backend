package org.example.entity;

import lombok.Data;
import java.util.Date;

@Data
public class ImageInfo {
    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;
    private Date uploadTime;
    private String description;
} 
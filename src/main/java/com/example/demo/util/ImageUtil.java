package com.example.demo.util;
import com.example.demo.service.*;
import org.apache.commons.io.*;


import org.springframework.http.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ImageUtil {
  private static final String PROFILE_FOLDER = System.getProperty("user.dir") + File.separator + "image" + File.separator + "profile" + File.separator;
  private static final String PRODUCT_FOLDER = System.getProperty("user.dir") + File.separator + "image" + File.separator + "product" + File.separator;
  private static final String DEFAULT_PROFILE = "default.jpg";

  public static String saveProfile(MultipartFile profile, String username) {
    if(profile==null || profile.isEmpty())
      return DEFAULT_PROFILE;
    String ext = FilenameUtils.getExtension(profile.getOriginalFilename());
    String profileName = username + "." + ext;
    System.out.println("프로필 사진 :" + profileName);
    System.out.println(profile.getOriginalFilename());
    try {
      File target = new File(PROFILE_FOLDER, profileName);
      profile.transferTo(target);
      return profileName;
    } catch(IOException e) {
      e.printStackTrace();
    }
    return DEFAULT_PROFILE;
  }

  public static List<String> savaProductImage(List<MultipartFile> images) {
    if(images==null || images.isEmpty())
      return null;
    List<String> imageNames = new ArrayList<>();
    for(MultipartFile image:images) {
      String ext = FilenameUtils.getExtension(image.getOriginalFilename());
      String savedName = UUID.randomUUID().toString() + "." + ext;
      try {
        File target = new File(PRODUCT_FOLDER, savedName);
        image.transferTo(target);
        imageNames.add(savedName);
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
    return imageNames;
  }

  public static ResponseEntity<byte[]> getFile(String imageName) {
    String folder = imageName.length()<20? PROFILE_FOLDER : PRODUCT_FOLDER;
    File file = new File(folder, imageName);
    try {
      byte[] bytes = Files.readAllBytes(file.toPath());
      String contentType = Files.probeContentType(file.toPath());
      MediaType type = MediaType.parseMediaType(contentType);
      return ResponseEntity.ok().contentType(type).body(bytes);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ResponseEntity.status(500).body(null);
  }

  public static void deleteProfile(String profile) {
    File imageFile = new File(PROFILE_FOLDER, profile);
    imageFile.delete();
  }

  public static void deleteProductImages(List<String> imageNames) {
    if(imageNames==null || imageNames.isEmpty())
      return;
    for(String image:imageNames) {
      File imageFile = new File(PRODUCT_FOLDER, image);
      imageFile.delete();
    }
  }
}

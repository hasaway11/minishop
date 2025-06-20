package com.example.demo.util;

import org.springframework.http.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.util.*;

public class FunctionUtil {
  private static final String PROFLILE_FOLDER = System.getProperty("user.dir") + File.separator + "image" + File.separator;
  private static final String PROFILE_NAME = "default.jpg";
  private static String cachedBase64Profile;

  // 서버 시작 시 초기화
  public static void initDefaultProfile() {
    try {
      File file = new File(PROFLILE_FOLDER, PROFILE_NAME);
      try (FileInputStream fis = new FileInputStream(file)) {
        byte[] fileBytes = fis.readAllBytes();
        cachedBase64Profile = "data:" + MediaType.IMAGE_JPEG_VALUE + ";base64," + Base64.getEncoder().encodeToString(fileBytes);
      }
    } catch (IOException e) {
      System.err.println("[ERROR] 기본 프로필 이미지 로딩 실패");
      e.printStackTrace();
      cachedBase64Profile = null;
    }
  }

  public static String getDefaultBase64Profile() {
    return cachedBase64Profile;
  }

  public static Optional<String> getProfile(MultipartFile file, boolean isUpdate) {
    try {
      byte[] fileBytes = file.getBytes();
      String result = "data:" + file.getContentType() + ";base64," + Base64.getEncoder().encodeToString(fileBytes);
      return Optional.ofNullable(result);
    } catch(IOException e) {
      e.printStackTrace();
    }
    if(!isUpdate)
      return Optional.ofNullable(cachedBase64Profile);
    return Optional.ofNullable(null);
  }
}

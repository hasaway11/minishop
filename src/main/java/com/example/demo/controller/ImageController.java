package com.example.demo.controller;

import com.example.demo.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class ImageController {
  @GetMapping("/images/{imageName}")
  public ResponseEntity<byte[]> viewImage(@PathVariable String imageName) {
    return ImageUtil.getFile(imageName);
  }
}

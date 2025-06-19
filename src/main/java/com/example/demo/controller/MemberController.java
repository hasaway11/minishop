package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
public class MemberController {
  public ResponseEntity<Member> signup(MemberDto.Signup dto, BindingResult br) {

  }
}

package com.example.feignclient.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.feignclient.model.GenericAPIResponse;
import com.example.feignclient.service.BusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/sample", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SampleController {

  private final BusinessService businessService;

  @GetMapping
  public ResponseEntity<GenericAPIResponse> get() {
    return ResponseEntity.ok(businessService.getData());
  }
}

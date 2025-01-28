package com.example.feignclient.remote.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingRequestInterceptor implements RequestInterceptor {
  @Override
  public void apply(RequestTemplate template) {
    log.info(
        "Request METHOD: {} URL: {} BODY: {}", template.method(), template.url(), template.body());
  }
}

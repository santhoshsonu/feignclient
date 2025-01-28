package com.example.feignclient.remote.interceptor;

import feign.InvocationContext;
import feign.Request;
import feign.Response;
import feign.ResponseInterceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingResponseInterceptor implements ResponseInterceptor {
  @Override
  public Object intercept(InvocationContext invocationContext, Chain chain) throws Exception {
    try (Response response = invocationContext.response()) {
      Request request = response.request();
      log.info(
          "Request:: {}:{} Response:: Code: {} Body{}",
          request.httpMethod(),
          request.url(),
          response.status(),
          response.body());
    }
    return chain.next(invocationContext);
  }
}

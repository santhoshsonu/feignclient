package com.example.feignclient.remote.config;

import com.example.feignclient.remote.interceptor.LoggingRequestInterceptor;
import com.example.feignclient.remote.interceptor.LoggingResponseInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomFeignConfig {
  @Bean
  public LoggingRequestInterceptor loggingRequestInterceptor() {
    return new LoggingRequestInterceptor();
  }

  @Bean
  public LoggingResponseInterceptor loggingResponseInterceptor() {
    return new LoggingResponseInterceptor();
  }

  //  @Bean
  //  public Retryer retryer() {
  //    return new Retryer.Default(initialInterval, maxInterval, maxAttempts);
  //  }

  //
  //  @Bean
  //  public Retryer exponentialBackOffRetryer() {
  //    return new ExponentialBackOffRetryer(initialInterval, multiplier, maxAttempts, maxInterval);
  //  }

  //  @Bean
  //  public FeignMetricsCapability feignMetricsCapability() {
  //    return new FeignMetricsCapability(meterRegistry, observationRegistry);
  //  }
}

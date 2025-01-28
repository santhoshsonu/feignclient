package com.example.feignclient.remote;

import com.example.feignclient.remote.config.CustomFeignConfig;
import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "sampleApi",
    configuration = {CustomFeignConfig.class})
public interface SampleAPIProvider {

  @GetMapping("/")
  @Retry(name = "sampleApiGet", fallbackMethod = "getFallbackRetry")
  @CircuitBreaker(name = "sampleApiGet", fallbackMethod = "getFallbackCircuitBreaker")
  String get();

  default String getFallbackRetry(FeignException t) {
    if (t instanceof RetryableException re) {
      throw new RetryException("retry fallback response");
    }
    throw t;
  }

  default String getFallbackCircuitBreaker(CallNotPermittedException t) {
    throw new RetryException("circuit breaker fallback response");
  }
}

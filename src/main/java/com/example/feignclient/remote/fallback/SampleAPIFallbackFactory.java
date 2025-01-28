package com.example.feignclient.remote.fallback;

import com.example.feignclient.remote.SampleAPIProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SampleAPIFallbackFactory implements FallbackFactory<SampleAPIProvider> {
  @Override
  public SampleAPIProvider create(Throwable cause) {
    log.info("SampleAPIProvider fallback reason: {}", cause.getMessage());
    return new SampleAPIFallback();
  }
}

package com.example.feignclient.service;

import com.example.feignclient.model.GenericAPIResponse;
import com.example.feignclient.remote.RetryException;
import com.example.feignclient.remote.SampleAPIProvider;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessService {
  private final SampleAPIProvider sampleAPIProvider;
  private final AtomicInteger counter = new AtomicInteger(0);

  public GenericAPIResponse getData() {
    int count = counter.incrementAndGet();
    try {
      log.info("Call #{}", count);
      String response = sampleAPIProvider.get();
      return new GenericAPIResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), response, null);
    } catch (RetryException e) {
      return new GenericAPIResponse(
          HttpStatus.SERVICE_UNAVAILABLE.value(), "Please retry after some time", null, null);
    } catch (Exception ex) {
      log.error("Call #{}: Error: {}", count, ex.getMessage(), ex);
      return new GenericAPIResponse(
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          "Error while calling external service",
          null,
          null);
    }
  }
}

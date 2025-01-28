package com.example.feignclient.remote.config;

import feign.RetryableException;
import feign.Retryer;
import java.time.Instant;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.ExponentialBackOff;

@Slf4j
public class ExponentialBackOffRetryer implements Retryer {
  @Getter private int attempt = 0;
  private BackOffExecution execution;
  private final ExponentialBackOff backOff;

  public ExponentialBackOffRetryer(
      long initialInterval, double multiplier, int maxAttempts, long maxInterval) {
    backOff = new ExponentialBackOff(initialInterval, multiplier);
    backOff.setMaxAttempts(maxAttempts);
    backOff.setMaxInterval(maxInterval);
  }

  @Override
  public void continueOrPropagate(RetryableException e) {
    if (execution == null) {
      if (e.retryAfter() != null) {
        if (Instant.now().toEpochMilli() + backOff.getMaxInterval() < e.retryAfter()) {
          log.info(
              "Ignoring exponential backoff as retryAfter: {} is larger than maxInterval: {}",
              e.retryAfter(),
              backOff.getMaxInterval());
          throw e;
        }
      }
      log.info("Starting exponential backoff");
      execution = backOff.start();
    }

    long interval = execution.nextBackOff();
    if (interval == BackOffExecution.STOP) {
      reset();
      throw e;
    }

    try {
      Thread.sleep(interval);
      attempt++;
    } catch (InterruptedException ignored) {
      reset();
      Thread.currentThread().interrupt();
      throw e;
    }
  }

  @Override
  public Retryer clone() {
    return new ExponentialBackOffRetryer(
        this.backOff.getInitialInterval(),
        this.backOff.getMultiplier(),
        this.backOff.getMaxAttempts(),
        this.backOff.getMaxInterval());
  }

  private void reset() {
    log.info("Stopping exponential backoff after {} attempts", attempt);
    execution = null;
    attempt = 0;
  }
}

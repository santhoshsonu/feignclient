package com.example.feignclient.remote.config;

import feign.*;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import java.lang.reflect.InvocationHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignMetricsCapability implements Capability {
  private final MeterRegistry meterRegistry;
  private final ObservationRegistry observationRegistry;

  @Override
  public InvocationHandlerFactory enrich(InvocationHandlerFactory invocationHandlerFactory) {
    return (target, dispatch) -> {
      InvocationHandler handler = invocationHandlerFactory.create(target, dispatch);
      return (proxy, method, args) -> {
        String clientName = target.name();
        String endpoint = method.getName();

        // Create a new ObservationContext for the method invocation
        Observation observation =
            Observation.createNotStarted("feign.client.request", observationRegistry)
                .contextualName(clientName + " - " + endpoint);

        // Track execution time and success/failure
        Timer.Sample timer = Timer.start(meterRegistry);
        try {
          // Execute the method
          Object result = observation.observeChecked(() -> handler.invoke(proxy, method, args));

          // Record success
          meterRegistry
              .counter("feign.requests.success", "client", clientName, "endpoint", endpoint)
              .increment();
          timer.stop(
              meterRegistry.timer(
                  "feign.requests.duration", "client", clientName, "endpoint", endpoint));

          return result;
        } catch (Exception e) {
          // Record failure
          meterRegistry
              .counter("feign.requests.failure", "client", clientName, "endpoint", endpoint)
              .increment();
          timer.stop(
              meterRegistry.timer(
                  "feign.requests.duration", "client", clientName, "endpoint", endpoint));

          throw e;
        }
      };
    };
  }
}

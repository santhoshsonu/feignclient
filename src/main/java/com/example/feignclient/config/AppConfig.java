package com.example.feignclient.config;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  //  @Bean
  //  OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url) {
  //    return OtlpHttpSpanExporter.builder()
  //        .setEndpoint(url)
  //        .setConnectTimeout(5000, TimeUnit.SECONDS)
  //        .setTimeout(5000, TimeUnit.SECONDS) // Export within 5 seconds
  //        .setCompression("gzip")
  //        .build();
  //  }

  @Bean
  OtlpGrpcSpanExporter otlpGrpcSpanExporter(@Value("${tracing.url}") String url) {
    return OtlpGrpcSpanExporter.builder()
        .setEndpoint(url)
        .setConnectTimeout(5000, TimeUnit.SECONDS)
        .setTimeout(5000, TimeUnit.SECONDS) // Export within 5 seconds
        .setCompression("gzip")
        .build();
  }
}

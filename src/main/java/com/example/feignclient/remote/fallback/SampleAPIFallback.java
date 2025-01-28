package com.example.feignclient.remote.fallback;

import com.example.feignclient.remote.SampleAPIProvider;

public class SampleAPIFallback implements SampleAPIProvider {
  @Override
  public String get() {
    return "fallback response";
  }
}

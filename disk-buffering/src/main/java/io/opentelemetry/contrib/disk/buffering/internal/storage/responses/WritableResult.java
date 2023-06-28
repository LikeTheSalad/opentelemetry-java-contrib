package io.opentelemetry.contrib.disk.buffering.internal.storage.responses;

public enum WritableResult {
  SUCCEEDED,
  CLOSED,
  FILE_EXPIRED,
  FILE_IS_FULL
}

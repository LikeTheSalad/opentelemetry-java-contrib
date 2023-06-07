package io.opentelemetry.contrib.disk.buffering.internal.storage;

public class Configuration {
  public final long maxFileAgeForWriteInMillis;
  public final long minFileAgeForReadInMillis;
  public final long maxFileAgeForReadInMillis;
  public final int maxFileSize;

  public Configuration(
      long maxFileAgeForWriteInMillis,
      long minFileAgeForReadInMillis,
      long maxFileAgeForReadInMillis,
      int maxFileSize) {
    this.maxFileAgeForWriteInMillis = maxFileAgeForWriteInMillis;
    this.minFileAgeForReadInMillis = minFileAgeForReadInMillis;
    this.maxFileAgeForReadInMillis = maxFileAgeForReadInMillis;
    this.maxFileSize = maxFileSize;
  }
}

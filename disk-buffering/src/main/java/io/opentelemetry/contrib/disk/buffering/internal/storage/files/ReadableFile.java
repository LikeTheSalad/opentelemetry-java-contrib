package io.opentelemetry.contrib.disk.buffering.internal.storage.files;

import static io.opentelemetry.contrib.disk.buffering.internal.storage.files.utils.Constants.NEW_LINE_BYTES_SIZE;

import io.opentelemetry.contrib.disk.buffering.internal.storage.Configuration;
import io.opentelemetry.contrib.disk.buffering.internal.storage.exceptions.NoMoreLinesToReadException;
import io.opentelemetry.contrib.disk.buffering.internal.storage.exceptions.ReadingTimeoutException;
import io.opentelemetry.contrib.disk.buffering.internal.storage.files.utils.TemporaryFileProvider;
import io.opentelemetry.contrib.disk.buffering.internal.storage.utils.TimeProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.function.Function;

public final class ReadableFile extends StorageFile {
  private final int originalFileSize;
  private final BufferedReader bufferedReader;
  private final FileChannel tempInChannel;
  private final File temporaryFile;
  private final TimeProvider timeProvider;
  private final long expireTimeMillis;
  private int readBytes = 0;

  public ReadableFile(
      File file, long createdTimeMillis, TimeProvider timeProvider, Configuration configuration)
      throws IOException {
    this(file, createdTimeMillis, timeProvider, configuration, TemporaryFileProvider.INSTANCE);
  }

  public ReadableFile(
      File file,
      long createdTimeMillis,
      TimeProvider timeProvider,
      Configuration configuration,
      TemporaryFileProvider temporaryFileProvider)
      throws IOException {
    super(file);
    this.timeProvider = timeProvider;
    expireTimeMillis = createdTimeMillis + configuration.maxFileAgeForReadInMillis;
    originalFileSize = (int) file.length();
    temporaryFile = temporaryFileProvider.createTemporaryFile();
    Files.copy(file.toPath(), temporaryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    FileInputStream tempInputStream = new FileInputStream(temporaryFile);
    tempInChannel = tempInputStream.getChannel();
    bufferedReader =
        new BufferedReader(new InputStreamReader(tempInputStream, StandardCharsets.UTF_8));
  }

  public synchronized void readLine(Function<byte[], Boolean> consumer) throws IOException {
    if (hasExpired()) {
      throw new ReadingTimeoutException();
    }
    String lineString = bufferedReader.readLine();
    if (lineString == null) {
      throw new NoMoreLinesToReadException();
    }
    byte[] line = lineString.getBytes(StandardCharsets.UTF_8);
    if (consumer.apply(line)) {
      readBytes += line.length + NEW_LINE_BYTES_SIZE;
      try (FileOutputStream out = new FileOutputStream(file, false)) {
        tempInChannel.transferTo(readBytes, originalFileSize - readBytes, out.getChannel());
      }
    }
  }

  @Override
  public long getSize() {
    return originalFileSize;
  }

  @Override
  public boolean hasExpired() {
    return timeProvider.getSystemCurrentTimeMillis() >= expireTimeMillis;
  }

  @Override
  public synchronized void close() throws IOException {
    bufferedReader.close();
    temporaryFile.delete();
  }
}

package io.opentelemetry.contrib.disk.buffer.internal.mapping.logs;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.logs.Severity;
import io.opentelemetry.contrib.disk.buffer.internal.mapping.common.BaseResourceSignalsDataMapper;
import io.opentelemetry.contrib.disk.buffer.internal.mapping.logs.models.LogRecordDataImpl;
import io.opentelemetry.contrib.disk.buffer.internal.serialization.logs.ResourceLogsData;
import io.opentelemetry.contrib.disk.buffer.testutils.BaseSignalDataMapperTest;
import io.opentelemetry.contrib.disk.buffer.testutils.TestData;
import io.opentelemetry.sdk.logs.data.Body;
import io.opentelemetry.sdk.logs.data.LogRecordData;
import org.junit.jupiter.api.Test;

class ResourceLogsDataMapperTest extends BaseSignalDataMapperTest<LogRecordData, ResourceLogsData> {

  private static final LogRecordData LOG_RECORD =
      LogRecordDataImpl.builder()
          .setResource(TestData.RESOURCE_FULL)
          .setSpanContext(TestData.SPAN_CONTEXT)
          .setInstrumentationScopeInfo(TestData.INSTRUMENTATION_SCOPE_INFO_FULL)
          .setAttributes(TestData.ATTRIBUTES)
          .setBody(Body.string("Log body"))
          .setSeverity(Severity.DEBUG)
          .setSeverityText("Log severity text")
          .setTimestampEpochNanos(100L)
          .setObservedTimestampEpochNanos(200L)
          .setTotalAttributeCount(3)
          .build();

  private static final LogRecordData LOG_RECORD_WITHOUT_SEVERITY_TEXT =
      LogRecordDataImpl.builder()
          .setResource(TestData.RESOURCE_FULL)
          .setSpanContext(TestData.SPAN_CONTEXT)
          .setInstrumentationScopeInfo(TestData.INSTRUMENTATION_SCOPE_INFO_FULL)
          .setAttributes(Attributes.empty())
          .setBody(Body.string("Log body"))
          .setSeverity(Severity.DEBUG)
          .setTimestampEpochNanos(100L)
          .setObservedTimestampEpochNanos(200L)
          .setTotalAttributeCount(3)
          .build();

  @Test
  public void verifyMapping() {
    assertItemsMapping(LOG_RECORD, LOG_RECORD_WITHOUT_SEVERITY_TEXT);
  }

  @Override
  protected BaseResourceSignalsDataMapper<LogRecordData, ?, ?, ?, ResourceLogsData> getMapper() {
    return ResourceLogsDataMapper.INSTANCE;
  }
}

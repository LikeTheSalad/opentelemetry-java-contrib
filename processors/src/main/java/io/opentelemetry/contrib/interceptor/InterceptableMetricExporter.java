package io.opentelemetry.contrib.interceptor;

import io.opentelemetry.contrib.interceptor.common.Interceptable;
import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.metrics.InstrumentType;
import io.opentelemetry.sdk.metrics.data.AggregationTemporality;
import io.opentelemetry.sdk.metrics.data.MetricData;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import java.util.Collection;

public class InterceptableMetricExporter extends Interceptable<MetricData>
    implements MetricExporter {
  private final MetricExporter delegate;

  public static InterceptableMetricExporter create(MetricExporter delegate) {
    return new InterceptableMetricExporter(delegate);
  }

  private InterceptableMetricExporter(MetricExporter delegate) {
    this.delegate = delegate;
  }

  @Override
  public CompletableResultCode export(Collection<MetricData> metrics) {
    return delegate.export(interceptAll(metrics));
  }

  @Override
  public CompletableResultCode flush() {
    return delegate.flush();
  }

  @Override
  public CompletableResultCode shutdown() {
    return delegate.shutdown();
  }

  @Override
  public AggregationTemporality getAggregationTemporality(InstrumentType instrumentType) {
    return delegate.getAggregationTemporality(instrumentType);
  }
}

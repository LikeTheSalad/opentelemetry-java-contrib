package io.opentelemetry.contrib.disk.buffer.internal.serialization.mapping.metrics.models.datapoints;

import com.google.auto.value.AutoValue;
import io.opentelemetry.sdk.metrics.data.DoubleExemplarData;
import io.opentelemetry.sdk.metrics.data.ExponentialHistogramBuckets;
import io.opentelemetry.sdk.metrics.data.ExponentialHistogramPointData;
import java.util.List;

@AutoValue
public abstract class ExponentialHistogramPointDataImpl implements ExponentialHistogramPointData {

  public static Builder builder() {
    return new AutoValue_ExponentialHistogramPointDataImpl.Builder();
  }

  @Override
  public boolean hasMin() {
    return getMin() > -1;
  }

  @Override
  public boolean hasMax() {
    return getMax() > -1;
  }

  @AutoValue.Builder
  public abstract static class Builder implements PointDataBuilder<Builder> {
    public abstract Builder setScale(Integer value);

    public abstract Builder setSum(Double value);

    public abstract Builder setCount(Long value);

    public abstract Builder setZeroCount(Long value);

    public abstract Builder setMin(Double value);

    public abstract Builder setMax(Double value);

    public abstract Builder setExemplars(List<DoubleExemplarData> value);

    public abstract Builder setPositiveBuckets(ExponentialHistogramBuckets value);

    public abstract Builder setNegativeBuckets(ExponentialHistogramBuckets value);

    public abstract ExponentialHistogramPointDataImpl build();
  }
}

package io.opentelemetry.contrib.exporters.storage.serialization.metrics;

import com.dslplatform.json.JsonAttribute;
import io.opentelemetry.contrib.exporters.storage.serialization.metrics.data.DataJson;
import javax.annotation.Nullable;

public abstract class MetricDataJson {
  public static final String NAME = "name";
  public static final String DESCRIPTION = "description";
  public static final String UNIT = "unit";

  @Nullable
  @JsonAttribute(name = NAME)
  public String name;

  @Nullable
  @JsonAttribute(name = DESCRIPTION)
  public String description;

  @Nullable
  @JsonAttribute(name = UNIT)
  public String unit;

  public abstract void setData(DataJson<?> data);

  @Nullable
  public abstract DataJson<?> getData();
}

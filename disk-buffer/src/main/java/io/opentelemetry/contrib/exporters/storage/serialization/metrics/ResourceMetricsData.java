package io.opentelemetry.contrib.exporters.storage.serialization.metrics;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import io.opentelemetry.contrib.exporters.storage.serialization.common.ResourceSignalsData;
import java.util.Collection;

@CompiledJson(objectFormatPolicy = CompiledJson.ObjectFormatPolicy.EXPLICIT)
public final class ResourceMetricsData implements ResourceSignalsData<ResourceMetrics> {

  @JsonAttribute(name = "resourceMetrics")
  public final Collection<ResourceMetrics> resourceMetrics;

  public ResourceMetricsData(Collection<ResourceMetrics> resourceMetrics) {
    this.resourceMetrics = resourceMetrics;
  }

  @Override
  public Collection<ResourceMetrics> getResourceSignals() {
    return resourceMetrics;
  }
}

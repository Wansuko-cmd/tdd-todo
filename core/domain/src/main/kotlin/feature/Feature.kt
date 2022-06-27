package feature

import task.FeatureId
import java.util.*

data class Feature(
    val id: FeatureId = FeatureId(UUID.randomUUID().toString()),
    val projectId: ProjectId,
    val title: FeatureTitle,
    val description: FeatureDescription,
)

@JvmInline
value class FeatureTitle(val value: String)

@JvmInline
value class FeatureDescription(val value: String)

@JvmInline
value class ProjectId(val value: String)

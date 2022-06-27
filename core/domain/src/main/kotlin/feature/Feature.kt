package feature

import java.util.*

data class Feature(
    val id: FeatureId = FeatureId(UUID.randomUUID().toString()),
    val projectId: ProjectId,
    val title: FeatureTitle,
    val description: FeatureDescription,
    val phase: FeaturePhase,
) {
    fun copyWithPhase(newPhase: FeaturePhase): Feature = copy(phase = newPhase)
}

@JvmInline
value class FeatureId(val value: String)

@JvmInline
value class FeatureTitle(val value: String)

@JvmInline
value class FeatureDescription(val value: String)

@JvmInline
value class ProjectId(val value: String)

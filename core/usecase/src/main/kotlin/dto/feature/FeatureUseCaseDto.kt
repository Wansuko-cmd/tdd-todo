package dto.feature

import feature.Feature
import feature.FeatureDescription
import feature.FeatureId
import feature.FeaturePhase
import feature.FeatureTitle
import task.TaskId

data class FeatureUseCaseDto(
    val id: FeatureId,
    val title: FeatureTitle,
    val description: FeatureDescription,
    val phase: FeaturePhase,
    val tasks: List<TaskId>
) {
    companion object {
        internal fun Feature.toUseCaseDto() = FeatureUseCaseDto(
            id = id,
            title = title,
            description = description,
            phase = phase,
            tasks = tasks,
        )
    }
}

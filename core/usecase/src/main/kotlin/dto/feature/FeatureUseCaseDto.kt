package dto.feature

import feature.Feature
import feature.FeaturePhase
import task.TaskId

data class FeatureUseCaseDto(
    val id: String,
    val title: String,
    val description: String,
    val phase: FeaturePhase,
    val tasks: List<TaskId>
) {
    companion object {
        fun Feature.toUseCaseDto() = FeatureUseCaseDto(
            id = id.value,
            title = title.value,
            description = description.value,
            phase = phase,
            tasks = tasks,
        )
    }
}

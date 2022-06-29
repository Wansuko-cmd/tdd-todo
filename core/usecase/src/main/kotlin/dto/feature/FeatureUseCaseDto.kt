package dto.feature

import dto.feature.FeaturePhaseUseCaseDto.Companion.toUseCaseDto
import feature.Feature
import feature.FeaturePhase

data class FeatureUseCaseDto(
    val id: String,
    val title: String,
    val description: String,
    val phase: FeaturePhaseUseCaseDto,
) {
    companion object {
        fun Feature.toUseCaseDto() = FeatureUseCaseDto(
            id = id.value,
            title = title.value,
            description = description.value,
            phase = phase.toUseCaseDto(),
        )
    }
}

enum class FeaturePhaseUseCaseDto {
    Todo,
    Done;

    companion object {
        fun FeaturePhase.toUseCaseDto() = when(this) {
            FeaturePhase.Todo -> Todo
            FeaturePhase.Done -> Done
        }
        fun FeaturePhaseUseCaseDto.toDomain() = when(this) {
            Todo -> FeaturePhase.Todo
            Done -> FeaturePhase.Done
        }
    }
}

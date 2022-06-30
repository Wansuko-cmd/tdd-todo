package dto.feature

import feature.Feature
import feature.FeaturePhase

data class FeatureUseCaseDto(
    val id: String,
    val title: String,
    val description: String,
    val phase: FeaturePhase,
) {
    companion object {
        fun Feature.toUseCaseDto() = FeatureUseCaseDto(
            id = id.value,
            title = title.value,
            description = description.value,
            phase = phase,
        )
    }
}

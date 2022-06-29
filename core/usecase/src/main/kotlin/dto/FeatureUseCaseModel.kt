package dto

import dto.FeaturePhaseUseCaseModel.Companion.toUseCaseModel
import feature.Feature
import feature.FeaturePhase

data class FeatureUseCaseModel(
    val id: String,
    val title: String,
    val description: String,
    val phase: FeaturePhaseUseCaseModel,
) {
    companion object {
        fun Feature.toUseCaseModel() = FeatureUseCaseModel(
            id = id.value,
            title = title.value,
            description = description.value,
            phase = phase.toUseCaseModel(),
        )
    }
}

enum class FeaturePhaseUseCaseModel {
    Todo,
    Done;

    companion object {
        fun FeaturePhase.toUseCaseModel() = when(this) {
            FeaturePhase.Todo -> Todo
            FeaturePhase.Done -> Done
        }
    }
}

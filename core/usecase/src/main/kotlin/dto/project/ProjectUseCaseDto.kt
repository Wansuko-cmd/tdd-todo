package dto.project

import feature.FeatureId
import project.Project

data class ProjectUseCaseDto(
    val id: String,
    val title: String,
    val description: String,
    val features: List<FeatureId>
) {
    companion object {
        fun Project.toUseCaseDto() = ProjectUseCaseDto(
            id = id.value,
            title = title.value,
            description = description.value,
            features = features,
        )
    }
}

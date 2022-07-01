package dto.project

import feature.FeatureId
import project.Project
import project.ProjectDescription
import project.ProjectId
import project.ProjectTitle

data class ProjectUseCaseDto(
    val id: ProjectId,
    val title: ProjectTitle,
    val description: ProjectDescription,
    val features: List<FeatureId>
) {
    companion object {
        internal fun Project.toUseCaseDto() = ProjectUseCaseDto(
            id = id,
            title = title,
            description = description,
            features = features,
        )
    }
}

package dto.project

import project.Project

data class ProjectUseCaseDto(
    val id: String,
    val title: String,
    val description: String,
) {
    companion object {
        fun Project.toUseCaseDto() = ProjectUseCaseDto(
            id = id.value,
            title = title.value,
            description = description.value,
        )
    }
}

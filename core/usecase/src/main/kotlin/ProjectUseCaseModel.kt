import project.Project

data class ProjectUseCaseModel(
    val id: String,
    val title: String,
    val description: String,
) {
    companion object {
        fun Project.toUseCaseModel() = ProjectUseCaseModel(
            id = id.value,
            title = title.value,
            description = description.value,
        )
    }
}

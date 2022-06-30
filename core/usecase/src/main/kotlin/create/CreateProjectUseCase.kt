package create

import project.Project
import project.ProjectDescription
import project.ProjectRepository
import project.ProjectTitle

class CreateProjectUseCase(private val projectRepository: ProjectRepository) {
    suspend operator fun invoke(title: ProjectTitle, description: ProjectDescription) =
        Project(title = title, description = description)
            .let { projectRepository.insert(it) }
}

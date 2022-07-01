package create

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import dto.project.ProjectUseCaseDto
import dto.project.ProjectUseCaseDto.Companion.toUseCaseDto
import project.Project
import project.ProjectDescription
import project.ProjectRepository
import project.ProjectTitle
import toUseCaseException

class CreateProjectUseCase(private val projectRepository: ProjectRepository) {
    suspend operator fun invoke(
        title: ProjectTitle,
        description: ProjectDescription,
    ): ApiResult<ProjectUseCaseDto, UseCaseException> =
        Project.create(title = title, description = description)
            .let { project ->
                projectRepository.insert(project).mapBoth(
                    success = { project.toUseCaseDto() },
                    failure = { it.toUseCaseException() }
                )
            }
}

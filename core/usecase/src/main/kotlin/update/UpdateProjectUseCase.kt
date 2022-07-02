package update

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.mapBoth
import dto.project.ProjectQueryService
import dto.project.ProjectUseCaseDto
import dto.project.ProjectUseCaseDto.Companion.toUseCaseDto
import project.Project
import project.ProjectDescription
import project.ProjectId
import project.ProjectRepository
import project.ProjectTitle
import toUseCaseException

class UpdateProjectUseCase(
    private val projectQueryService: ProjectQueryService,
    private val projectRepository: ProjectRepository,
) {
    suspend operator fun invoke(
        projectId: ProjectId,
        title: ProjectTitle,
    ): ApiResult<ProjectUseCaseDto, UseCaseException> =
        update(projectId) { project -> project.changeTitle(title = title) }

    suspend operator fun invoke(
        projectId: ProjectId,
        description: ProjectDescription,
    ): ApiResult<ProjectUseCaseDto, UseCaseException> =
        update(projectId) { project -> project.changeDescription(description = description) }

    private suspend fun update(projectId: ProjectId, newProjectBuilder: (Project) -> Project) =
        projectQueryService.get(projectId)
            .mapBoth(
                success = newProjectBuilder,
                failure = { it.toUseCaseException() },
            )
            .flatMap { project ->
                projectRepository
                    .update(project)
                    .mapBoth(
                        success = { project.toUseCaseDto() },
                        failure = { it.toUseCaseException() },
                    )
            }
}

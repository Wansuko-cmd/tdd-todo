package update

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.mapBoth
import com.wsr.apiresult.mapFailure
import dto.project.ProjectQueryService
import project.*
import toUseCaseException

class UpdateProjectUseCase(
    private val projectQueryService: ProjectQueryService,
    private val projectRepository: ProjectRepository,
) {
    suspend operator fun invoke(
        projectId: ProjectId,
        title: ProjectTitle,
    ): ApiResult<Unit, UseCaseException> =
        update(projectId) { project -> project.copy(title = title) }

    suspend operator fun invoke(
        projectId: ProjectId,
        description: ProjectDescription,
    ): ApiResult<Unit, UseCaseException> =
        update(projectId) { project -> project.copy(description = description) }

    private suspend fun update(projectId: ProjectId, newProjectBuilder: (Project) -> Project) =
        projectQueryService.get(projectId)
            .mapBoth(
                success = newProjectBuilder,
                failure = { it.toUseCaseException() },
            )
            .flatMap { task ->
                projectRepository
                    .update(task)
                    .mapFailure { it.toUseCaseException() }
            }
}

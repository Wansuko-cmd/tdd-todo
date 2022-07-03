package delete

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.map
import com.wsr.apiresult.mapFailure
import com.wsr.apiresult.sequence
import dto.feature.FeatureQueryService
import dto.task.TaskQueryService
import feature.FeatureRepository
import project.ProjectId
import project.ProjectRepository
import task.TaskRepository
import toUseCaseException

class DeleteProjectUseCase(
    private val taskQueryService: TaskQueryService,
    private val featureQueryService: FeatureQueryService,
    private val featureRepository: FeatureRepository,
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository,
) {
    private val featureDeleter = FeatureDeleter(taskQueryService, featureRepository, taskRepository)

    suspend operator fun invoke(projectId: ProjectId): ApiResult<Unit, UseCaseException> {
        return projectRepository.delete(projectId)
            .mapFailure { it.toUseCaseException() }
            .flatMap {
                featureQueryService.getByProjectId(projectId)
                    .mapFailure { it.toUseCaseException() }
                    .map { features -> features.map { it.id } }
                    .flatMap { featureIds ->
                        featureIds.map { featureDeleter(it) }
                            .sequence()
                            .map { /*** do nothing ***/ }
                    }
            }
    }
}

package delete

import UseCaseException
import com.wsr.apiresult.ApiResult
import dto.task.TaskQueryService
import feature.FeatureId
import feature.FeatureRepository
import task.TaskRepository

class DeleteFeatureUseCase(
    taskQueryService: TaskQueryService,
    featureRepository: FeatureRepository,
    taskRepository: TaskRepository,
) {
    private val featureDeleter = FeatureDeleter(taskQueryService, featureRepository, taskRepository)

    suspend operator fun invoke(featureId: FeatureId): ApiResult<Unit, UseCaseException> =
        featureDeleter(featureId)
}

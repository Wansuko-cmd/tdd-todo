package delete

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.map
import com.wsr.apiresult.mapFailure
import dto.task.TaskQueryService
import feature.FeatureId
import feature.FeatureRepository
import task.TaskRepository
import toUseCaseException

class DeleteFeatureUseCase(
    private val taskQueryService: TaskQueryService,
    private val featureRepository: FeatureRepository,
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(featureId: FeatureId): ApiResult<Unit, UseCaseException> =
        featureRepository.delete(featureId)
            .mapFailure { it.toUseCaseException() }
            .flatMap { taskQueryService.getByFeatureId(featureId).mapFailure { it.toUseCaseException() } }
            .map { tasks -> tasks.map { it.id } }
            .map { taskIds -> taskRepository.delete(taskIds) }
}

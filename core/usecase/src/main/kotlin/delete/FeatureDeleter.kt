package delete

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.map
import com.wsr.apiresult.mapBoth
import com.wsr.apiresult.mapFailure
import com.wsr.apiresult.sequence
import dto.task.TaskQueryService
import feature.FeatureId
import feature.FeatureRepository
import task.TaskRepository
import toUseCaseException

internal class FeatureDeleter(
    private val taskQueryService: TaskQueryService,
    private val featureRepository: FeatureRepository,
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(featureId: FeatureId): ApiResult<Unit, UseCaseException> =
        deleteFeature(featureId).flatMap { deleteTasksByFeatureId(featureId) }

    private suspend fun deleteFeature(featureId: FeatureId): ApiResult<Unit, UseCaseException> =
        featureRepository.delete(featureId)
            .mapFailure { it.toUseCaseException() }

    private suspend fun deleteTasksByFeatureId(featureId: FeatureId): ApiResult<Unit, UseCaseException> =
        taskQueryService.getByFeatureId(featureId)
            .mapFailure { it.toUseCaseException() }
            .map { tasks -> tasks.map { it.id } }
            .flatMap { taskIds ->
                taskIds.map { taskRepository.delete(it) }
                    .sequence()
                    .mapBoth(success = { /*** do nothing ***/ }, failure = { it.toUseCaseException() })
            }
}

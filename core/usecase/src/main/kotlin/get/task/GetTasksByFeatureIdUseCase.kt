package get.task

import dto.TaskUseCaseModel
import dto.TaskUseCaseModel.Companion.toUseCaseModel
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import feature.FeatureId

class GetTasksByFeatureIdUseCase(private val queryService: GetTaskQueryService) {
    suspend operator fun invoke(featureId: FeatureId): ApiResult<List<TaskUseCaseModel>, GetTaskUseCaseException> =
        queryService.getByFeatureId(featureId).mapBoth(
            success = { tasks -> tasks.map { it.toUseCaseModel() } },
            failure = { GetTaskUseCaseException.DatabaseException(it.message) }
        )
}

sealed class GetTaskUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : GetTaskUseCaseException()
}

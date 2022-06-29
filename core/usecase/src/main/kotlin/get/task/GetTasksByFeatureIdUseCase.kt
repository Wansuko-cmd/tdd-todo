package get.task

import dto.TaskUseCaseDto
import dto.TaskUseCaseDto.Companion.toUseCaseDto
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import feature.FeatureId

class GetTasksByFeatureIdUseCase(private val queryService: GetTaskQueryService) {
    suspend operator fun invoke(featureId: FeatureId): ApiResult<List<TaskUseCaseDto>, GetTaskUseCaseException> =
        queryService.getByFeatureId(featureId).mapBoth(
            success = { tasks -> tasks.map { it.toUseCaseDto() } },
            failure = { GetTaskUseCaseException.DatabaseException(it.message) }
        )
}

sealed class GetTaskUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : GetTaskUseCaseException()
}

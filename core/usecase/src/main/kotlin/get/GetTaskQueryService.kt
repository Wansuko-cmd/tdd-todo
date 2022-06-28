package get

import com.wsr.apiresult.ApiResult
import feature.FeatureId
import task.Task

interface GetTaskQueryService {
    suspend fun getByFeatureId(featureId: FeatureId): ApiResult<List<Task>, Exception>
}

sealed class GetTaskUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : GetTaskUseCaseException()
}

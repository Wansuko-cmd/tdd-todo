package get

import QueryServiceException
import com.wsr.apiresult.ApiResult
import feature.FeatureId
import task.Task

interface GetTaskQueryService {
    suspend fun getByFeatureId(featureId: FeatureId): ApiResult<List<Task>, QueryServiceException>
}

sealed class GetTaskUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : GetTaskUseCaseException()
}

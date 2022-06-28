package get.task

import TaskUseCaseModel
import com.wsr.apiresult.ApiResult

interface GetTaskUseCase {
    suspend fun getByFeatureId(featureId: String): ApiResult<List<TaskUseCaseModel>, GetTaskUseCaseException>
}

sealed class GetTaskUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : GetTaskUseCaseException()
}

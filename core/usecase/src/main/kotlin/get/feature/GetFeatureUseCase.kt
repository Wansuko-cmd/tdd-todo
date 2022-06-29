package get.feature

import dto.FeatureUseCaseModel
import com.wsr.apiresult.ApiResult

interface GetFeatureUseCase {
    suspend fun getByProjectId(projectId: String): ApiResult<List<FeatureUseCaseModel>, Exception>
}

sealed class GetFeatureUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : GetFeatureUseCaseException()
}

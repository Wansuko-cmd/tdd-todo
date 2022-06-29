package get.feature

import dto.feature.FeatureUseCaseDto
import com.wsr.apiresult.ApiResult

interface GetFeatureUseCase {
    suspend fun getByProjectId(projectId: String): ApiResult<List<FeatureUseCaseDto>, Exception>
}

sealed class GetFeatureUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : GetFeatureUseCaseException()
}

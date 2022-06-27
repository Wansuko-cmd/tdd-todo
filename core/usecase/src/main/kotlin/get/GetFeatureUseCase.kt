package get

import FeatureUseCaseModel
import com.wsr.apiresult.ApiResult

interface GetFeatureUseCase {
    suspend fun getByProjectId(projectId: String): ApiResult<List<FeatureUseCaseModel>, Exception>
}

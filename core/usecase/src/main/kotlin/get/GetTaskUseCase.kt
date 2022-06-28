package get

import TaskUseCaseModel
import com.wsr.apiresult.ApiResult

interface GetTaskUseCase {
    suspend fun getByFeatureId(featureId: String): ApiResult<List<TaskUseCaseModel>, Exception>
}

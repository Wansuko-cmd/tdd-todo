package get

import TaskUseCaseModel
import TaskUseCaseModel.Companion.toUseCaseModel
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import feature.FeatureId

class GetTaskUseCaseImpl(private val queryService: GetTaskQueryService) : GetTaskUseCase {
    override suspend fun getByFeatureId(featureId: String): ApiResult<List<TaskUseCaseModel>, GetTaskUseCaseException> =
        queryService.getByFeatureId(FeatureId(featureId)).mapBoth(
            success = { tasks -> tasks.map { it.toUseCaseModel() } },
            failure = { GetTaskUseCaseException.DatabaseException(it.message) }
        )
}

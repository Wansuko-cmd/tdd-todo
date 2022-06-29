package dto.task

import QueryServiceException
import com.wsr.apiresult.ApiResult
import feature.FeatureId
import task.Task

interface TaskQueryService {
    suspend fun getByFeatureId(featureId: FeatureId): ApiResult<List<Task>, QueryServiceException>
}

package dto.task

import QueryServiceException
import com.wsr.apiresult.ApiResult
import feature.FeatureId
import task.Task
import task.TaskId

interface TaskQueryService {
    suspend fun get(taskId: TaskId): ApiResult<Task, QueryServiceException>
    suspend fun getByFeatureId(featureId: FeatureId): ApiResult<List<Task>, QueryServiceException>
}

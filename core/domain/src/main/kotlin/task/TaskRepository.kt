package task

import RepositoryException
import com.wsr.apiresult.ApiResult
import feature.FeatureId

interface TaskRepository {
    suspend fun insert(task: Task, featureId: FeatureId): ApiResult<Unit, RepositoryException>
    suspend fun update(task: Task): ApiResult<Unit, RepositoryException>
    suspend fun delete(taskId: TaskId): ApiResult<Unit, RepositoryException>
}

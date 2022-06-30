package task

import RepositoryException
import com.wsr.apiresult.ApiResult

interface TaskRepository {
    suspend fun insert(task: Task): ApiResult<Unit, RepositoryException>
    suspend fun update(task: Task): ApiResult<Unit, RepositoryException>
    suspend fun delete(taskId: TaskId): ApiResult<Unit, RepositoryException>
}

package task

import RepositoryException
import com.wsr.apiresult.ApiResult

interface TaskRepository {
    suspend fun update(task: Task): ApiResult<Unit, RepositoryException>
}

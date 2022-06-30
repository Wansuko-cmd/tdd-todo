package project

import RepositoryException
import com.wsr.apiresult.ApiResult

interface ProjectRepository {
    suspend fun insert(project: Project): ApiResult<Unit, RepositoryException>
    suspend fun update(project: Project): ApiResult<Unit, RepositoryException>
}

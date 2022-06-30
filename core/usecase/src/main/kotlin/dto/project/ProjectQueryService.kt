package dto.project

import QueryServiceException
import com.wsr.apiresult.ApiResult
import project.Project
import project.ProjectId

interface ProjectQueryService {
    suspend fun get(projectId: ProjectId): ApiResult<Project, QueryServiceException>
    suspend fun getAll(): ApiResult<List<Project>, QueryServiceException>
}

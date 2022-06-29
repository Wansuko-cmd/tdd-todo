package dto.project

import QueryServiceException
import com.wsr.apiresult.ApiResult
import project.Project

interface ProjectQueryService {
    suspend fun getAll(): ApiResult<List<Project>, QueryServiceException>
}

package get

import com.wsr.apiresult.ApiResult
import project.Project

interface GetProjectQueryService {
    suspend fun getAll(): ApiResult<List<Project>, Exception>
}

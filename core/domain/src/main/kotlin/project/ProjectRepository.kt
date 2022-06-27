package project

import com.wsr.apiresult.ApiResult

interface ProjectRepository {
    suspend fun getAll(): ApiResult<List<Project>, Exception>
}

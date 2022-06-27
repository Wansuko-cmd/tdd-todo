package get

import com.wsr.apiresult.ApiResult
import project.Project

interface GetProjectUseCase {
    suspend fun getAll(): ApiResult<List<Project>, Exception>
}

sealed class GetProjectUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : GetProjectUseCaseException()
}

package get.project

import dto.ProjectUseCaseModel
import com.wsr.apiresult.ApiResult

interface GetProjectUseCase {
    suspend fun getAll(): ApiResult<List<ProjectUseCaseModel>, Exception>
}

sealed class GetProjectUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : GetProjectUseCaseException()
}

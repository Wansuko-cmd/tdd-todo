package get.project

import dto.project.ProjectUseCaseDto
import com.wsr.apiresult.ApiResult

interface GetProjectUseCase {
    suspend fun getAll(): ApiResult<List<ProjectUseCaseDto>, Exception>
}

sealed class GetProjectUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : GetProjectUseCaseException()
}

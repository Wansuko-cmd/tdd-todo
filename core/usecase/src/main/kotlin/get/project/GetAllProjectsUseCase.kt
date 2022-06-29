package get.project

import dto.project.ProjectUseCaseDto
import dto.project.ProjectUseCaseDto.Companion.toUseCaseDto
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import dto.project.ProjectQueryService

class GetAllProjectsUseCase(private val queryService: ProjectQueryService) {
    suspend operator fun invoke(): ApiResult<List<ProjectUseCaseDto>, GetAllProjectsUseCaseException> =
        queryService.getAll().mapBoth(
            success = { projects -> projects.map { it.toUseCaseDto() } },
            failure = { GetAllProjectsUseCaseException.DatabaseException(it.message) },
        )
}

sealed class GetAllProjectsUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : GetAllProjectsUseCaseException()
}

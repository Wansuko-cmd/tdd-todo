package get.project

import dto.project.ProjectUseCaseDto
import dto.project.ProjectUseCaseDto.Companion.toUseCaseDto
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import dto.project.ProjectQueryService

class GetProjectUseCaseImpl(private val queryService: ProjectQueryService) : GetProjectUseCase {
    override suspend fun getAll(): ApiResult<List<ProjectUseCaseDto>, GetProjectUseCaseException> =
        queryService.getAll().mapBoth(
            success = { projects -> projects.map { it.toUseCaseDto() } },
            failure = { GetProjectUseCaseException.DatabaseException(it.message) },
        )
}

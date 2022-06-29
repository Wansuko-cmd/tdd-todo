package get.project

import dto.ProjectUseCaseDto
import dto.ProjectUseCaseDto.Companion.toUseCaseDto
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth

class GetProjectUseCaseImpl(private val queryService: GetProjectQueryService) : GetProjectUseCase {
    override suspend fun getAll(): ApiResult<List<ProjectUseCaseDto>, GetProjectUseCaseException> =
        queryService.getAll().mapBoth(
            success = { projects -> projects.map { it.toUseCaseDto() } },
            failure = { GetProjectUseCaseException.DatabaseException(it.message) },
        )
}

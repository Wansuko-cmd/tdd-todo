package get.project

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import dto.project.ProjectQueryService
import dto.project.ProjectUseCaseDto
import dto.project.ProjectUseCaseDto.Companion.toUseCaseDto
import toUseCaseException

class GetAllProjectsUseCase(private val queryService: ProjectQueryService) {
    suspend operator fun invoke(): ApiResult<List<ProjectUseCaseDto>, UseCaseException> =
        queryService.getAll().mapBoth(
            success = { projects -> projects.map { it.toUseCaseDto() } },
            failure = { it.toUseCaseException() },
        )
}

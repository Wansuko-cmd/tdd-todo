package get.feature

import UseCaseException
import dto.feature.FeatureUseCaseDto
import dto.feature.FeatureUseCaseDto.Companion.toUseCaseDto
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import dto.feature.FeatureQueryService
import project.ProjectId
import toUseCaseException

class GetFeaturesByProjectIdUseCase(private val queryService: FeatureQueryService) {
    suspend operator fun invoke(projectId: ProjectId): ApiResult<List<FeatureUseCaseDto>, UseCaseException> =
        queryService.getByProjectId(projectId).mapBoth(
            success = { features -> features.map { it.toUseCaseDto() } },
            failure = { it.toUseCaseException() },
        )
}

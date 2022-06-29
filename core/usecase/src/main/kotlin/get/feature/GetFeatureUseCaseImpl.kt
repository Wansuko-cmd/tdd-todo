package get.feature

import dto.feature.FeatureUseCaseDto
import dto.feature.FeatureUseCaseDto.Companion.toUseCaseDto
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import dto.feature.FeatureQueryService
import project.ProjectId

class GetFeatureUseCaseImpl(private val queryService: FeatureQueryService) : GetFeatureUseCase {
    override suspend fun getByProjectId(projectId: String): ApiResult<List<FeatureUseCaseDto>, Exception> =
        queryService.getByProjectId(ProjectId(projectId)).mapBoth(
            success = { features -> features.map { it.toUseCaseDto() } },
            failure = { GetFeatureUseCaseException.DatabaseException(it.message) },
        )
}

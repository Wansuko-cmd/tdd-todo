package get.feature

import dto.FeatureUseCaseDto
import dto.FeatureUseCaseDto.Companion.toUseCaseDto
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import project.ProjectId

class GetFeatureUseCaseImpl(private val queryService: GetFeatureQueryService) : GetFeatureUseCase {
    override suspend fun getByProjectId(projectId: String): ApiResult<List<FeatureUseCaseDto>, Exception> =
        queryService.getByProjectId(ProjectId(projectId)).mapBoth(
            success = { features -> features.map { it.toUseCaseDto() } },
            failure = { GetFeatureUseCaseException.DatabaseException(it.message) },
        )
}

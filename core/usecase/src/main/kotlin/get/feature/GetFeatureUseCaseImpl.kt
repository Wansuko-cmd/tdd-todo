package get.feature

import dto.FeatureUseCaseModel
import dto.FeatureUseCaseModel.Companion.toUseCaseModel
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import project.ProjectId

class GetFeatureUseCaseImpl(private val queryService: GetFeatureQueryService) : GetFeatureUseCase {
    override suspend fun getByProjectId(projectId: String): ApiResult<List<FeatureUseCaseModel>, Exception> =
        queryService.getByProjectId(ProjectId(projectId)).mapBoth(
            success = { features -> features.map { it.toUseCaseModel() } },
            failure = { GetFeatureUseCaseException.DatabaseException(it.message) },
        )
}

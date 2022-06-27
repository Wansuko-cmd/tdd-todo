package get

import FeatureUseCaseModel
import FeatureUseCaseModel.Companion.toUseCaseModel
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.map
import project.ProjectId

class GetFeatureUseCaseImpl(private val queryService: GetFeatureQueryService) : GetFeatureUseCase {
    override suspend fun getByProjectId(projectId: String): ApiResult<List<FeatureUseCaseModel>, Exception> =
        queryService.getByProjectId(ProjectId(projectId)).map { features -> features.map { it.toUseCaseModel() } }
}

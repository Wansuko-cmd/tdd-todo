package dto.feature

import QueryServiceException
import com.wsr.apiresult.ApiResult
import feature.Feature
import feature.FeatureId
import project.ProjectId

interface FeatureQueryService {
    suspend fun get(featureId: FeatureId): ApiResult<Feature, QueryServiceException>
    suspend fun getByProjectId(projectId: ProjectId): ApiResult<List<Feature>, QueryServiceException>
}

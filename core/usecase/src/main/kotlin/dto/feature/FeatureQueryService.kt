package dto.feature

import QueryServiceException
import com.wsr.apiresult.ApiResult
import feature.Feature
import project.ProjectId

interface FeatureQueryService {
    suspend fun getByProjectId(projectId: ProjectId): ApiResult<List<Feature>, QueryServiceException>
}

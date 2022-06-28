package get

import QueryServiceException
import com.wsr.apiresult.ApiResult
import feature.Feature
import project.ProjectId

interface GetFeatureQueryService {
    suspend fun getByProjectId(projectId: ProjectId): ApiResult<List<Feature>, QueryServiceException>
}

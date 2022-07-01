package feature

import RepositoryException
import com.wsr.apiresult.ApiResult
import project.ProjectId

interface FeatureRepository {
    suspend fun insert(feature: Feature, projectId: ProjectId): ApiResult<Unit, RepositoryException>
    suspend fun update(feature: Feature): ApiResult<Unit, RepositoryException>
    suspend fun delete(featureId: FeatureId): ApiResult<Unit, RepositoryException>
}

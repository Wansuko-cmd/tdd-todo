package feature

import RepositoryException
import com.wsr.apiresult.ApiResult

interface FeatureRepository {
    suspend fun update(feature: Feature): ApiResult<Unit, RepositoryException>
}
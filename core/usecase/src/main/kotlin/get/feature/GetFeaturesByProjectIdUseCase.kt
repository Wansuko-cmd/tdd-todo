package get.feature

import dto.feature.FeatureUseCaseDto
import dto.feature.FeatureUseCaseDto.Companion.toUseCaseDto
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import dto.feature.FeatureQueryService
import project.ProjectId

class GetFeaturesByProjectIdUseCase(private val queryService: FeatureQueryService) {
    suspend operator fun invoke(projectId: ProjectId): ApiResult<List<FeatureUseCaseDto>, Exception> =
        queryService.getByProjectId(projectId).mapBoth(
            success = { features -> features.map { it.toUseCaseDto() } },
            failure = { GetFeatureUseCaseException.DatabaseException(it.message) },
        )
}

sealed class GetFeatureUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : GetFeatureUseCaseException()
}

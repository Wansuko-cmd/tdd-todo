package update

import com.wsr.apiresult.*
import dto.feature.FeatureQueryService
import feature.FeatureId
import feature.FeaturePhase
import feature.FeatureRepository

class UpdateFeaturePhaseUseCase(
    private val featureQueryService: FeatureQueryService,
    private val featureRepository: FeatureRepository,
) {
    suspend operator fun invoke(
        featureId: FeatureId,
        phase: FeaturePhase,
    ): ApiResult<Unit, UpdateFeaturePhaseUseCaseException> =
        featureQueryService.get(featureId)
            .mapBoth(
                success = { feature -> feature.copyWithPhase(phase) },
                failure = { UpdateFeaturePhaseUseCaseException.DatabaseException(it.message) },
            )
            .flatMap { feature ->
                featureRepository
                    .update(feature)
                    .mapFailure { UpdateFeaturePhaseUseCaseException.DatabaseException(it.message) }
            }
}

sealed class UpdateFeaturePhaseUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : UpdateFeaturePhaseUseCaseException()
}

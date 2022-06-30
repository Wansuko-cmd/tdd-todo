package update

import com.wsr.apiresult.*
import dto.feature.FeatureQueryService
import feature.Feature
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
    ): ApiResult<Unit, UpdateFeatureUseCaseException> =
        update(featureId) { feature -> feature.copyWithPhase(phase) }

    private suspend fun update(featureId: FeatureId, newFeatureBuilder: (Feature) -> Feature) =
        featureQueryService.get(featureId)
            .mapBoth(
                success = newFeatureBuilder,
                failure = { UpdateFeatureUseCaseException.DatabaseException(it.message) },
            )
            .flatMap { feature ->
                featureRepository
                    .update(feature)
                    .mapFailure { UpdateFeatureUseCaseException.DatabaseException(it.message) }
            }
}

sealed class UpdateFeatureUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : UpdateFeatureUseCaseException()
}

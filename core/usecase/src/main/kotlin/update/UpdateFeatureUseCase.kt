package update

import UseCaseException
import com.wsr.apiresult.*
import dto.feature.FeatureQueryService
import feature.*
import toUseCaseException

class UpdateFeatureUseCase(
    private val featureQueryService: FeatureQueryService,
    private val featureRepository: FeatureRepository,
) {
    suspend operator fun invoke(
        featureId: FeatureId,
        title: FeatureTitle,
    ): ApiResult<Unit, UseCaseException> =
        update(featureId) { feature -> feature.copy(title = title) }

    suspend operator fun invoke(
        featureId: FeatureId,
        description: FeatureDescription,
    ): ApiResult<Unit, UseCaseException> =
        update(featureId) { feature -> feature.copy(description = description) }

    suspend operator fun invoke(
        featureId: FeatureId,
        phase: FeaturePhase,
    ): ApiResult<Unit, UseCaseException> =
        update(featureId) { feature -> feature.changePhase(phase) }

    private suspend fun update(featureId: FeatureId, newFeatureBuilder: (Feature) -> Feature) =
        featureQueryService.get(featureId)
            .mapBoth(
                success = newFeatureBuilder,
                failure = { it.toUseCaseException() },
            )
            .flatMap { feature ->
                featureRepository
                    .update(feature)
                    .mapFailure { it.toUseCaseException() }
            }
}

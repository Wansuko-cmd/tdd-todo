package update

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.mapBoth
import com.wsr.apiresult.mapFailure
import dto.feature.FeatureQueryService
import feature.Feature
import feature.FeatureDescription
import feature.FeatureId
import feature.FeaturePhase
import feature.FeatureRepository
import feature.FeatureTitle
import toUseCaseException

class UpdateFeatureUseCase(
    private val featureQueryService: FeatureQueryService,
    private val featureRepository: FeatureRepository,
) {
    suspend operator fun invoke(
        featureId: FeatureId,
        title: FeatureTitle,
    ): ApiResult<Unit, UseCaseException> =
        update(featureId) { feature -> feature.changeTitle(title = title) }

    suspend operator fun invoke(
        featureId: FeatureId,
        description: FeatureDescription,
    ): ApiResult<Unit, UseCaseException> =
        update(featureId) { feature -> feature.changeDescription(description = description) }

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

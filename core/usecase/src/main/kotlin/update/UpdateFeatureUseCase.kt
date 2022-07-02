package update

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.mapBoth
import dto.feature.FeatureQueryService
import dto.feature.FeatureUseCaseDto
import dto.feature.FeatureUseCaseDto.Companion.toUseCaseDto
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
    ): ApiResult<FeatureUseCaseDto, UseCaseException> =
        update(featureId) { feature -> feature.changeTitle(title = title) }

    suspend operator fun invoke(
        featureId: FeatureId,
        description: FeatureDescription,
    ): ApiResult<FeatureUseCaseDto, UseCaseException> =
        update(featureId) { feature -> feature.changeDescription(description = description) }

    suspend operator fun invoke(
        featureId: FeatureId,
        phase: FeaturePhase,
    ): ApiResult<FeatureUseCaseDto, UseCaseException> =
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
                    .mapBoth(
                        success = { feature.toUseCaseDto() },
                        failure = { it.toUseCaseException() },
                    )
            }
}

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
    suspend operator fun invoke(featureId: FeatureId, phase: FeaturePhase): ApiResult<Unit, Exception> =
        featureQueryService.get(featureId)
            .map { it.copyWithPhase(phase) }
            .flatMap { feature ->
                featureRepository.update(feature)
            }
}

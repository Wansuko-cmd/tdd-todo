package update

import com.wsr.apiresult.*
import dto.feature.FeaturePhaseUseCaseDto
import dto.feature.FeaturePhaseUseCaseDto.Companion.toDomain
import dto.feature.FeatureQueryService
import feature.FeatureId
import feature.FeatureRepository

class UpdateFeaturePhaseUseCase(
    private val featureQueryService: FeatureQueryService,
    private val featureRepository: FeatureRepository,
) {
    suspend operator fun invoke(featureId: FeatureId, phase: FeaturePhaseUseCaseDto): ApiResult<Unit, Exception> =
        featureQueryService.get(featureId)
            .map { it.copyWithPhase(phase.toDomain()) }
            .flatMap { feature ->
                featureRepository.update(feature)
            }
}

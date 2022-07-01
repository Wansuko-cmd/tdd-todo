package create

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import dto.feature.FeatureUseCaseDto
import dto.feature.FeatureUseCaseDto.Companion.toUseCaseDto
import feature.Feature
import feature.FeatureDescription
import feature.FeatureRepository
import feature.FeatureTitle
import project.ProjectId
import toUseCaseException

class CreateFeatureUseCase(private val featureRepository: FeatureRepository) {
    suspend operator fun invoke(
        projectId: ProjectId,
        title: FeatureTitle,
        description: FeatureDescription,
    ): ApiResult<FeatureUseCaseDto, UseCaseException> =
        Feature.create(title = title, description = description)
            .let { feature ->
                featureRepository.insert(feature, projectId).mapBoth(
                    success = { feature.toUseCaseDto() },
                    failure = { it.toUseCaseException() },
                )
            }
}

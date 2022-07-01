package create

import com.wsr.apiresult.mapFailure
import feature.Feature
import feature.FeatureDescription
import feature.FeatureRepository
import feature.FeatureTitle
import project.ProjectId
import toUseCaseException

class CreateFeatureUseCase(private val featureRepository: FeatureRepository) {
    suspend operator fun invoke(projectId: ProjectId, title: FeatureTitle, description: FeatureDescription) =
        Feature.create(title = title, description = description)
            .let { featureRepository.insert(it) }
            .mapFailure { it.toUseCaseException() }
}

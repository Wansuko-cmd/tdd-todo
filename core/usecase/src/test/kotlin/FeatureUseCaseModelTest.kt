@file:Suppress("NonAsciiCharacters", "TestFunctionName")

import FeaturePhaseUseCaseModel.Companion.toUseCaseModel
import FeatureUseCaseModel.Companion.toUseCaseModel
import feature.*
import org.javalite.test.jspec.JSpec.the
import org.junit.Test
import project.ProjectId

class FeatureUseCaseModelTest {

    private val feature = Feature(
        id = FeatureId("mockId"),
        projectId = ProjectId("mockProjectId"),
        title = FeatureTitle("mockTitle"),
        description = FeatureDescription("mockDescription"),
        phase = FeaturePhase.Todo,
    )

    @Test
    fun Featureから変換可能() {
        val expected = FeatureUseCaseModel(
            id = feature.id.value,
            title = feature.title.value,
            description = feature.description.value,
            phase = feature.phase.toUseCaseModel(),
        )
        the(feature.toUseCaseModel()).shouldBeEqual(expected)
    }
}

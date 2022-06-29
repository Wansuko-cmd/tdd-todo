@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package dto

import dto.feature.FeaturePhaseUseCaseDto.Companion.toUseCaseDto
import dto.feature.FeatureUseCaseDto
import dto.feature.FeatureUseCaseDto.Companion.toUseCaseDto
import feature.*
import org.javalite.test.jspec.JSpec.the
import org.junit.Test
import project.ProjectId

class FeatureUseCaseDtoTest {

    private val feature = Feature(
        id = FeatureId("mockId"),
        projectId = ProjectId("mockProjectId"),
        title = FeatureTitle("mockTitle"),
        description = FeatureDescription("mockDescription"),
        phase = FeaturePhase.Todo,
    )

    @Test
    fun Featureから変換可能() {
        val expected = FeatureUseCaseDto(
            id = feature.id.value,
            title = feature.title.value,
            description = feature.description.value,
            phase = feature.phase.toUseCaseDto(),
        )
        the(feature.toUseCaseDto()).shouldBeEqual(expected)
    }
}

@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package dto

import dto.feature.FeatureUseCaseDto
import dto.feature.FeatureUseCaseDto.Companion.toUseCaseDto
import feature.*
import org.javalite.test.jspec.JSpec.the
import org.junit.Test
import project.ProjectId

class FeatureUseCaseDtoTest {

    private val feature = Feature.create(
        title = FeatureTitle("mockTitle"),
        description = FeatureDescription("mockDescription"),
    )

    @Test
    fun Featureから変換可能() {
        val expected = FeatureUseCaseDto(
            id = feature.id.value,
            title = feature.title.value,
            description = feature.description.value,
            phase = feature.phase,
        )
        the(feature.toUseCaseDto()).shouldBeEqual(expected)
    }
}

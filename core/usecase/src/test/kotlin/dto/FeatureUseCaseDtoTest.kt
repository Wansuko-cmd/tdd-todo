@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package dto

import dto.feature.FeatureUseCaseDto
import dto.feature.FeatureUseCaseDto.Companion.toUseCaseDto
import feature.Feature
import feature.FeatureDescription
import feature.FeatureTitle
import org.javalite.test.jspec.JSpec.the
import org.junit.Test

class FeatureUseCaseDtoTest {

    private val feature = Feature.create(
        title = FeatureTitle("mockTitle"),
        description = FeatureDescription("mockDescription"),
    )

    @Test
    fun Featureから変換可能() {
        val expected = FeatureUseCaseDto(
            id = feature.id,
            title = feature.title,
            description = feature.description,
            phase = feature.phase,
            tasks = feature.tasks
        )
        the(feature.toUseCaseDto()).shouldBeEqual(expected)
    }
}

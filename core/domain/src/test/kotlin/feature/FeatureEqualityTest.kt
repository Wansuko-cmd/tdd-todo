@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package feature

import org.javalite.test.jspec.JSpec.the
import kotlin.test.Test

class FeatureEqualityTest {
    @Test
    fun FeatureはIdで等価性を判断() {
        val mockFeature = Feature.create(
            title = FeatureTitle("mockFeatureTitle"),
            description = FeatureDescription("mockFeatureDescription"),
        )
        val otherFeature = Feature.reconstruct(
            id = mockFeature.id,
            title = FeatureTitle("otherFeatureTitle"),
            description = FeatureDescription("otherFeatureDescription"),
            tasks = listOf(),
            phase = FeaturePhase.InProgress
        )
        the(mockFeature).shouldBeEqual(otherFeature)
    }
}

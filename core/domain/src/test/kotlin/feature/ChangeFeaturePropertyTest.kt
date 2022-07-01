@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package feature

import org.javalite.test.jspec.JSpec.the
import kotlin.test.Test

class ChangeFeaturePropertyTest {

    private val mockFeature = Feature.create(
        title = FeatureTitle("mockFeatureTitle"),
        description = FeatureDescription("mockFeatureDescription"),
    )

    @Test
    fun FeatureのTitleを書き換えたコピーを作成() {
        val copiedFeature = mockFeature.changeTitle(FeatureTitle("copiedFeatureTitle"))
        the(copiedFeature.title).shouldBeEqual(FeatureTitle("copiedFeatureTitle"))
    }

    @Test
    fun FeatureのDescriptionを書き換えたコピーを作成() {
        val copiedFeature = mockFeature.changeDescription(FeatureDescription("copiedFeatureDescription"))
        the(copiedFeature.description).shouldBeEqual(FeatureDescription("copiedFeatureDescription"))
    }

    @Test
    fun FeatureのPhaseを書き換えたコピーを作成() {
        val copiedFeature = mockFeature.changePhase(FeaturePhase.Done)
        the(copiedFeature.phase).shouldBeEqual(FeaturePhase.Done)
    }
}

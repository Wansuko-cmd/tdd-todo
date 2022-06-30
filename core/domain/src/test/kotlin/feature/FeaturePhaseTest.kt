@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package feature

import org.javalite.test.jspec.JSpec.the
import kotlin.test.Test

class FeaturePhaseTest {
    @Test
    fun FeaturePhaseの等価性のテスト() {
        the(FeaturePhase.Todo).shouldBeEqual(FeaturePhase.Todo)
        the(FeaturePhase.Todo).shouldNotBeEqual(FeaturePhase.Done)
    }
}

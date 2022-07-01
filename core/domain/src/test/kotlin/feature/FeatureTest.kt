@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package feature

import io.mockk.every
import io.mockk.mockkStatic
import org.javalite.test.jspec.JSpec.the
import task.*
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class FeatureTest {
    private lateinit var mockFeature: Feature

    @BeforeTest
    fun setup() {
        mockkStatic(UUID::class)
        val uuid = "mockUUID"
        every { UUID.randomUUID().toString() } returns uuid
        mockFeature = Feature.create(
            title = FeatureTitle("mockFeatureTitle"),
            description = FeatureDescription("mockFeatureDescription"),
        )
    }

    @Test
    fun Featureを一から生成() {
        val mockFeature = Feature.create(
            title = FeatureTitle("mockFeatureTitle"),
            description = FeatureDescription("mockFeatureDescription"),
        )

        the(mockFeature.id).shouldBeEqual(FeatureId("mockUUID"))
        the(mockFeature.title).shouldBeEqual(FeatureTitle("mockFeatureTitle"))
        the(mockFeature.description).shouldBeEqual(FeatureDescription("mockFeatureDescription"))
        the(mockFeature.phase).shouldBeEqual(FeaturePhase.Todo)
        the(mockFeature.tasks).shouldBeEqual(listOf<TaskId>())
    }

    @Test
    fun Featureを再生成() {
        val mockFeature = Feature.reconstruct(
            id = FeatureId("mockFeatureId"),
            title = FeatureTitle("mockFeatureTitle"),
            description = FeatureDescription("mockFeatureDescription"),
            phase = FeaturePhase.InProgress,
            tasks = List(3) { index -> TaskId("mockTaskId$index") },
        )

        the(mockFeature.id).shouldBeEqual(FeatureId("mockFeatureId"))
        the(mockFeature.title).shouldBeEqual(FeatureTitle("mockFeatureTitle"))
        the(mockFeature.description).shouldBeEqual(FeatureDescription("mockFeatureDescription"))
        the(mockFeature.phase).shouldBeEqual(FeaturePhase.InProgress)
        the(mockFeature.tasks).shouldBeEqual(List(3) { index -> TaskId("mockTaskId$index") })
    }

    @Test
    fun Featureのプロパティの等価性の確認() {
        the(mockFeature.id).shouldBeEqual(FeatureId("mockUUID"))
        the(mockFeature.title).shouldBeEqual(FeatureTitle("mockFeatureTitle"))
        the(mockFeature.description).shouldBeEqual(FeatureDescription("mockFeatureDescription"))
    }

    // TODO
    @Test
    fun Featureはプロパティの値が同じであれば等価とみなされる() {
//        val otherTask = Feature.reconstruct(
//            id = mockFeature.id,
//            title = mockFeature.title,
//            description = mockFeature.description,
//            phase = mockFeature.phase,
//            tasks = mockFeature.tasks,
//        )
//        the(mockFeature).shouldBeEqual(otherTask)
    }

    @Test
    fun changePhaseを用いてPhaseが変わったFeatureを取得可能() {
        val copiedFeature = mockFeature.changePhase(FeaturePhase.Done)
        the(copiedFeature.phase).shouldBeEqual(FeaturePhase.Done)
    }
}

@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package feature

import io.mockk.every
import io.mockk.mockkStatic
import org.javalite.test.jspec.JSpec.the
import task.TaskId
import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test

class GenerateFeatureTest {

    @BeforeTest
    fun setup() {
        mockkStatic(UUID::class)
        val uuid = "mockUUID"
        every { UUID.randomUUID().toString() } returns uuid
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
}

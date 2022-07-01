@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package feature

import io.mockk.every
import io.mockk.mockkStatic
import org.javalite.test.jspec.JSpec.the
import project.ProjectId
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
        mockFeature = Feature(
            projectId = ProjectId("mockProjectId"),
            title = FeatureTitle("mockFeatureTitle"),
            description = FeatureDescription("mockFeatureDescription"),
            phase = FeaturePhase.Todo,
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
    fun Featureのプロパティの等価性の確認() {
        the(mockFeature.id).shouldBeEqual(FeatureId("mockUUID"))
        the(mockFeature.projectId).shouldBeEqual(ProjectId("mockProjectId"))
        the(mockFeature.title).shouldBeEqual(FeatureTitle("mockFeatureTitle"))
        the(mockFeature.description).shouldBeEqual(FeatureDescription("mockFeatureDescription"))
    }

    @Test
    fun Featureはプロパティの値が同じであれば等価とみなされる() {
        val otherTask = Feature(
            id = mockFeature.id,
            projectId = mockFeature.projectId,
            title = mockFeature.title,
            description = mockFeature.description,
            phase = mockFeature.phase,
        )
        the(mockFeature).shouldBeEqual(otherTask)
    }

    @Test
    fun copyWithPhaseを用いてPhaseが変わったFeatureを取得可能() {
        the(mockFeature.copyWithPhase(newPhase = FeaturePhase.Done).phase).shouldBeEqual(FeaturePhase.Done)
    }
}

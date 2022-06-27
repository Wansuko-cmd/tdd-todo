@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package feature

import io.mockk.every
import io.mockk.mockkStatic
import org.javalite.test.jspec.JSpec
import org.javalite.test.jspec.JSpec.a
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
        val uuid = "mockedUUID"
        every { UUID.randomUUID().toString() } returns uuid
        mockFeature = Feature(
            projectId = ProjectId("mockedProjectId"),
            title = FeatureTitle("mockFeatureTitle"),
            description = FeatureDescription("mockFeatureDescription"),
            phase = FeaturePhase.Todo,
        )
    }

    @Test
    fun Taskのプロパティを確認するテスト() {
        the(mockFeature.id).shouldBeEqual(FeatureId("mockedUUID"))
        the(mockFeature.projectId).shouldBeEqual(ProjectId("mockedProjectId"))
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
}

@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package project

import feature.Feature
import feature.FeatureId
import io.mockk.every
import io.mockk.mockkStatic
import org.javalite.test.jspec.JSpec.the
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class ProjectTest {
    @BeforeTest
    fun setup() {
        mockkStatic(UUID::class)
        val uuid = "mockUUID"
        every { UUID.randomUUID().toString() } returns uuid
    }

    @Test
    fun Projectを一から生成() {
        val mockProject = Project.create(
            title = ProjectTitle("mockProjectTitle"),
            description = ProjectDescription("mockProjectDescription"),
        )
        the(mockProject.id).shouldBeEqual(ProjectId("mockUUID"))
        the(mockProject.title).shouldBeEqual(ProjectTitle("mockProjectTitle"))
        the(mockProject.description).shouldBeEqual(ProjectDescription("mockProjectDescription"))
        the(mockProject.features).shouldBeEqual(listOf<FeatureId>())
    }

    @Test
    fun Projectを再生成() {
        val mockProject = Project.reconstruct(
            id = ProjectId("mockUUID"),
            title = ProjectTitle("mockProjectTitle"),
            description = ProjectDescription("mockProjectDescription"),
            features = List(3) { index -> FeatureId("mockFeatureId$index") }
        )
        the(mockProject.id).shouldBeEqual(ProjectId("mockUUID"))
        the(mockProject.title).shouldBeEqual(ProjectTitle("mockProjectTitle"))
        the(mockProject.description).shouldBeEqual(ProjectDescription("mockProjectDescription"))
        the(mockProject.features).shouldBeEqual(List(3) { index -> FeatureId("mockFeatureId$index") })
    }
}

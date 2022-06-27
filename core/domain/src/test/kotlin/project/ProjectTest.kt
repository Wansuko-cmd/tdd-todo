@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package project

import feature.ProjectId
import io.mockk.every
import io.mockk.mockkStatic
import org.javalite.test.jspec.JSpec.the
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class ProjectTest {
    private lateinit var mockProject: Project

    @BeforeTest
    fun setup() {
        mockkStatic(UUID::class)
        val uuid = "mockedUUID"
        every { UUID.randomUUID().toString() } returns uuid
        mockProject = Project(
            title = ProjectTitle("mockProjectTitle"),
            description = ProjectDescription("mockProjectDescription"),
        )
    }

    @Test
    fun Taskのプロパティを確認するテスト() {
        the(mockProject.id).shouldBeEqual(ProjectId("mockedUUID"))
        the(mockProject.title).shouldBeEqual(ProjectTitle("mockProjectTitle"))
        the(mockProject.description).shouldBeEqual(ProjectDescription("mockProjectDescription"))
    }
}

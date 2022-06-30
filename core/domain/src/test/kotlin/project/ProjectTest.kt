@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package project

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
        val uuid = "mockUUID"
        every { UUID.randomUUID().toString() } returns uuid
        mockProject = Project(
            title = ProjectTitle("mockProjectTitle"),
            description = ProjectDescription("mockProjectDescription"),
        )
    }

    @Test
    fun Projectのプロパティの等価性の確認() {
        the(mockProject.id).shouldBeEqual(ProjectId("mockUUID"))
        the(mockProject.title).shouldBeEqual(ProjectTitle("mockProjectTitle"))
        the(mockProject.description).shouldBeEqual(ProjectDescription("mockProjectDescription"))
    }
}

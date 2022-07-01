@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package project

import org.javalite.test.jspec.JSpec.the
import kotlin.test.Test

class ProjectEqualityTest {
    @Test
    fun ProjectはIdで等価性を判断() {
        val mockProject = Project.create(
            title = ProjectTitle("mockProjectTitle"),
            description = ProjectDescription("mockProjectDescription"),
        )
        val otherProject = Project.reconstruct(
            id = mockProject.id,
            title = ProjectTitle("otherProjectTitle"),
            description = ProjectDescription("otherProjectDescription"),
            features = listOf(),
        )
        the(mockProject).shouldBeEqual(otherProject)
    }
}

@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package project

import org.javalite.test.jspec.JSpec.the
import kotlin.test.Test

class ProjectEqualityTest {
    @Test
    fun ProjectはIdで等価性を判断() {
        val mockProject = Project.create(
            title = ProjectTitle("mockTitle"),
            description = ProjectDescription("mockDescription"),
        )
        val otherProject = Project.reconstruct(
            id = mockProject.id,
            title = ProjectTitle("otherTitle"),
            description = ProjectDescription("otherDescription"),
            features = listOf(),
        )
        the(mockProject).shouldBeEqual(otherProject)
    }
}

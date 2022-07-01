@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package project

import org.javalite.test.jspec.JSpec.the
import kotlin.test.Test

class ChangeProjectPropertyTest {

    private val mockProject = Project.create(
        title = ProjectTitle("mockProjectTitle"),
        description = ProjectDescription("mockProjectDescription"),
    )

    @Test
    fun ProjectのTitleを書き換えたコピーを作成() {
        val copiedProject = mockProject.changeTitle(ProjectTitle("copiedProjectTitle"))
        the(copiedProject.title).shouldBeEqual(ProjectTitle("copiedProjectTitle"))
    }

    @Test
    fun ProjectのDescriptionを書き換えたコピーを作成() {
        val copiedProject = mockProject.changeDescription(ProjectDescription("copiedProjectDescription"))
        the(copiedProject.description).shouldBeEqual(ProjectDescription("copiedProjectDescription"))
    }
}

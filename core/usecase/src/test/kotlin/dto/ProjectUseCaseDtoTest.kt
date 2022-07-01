@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package dto

import dto.project.ProjectUseCaseDto
import dto.project.ProjectUseCaseDto.Companion.toUseCaseDto
import org.javalite.test.jspec.JSpec.the
import project.Project
import project.ProjectDescription
import project.ProjectId
import project.ProjectTitle
import kotlin.test.Test

class ProjectUseCaseDtoTest {

    private val project = Project.create(
        title = ProjectTitle("mockTitle"),
        description = ProjectDescription("mockDescription"),
    )

    @Test
    fun Projectから変換可能() {
        val expected = ProjectUseCaseDto(
            id = project.id.value,
            title = project.title.value,
            description = project.description.value,
        )
        the(project.toUseCaseDto()).shouldBeEqual(expected)
    }
}

@file:Suppress("NonAsciiCharacters", "TestFunctionName")

import get.ProjectUseCaseModel
import get.ProjectUseCaseModel.Companion.toUseCaseModel
import org.javalite.test.jspec.JSpec.the
import project.Project
import project.ProjectDescription
import project.ProjectId
import project.ProjectTitle
import kotlin.test.Test

class ProjectUseCaseModelTest {

    private val project = Project(
        id = ProjectId("mockId"),
        title = ProjectTitle("mockTitle"),
        description = ProjectDescription("mockDescription"),
    )

    @Test
    fun Projectから変換可能() {
        val expected = ProjectUseCaseModel(
            id = project.id.value,
            title = project.title.value,
            description = project.description.value,
        )
        the(project.toUseCaseModel()).shouldBeEqual(expected)
    }
}

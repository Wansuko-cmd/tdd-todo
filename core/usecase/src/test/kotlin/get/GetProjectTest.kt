@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package get

import com.wsr.apiresult.ApiResult
import org.javalite.test.jspec.JSpec.the
import project.Project
import project.ProjectDescription
import project.ProjectId
import project.ProjectTitle
import kotlin.test.Test

class GetProjectTest {

    private val mockData = List(5) { index ->
        Project(
            id = ProjectId("mockId$index"),
            title = ProjectTitle("mockTitle$index"),
            description = ProjectDescription("mockDescription$index")
        )
    }

    @Test
    fun 全部プロジェクトを取得する() {
        val projects = GetProjectUseCase().getAll()
        the(projects).shouldBeEqual(ApiResult.Success(mockData))
    }
}

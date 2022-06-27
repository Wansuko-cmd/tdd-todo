package get

import com.wsr.apiresult.ApiResult
import project.Project
import project.ProjectDescription
import project.ProjectId
import project.ProjectTitle

class GetProjectUseCaseImpl {
    suspend fun getAll() = ApiResult.Success(List(5) { index ->
        Project(
            id = ProjectId("mockId$index"),
            title = ProjectTitle("mockTitle$index"),
            description = ProjectDescription("mockDescription$index")
        )
    })
}

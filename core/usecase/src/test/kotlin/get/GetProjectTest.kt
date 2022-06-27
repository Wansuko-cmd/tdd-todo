@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package get

import com.wsr.apiresult.ApiResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.javalite.test.jspec.JSpec.the
import project.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetProjectTest {

    private lateinit var target: GetProjectUseCase
    @MockK
    private lateinit var getProjectQueryService: GetProjectQueryService

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetProjectUseCaseImpl(getProjectQueryService)
    }

    private val mockData = List(5) { index ->
        Project(
            id = ProjectId("mockId$index"),
            title = ProjectTitle("mockTitle$index"),
            description = ProjectDescription("mockDescription$index")
        )
    }

    @Test
    fun 全部プロジェクトを取得する() = runBlocking {
        coEvery { getProjectQueryService.getAll() } returns ApiResult.Success(mockData)
        val projects = target.getAll()

        the(projects).shouldBeEqual(ApiResult.Success(mockData))

        coVerify(exactly = 1) { getProjectQueryService.getAll() }
        confirmVerified(getProjectQueryService)
    }
}

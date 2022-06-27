@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

import org.javalite.test.jspec.JSpec.*
import kotlin.test.Test

class TaskTest {

    private val mockTask = Task(
        id = TaskId("mockTaskId"),
        projectId = ProjectId("mockedProjectId"),
        title = TaskTitle("mockTitle"),
        description = TaskDescription("mockDescription"),
    )

    @Test
    fun Taskの等価性のテスト() {
        a(mockTask).shouldBeEqual(mockTask)
    }

    @Test
    fun Taskのプロパティを表すテスト() {
        the(mockTask.phase).shouldBeEqual(TaskPhase.Todo)
        the(mockTask.projectId).shouldBeEqual(ProjectId("mockedProjectId"))
    }
}

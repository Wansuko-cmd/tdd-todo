@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

import org.javalite.test.jspec.JSpec.*
import kotlin.test.Test

class TaskTest {

    private val mockTask = Task(
        id = TaskId("mockTaskId"),
        projectId = ProjectId("mockedProjectId"),
        title = TaskTitle("mockTaskTitle"),
        description = TaskDescription("mockTaskDescription"),
    )

    @Test
    fun Taskの等価性のテスト() {
        a(mockTask).shouldBeEqual(mockTask)
    }

    @Test
    fun Taskのプロパティを表すテスト() {
        the(mockTask.projectId).shouldBeEqual(ProjectId("mockedProjectId"))
        the(mockTask.title).shouldBeEqual(TaskTitle("mockTaskTitle"))
        the(mockTask.description).shouldBeEqual(TaskDescription("mockTaskDescription"))
        the(mockTask.phase).shouldBeEqual(TaskPhase.Todo)
    }
}

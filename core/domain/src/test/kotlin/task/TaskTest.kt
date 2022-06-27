@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

import org.javalite.test.jspec.JSpec.*
import kotlin.test.Test

class TaskTest {

    private val mockTask = Task(
        id = TaskId("mockTaskId"),
        featureId = FeatureId("mockedFeatureId"),
        title = TaskTitle("mockTaskTitle"),
        description = TaskDescription("mockTaskDescription"),
    )

    @Test
    fun Taskの等価性のテスト() {
        a(mockTask).shouldBeEqual(mockTask)
    }

    @Test
    fun Taskのプロパティを表すテスト() {
        the(mockTask.featureId).shouldBeEqual(FeatureId("mockedFeatureId"))
        the(mockTask.title).shouldBeEqual(TaskTitle("mockTaskTitle"))
        the(mockTask.description).shouldBeEqual(TaskDescription("mockTaskDescription"))
        the(mockTask.phase).shouldBeEqual(TaskPhase.Todo)
    }

    @Test
    fun TaskのPhaseを変更するテスト() {
        the(mockTask.copyWithPhase(newPhase = TaskPhase.Refactor).phase).shouldBeEqual(TaskPhase.Refactor)
    }
}

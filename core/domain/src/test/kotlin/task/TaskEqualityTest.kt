@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

import org.javalite.test.jspec.JSpec.*
import kotlin.test.Test


class TaskEqualityTest {
    @Test
    fun TaskはIdで等価性を判断() {
        val mockTask = Task.create(
            title = TaskTitle("mockTaskTitle"),
            description = TaskDescription("mockTaskDescription"),
        )
        val otherTask = Task.reconstruct(
            id = mockTask.id,
            title = TaskTitle("mockTaskTitle"),
            description = TaskDescription("mockTaskDescription"),
            phase = TaskPhase.Todo,
        )
        the(mockTask).shouldBeEqual(otherTask)
    }
}

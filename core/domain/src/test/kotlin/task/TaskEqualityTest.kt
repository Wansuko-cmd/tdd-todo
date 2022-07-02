@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

import org.javalite.test.jspec.JSpec.the
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
            title = TaskTitle("otherTaskTitle"),
            description = TaskDescription("otherTaskDescription"),
            phase = TaskPhase.Todo,
        )
        the(mockTask).shouldBeEqual(otherTask)
    }
}

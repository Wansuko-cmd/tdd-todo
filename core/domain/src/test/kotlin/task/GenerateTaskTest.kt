@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

import io.mockk.every
import io.mockk.mockkStatic
import org.javalite.test.jspec.JSpec.the
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class GenerateTaskTest {

    @BeforeTest
    fun setup() {
        mockkStatic(UUID::class)
        val uuid = "mockUUID"
        every { UUID.randomUUID().toString() } returns uuid
    }

    @Test
    fun Taskを一から作成() {
        val mockTask = Task.create(
            title = TaskTitle("mockTaskTitle"),
            description = TaskDescription("mockTaskDescription"),
        )

        the(mockTask.id).shouldBeEqual(TaskId("mockUUID"))
        the(mockTask.title).shouldBeEqual(TaskTitle("mockTaskTitle"))
        the(mockTask.description).shouldBeEqual(TaskDescription("mockTaskDescription"))
        the(mockTask.phase).shouldBeEqual(TaskPhase.Todo)
    }

    @Test
    fun Taskを再生成() {
        val mockTask = Task.reconstruct(
            id = TaskId("mockTaskId"),
            title = TaskTitle("mockTaskTitle"),
            description = TaskDescription("mockTaskDescription"),
            phase = TaskPhase.Red,
        )

        the(mockTask.id).shouldBeEqual(TaskId("mockTaskId"))
        the(mockTask.title).shouldBeEqual(TaskTitle("mockTaskTitle"))
        the(mockTask.description).shouldBeEqual(TaskDescription("mockTaskDescription"))
        the(mockTask.phase).shouldBeEqual(TaskPhase.Red)
    }
}

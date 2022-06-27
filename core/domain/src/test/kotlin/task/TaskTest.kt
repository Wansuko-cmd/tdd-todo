@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

import org.javalite.test.jspec.JSpec.*
import kotlin.test.Test

class TaskTest {
    @Test
    fun Taskの等価性のテスト() {
        val mockTask = Task(
            id = TaskId("mockId"),
            title = TaskTitle("mockTitle"),
            description = TaskDescription("mockDescription"),
        )
        a(mockTask).shouldBeEqual(mockTask)
    }
}

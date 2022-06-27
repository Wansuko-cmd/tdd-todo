@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

import org.javalite.test.jspec.JSpec.a
import kotlin.test.Test

class TaskTest {
    @Test
    fun Taskの等価性のテスト() {
        val task = Task(id = "1", title = "mockTitle", description = "mockDescription")
        a(task).shouldBeEqual(Task(id = "1", title = "", description = ""))
    }
}

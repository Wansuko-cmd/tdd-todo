@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package dto

import dto.task.TaskUseCaseDto
import dto.task.TaskUseCaseDto.Companion.toUseCaseDto
import feature.*
import org.javalite.test.jspec.JSpec.the
import org.junit.Test
import task.*

class TaskUseCaseDtoTest {

    private val task = Task.create(
        title = TaskTitle("mockTitle"),
        description = TaskDescription("mockDescription"),
    )

    @Test
    fun Featureから変換可能() {
        val expected = TaskUseCaseDto(
            id = task.id.value,
            title = task.title.value,
            description = task.description.value,
            phase = task.phase,
        )
        the(task.toUseCaseDto()).shouldBeEqual(expected)
    }
}

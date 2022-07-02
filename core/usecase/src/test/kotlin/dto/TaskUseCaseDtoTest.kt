@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package dto

import dto.task.TaskUseCaseDto
import dto.task.TaskUseCaseDto.Companion.toUseCaseDto
import org.javalite.test.jspec.JSpec.the
import org.junit.Test
import task.Task
import task.TaskDescription
import task.TaskTitle

class TaskUseCaseDtoTest {

    private val task = Task.create(
        title = TaskTitle("mockTitle"),
        description = TaskDescription("mockDescription"),
    )

    @Test
    fun Featureから変換可能() {
        val expected = TaskUseCaseDto(
            id = task.id,
            title = task.title,
            description = task.description,
            phase = task.phase,
        )
        the(task.toUseCaseDto()).shouldBeEqual(expected)
    }
}

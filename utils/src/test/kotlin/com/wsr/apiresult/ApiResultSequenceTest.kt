@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.apiresult

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class ApiResultSequenceTest {
    @Test
    fun listの中身の型が全てSuccessの時はSuccess型を返す() {
        val mockedList = List(5) { ApiResult.Success(it) }
        val actual = mockedList.sequence()
        val expected = ApiResult.Success(List(5) { it })
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun listの中身にFailureがあるときはFailure型を返す() {
        val mockedList = List(5) { ApiResult.Success(it) } + ApiResult.Failure("Failure")
        val actual = mockedList.sequence()
        val expected = ApiResult.Failure("Failure")
        assertThat(actual).isEqualTo(expected)
    }
}

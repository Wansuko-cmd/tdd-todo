@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.apiresult

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test
import kotlin.test.fail

class ApiResultMapTest {

    /*** mapBoth関数 ***/
    @Test
    fun 型がSuccessの時はラムダ式を実行して結果を返す() {
        val mockedApiResult = ApiResult.Success("mockedSuccess")
        val updatedMockedApiResult = mockedApiResult.map { "updatedSuccess" }

        val expected = ApiResult.Success("updatedSuccess")

        assertThat(updatedMockedApiResult).isEqualTo(expected)
    }

    @Test
    fun 型がFailureの時はそのままの結果を返す() {
        val mockedApiResult = ApiResult.Failure("mockedFailure")
        val updatedMockedApiResult = mockedApiResult.map { fail("mapの中身を実行") }

        val expected = ApiResult.Failure("mockedFailure")

        assertThat(updatedMockedApiResult).isEqualTo(expected)
    }
}

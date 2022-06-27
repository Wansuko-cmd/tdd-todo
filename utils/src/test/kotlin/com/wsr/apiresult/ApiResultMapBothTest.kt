@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.apiresult

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test
import kotlin.test.fail

class ApiResultMapBothTest {

    /*** mapBoth関数 ***/
    @Test
    fun 型がSuccessの時はsuccessを実行して結果を返す() {
        val mockedApiResult = ApiResult.Success("mockedSuccess")
        val updatedMockedApiResult = mockedApiResult.mapBoth(
            success = { "updatedSuccess" },
            failure = { fail("failureの方を実行") },
        )

        val expected = ApiResult.Success("updatedSuccess")

        assertThat(updatedMockedApiResult).isEqualTo(expected)
    }

    @Test
    fun 型がFailureの時はfailureを実行して結果を返す() {
        val mockedApiResult = ApiResult.Failure("mockedFailure")
        val updatedMockedApiResult = mockedApiResult.mapBoth(
            success = { fail("successの方を実行") },
            failure = { "updatedFailure" },
        )

        val expected = ApiResult.Failure("updatedFailure")

        assertThat(updatedMockedApiResult).isEqualTo(expected)
    }
}

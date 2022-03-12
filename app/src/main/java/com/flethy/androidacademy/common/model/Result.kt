package com.flethy.androidacademy.common.model

sealed class Result {
    internal class Success: Result()
    internal class Failure: Result()
}

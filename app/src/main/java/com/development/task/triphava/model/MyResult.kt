package com.development.task.triphava.model


data class MyResult<out T>(val status: Status, val data: T?, val message: String?) {

    var exception: Exception? = null

    constructor(status: Status, data: T?, message: String? = null, exception: Exception?) : this(
        status,
        data,
        message ?: ""
    ) {
        this.exception = exception
    }

    companion object {
        fun <T> success(data: T?): MyResult<T> {
            return MyResult(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null): MyResult<T> {
            return MyResult(Status.ERROR, data, msg)
        }

        fun <T> error(exception: Exception, data: T? = null): MyResult<T> {
            return MyResult(Status.ERROR, data, exception = exception)
        }

        fun <T> loading(): MyResult<T> {
            return MyResult(Status.LOADING, null, null)
        }
    }
}

enum class Status {
    LOADING,
    ERROR,
    SUCCESS
}

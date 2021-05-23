package com.namnguyen.data.util

import com.namnguyen.data.executor.PostExecutionThread
import com.namnguyen.domain.base.BaseResponse
import com.namnguyen.domain.base.BaseResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
fun <T> flowRes(
    postExecutionThread: PostExecutionThread,
    block: suspend () -> BaseResponse<T>
) = flow { emit(block.invoke()) }
    .map { response ->
        when (response.status) {
            200 -> BaseResult.Success(response.status, response.message, response.data)
            else -> BaseResult.Failed(
                response.status,
                response.message,
                response.exception,
                response.data
            )
        }
    }
    .catch { cause ->
        emit(BaseResult.Error(cause))
    }
    .flowOn(postExecutionThread.io)
    .onStart { emit(BaseResult.Loading) }

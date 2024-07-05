package com.example.todoapp.data.remote

import com.example.todoapp.data.remote.dto.GetTodoListResponse
import retrofit2.Response
import retrofit2.http.GET

interface TodoListApi {

    @GET("list")
    suspend fun getTodoList(): Response<GetTodoListResponse>
}
package com.example.todoapp.data.remote

import com.example.todoapp.data.remote.dto.GetTodoItemResponse
import com.example.todoapp.data.remote.dto.GetTodoListResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoItemApi {

    @GET("list")
    suspend fun getTodoList(): Response<GetTodoListResponse>

    @PATCH("list")
    suspend fun updateTodoList(): Response<GetTodoItemResponse>

    @GET("list/{id}")
    suspend fun getTodoItem(@Path("id") id: String): Response<GetTodoItemResponse>

    @POST("list")
    suspend fun addTodoItem(): Response<GetTodoListResponse>

    @PUT("list/{id}")
    suspend fun updateTodoItem(): Response<GetTodoListResponse>

    @DELETE("list/{id}")
    suspend fun deleteTodoItem(): Response<ResponseBody>
}
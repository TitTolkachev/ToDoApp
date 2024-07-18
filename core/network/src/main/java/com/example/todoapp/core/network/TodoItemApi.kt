package com.example.todoapp.core.network

import com.example.todoapp.core.network.dto.AddTodoItemRequest
import com.example.todoapp.core.network.dto.AddTodoItemResponse
import com.example.todoapp.core.network.dto.DeleteTodoItemResponse
import com.example.todoapp.core.network.dto.GetTodoItemResponse
import com.example.todoapp.core.network.dto.GetTodoListResponse
import com.example.todoapp.core.network.dto.UpdateTodoItemRequest
import com.example.todoapp.core.network.dto.UpdateTodoItemResponse
import com.example.todoapp.core.network.dto.UpdateTodoListRequest
import com.example.todoapp.core.network.dto.UpdateTodoListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * API для работы с задачами.
 */
interface TodoItemApi {

    @GET("list")
    suspend fun getTodoList(): Response<GetTodoListResponse>

    @PATCH("list")
    suspend fun updateTodoList(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body body: UpdateTodoListRequest
    ): Response<UpdateTodoListResponse>

    @GET("list/{id}")
    suspend fun getTodoItem(@Path("id") id: String): Response<GetTodoItemResponse>

    @POST("list")
    suspend fun addTodoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body body: AddTodoItemRequest
    ): Response<AddTodoItemResponse>

    @PUT("list/{id}")
    suspend fun updateTodoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String,
        @Body body: UpdateTodoItemRequest,
    ): Response<UpdateTodoItemResponse>

    @DELETE("list/{id}")
    suspend fun deleteTodoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String,
    ): Response<DeleteTodoItemResponse>
}
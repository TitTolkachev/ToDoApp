package com.example.todoapp.data.repository

import com.example.todoapp.data.model.Importance
import com.example.todoapp.data.model.Importance.MEDIUM
import com.example.todoapp.data.model.TodoItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import java.util.UUID

class TodoItemsRepository {

    private val _items = MutableStateFlow(getTestData())
    val items: Flow<List<TodoItem>> = _items

    suspend fun createItem(item: TodoItem) {
        delay(100L)
        _items.update { list -> list.plus(item.copy(id = UUID.randomUUID().toString())) }
    }

    suspend fun updateItem(item: TodoItem) {
        delay(100L)
        _items.update { list -> list.map { if (it.id == item.id) item else it } }
    }

    suspend fun deleteItem(todoItemId: String) {
        delay(100L)
        _items.update { list -> list.filterNot { it.id == todoItemId } }
    }

    private fun getTestData(): List<TodoItem> {
        val currentTime = Date()
        return listOf(
            TodoItem("1", "Buy groceries", MEDIUM, Date(currentTime.time + 86400000), false, Date(currentTime.time - 86400000), null),
            TodoItem("2", "Finish project report. Ensure that all sections are thoroughly reviewed and that all references are properly cited. The deadline is approaching, so prioritize this task.", Importance.HIGH, Date(currentTime.time + 172800000), false, Date(currentTime.time - 172800000), currentTime),
            TodoItem("3", "Call Alice to catch up and discuss the upcoming weekend plans. It’s been a while since we last spoke.", Importance.LOW, null, true, Date(currentTime.time - 259200000), null),
            TodoItem("4", "Schedule dentist appointment for the next available slot. Make sure it doesn't clash with other commitments.", MEDIUM, Date(currentTime.time + 432000000), false, Date(currentTime.time - 432000000), null),
            TodoItem("5", "Plan weekend trip. Research destinations, book accommodations, and prepare an itinerary.", Importance.LOW, null, false, Date(currentTime.time - 518400000), Date(currentTime.time - 432000000)),
            TodoItem("6", "Read a book. Find some quiet time in the evening to relax and unwind.", Importance.LOW, null, false, Date(currentTime.time - 604800000), null),
            TodoItem("7", "Pay electricity bill before the due date to avoid late fees. Double-check the amount due.", Importance.HIGH, Date(currentTime.time + 259200000), false, Date(currentTime.time - 691200000), null),
            TodoItem("8", "Organize office space. Declutter the desk, file important documents, and discard unnecessary items.", MEDIUM, null, true, Date(currentTime.time - 777600000), null),
            TodoItem("9", "Workout at the gym. Follow the new exercise routine and track progress.", MEDIUM, null, false, Date(currentTime.time - 864000000), Date(currentTime.time - 86400000)),
            TodoItem("10", "Meditation. Spend 20 minutes in the morning to meditate and focus on breathing exercises.", Importance.LOW, null, false, Date(currentTime.time - 950400000), null),
            TodoItem("11", "Grocery shopping. Buy all the ingredients needed for the week’s meal plan.", MEDIUM, Date(currentTime.time + 86400000), true, Date(currentTime.time - 1036800000), currentTime),
            TodoItem("12", "Fix the sink in the kitchen. The leak needs to be addressed before it causes more damage.", Importance.HIGH, Date(currentTime.time + 432000000), false, Date(currentTime.time - 1123200000), null),
            TodoItem("13", "Prepare presentation for the upcoming meeting. Ensure all slides are updated with the latest information.", Importance.HIGH, Date(currentTime.time + 259200000), false, Date(currentTime.time - 1209600000), currentTime),
            TodoItem("14", "Visit grandma this weekend. Spend some quality time and help with any chores she might need.", Importance.LOW, null, true, Date(currentTime.time - 1296000000), null),
            TodoItem("15", "Write a blog post about the recent travel experiences. Include photos and tips for future travelers.", MEDIUM, Date(currentTime.time + 604800000), false, Date(currentTime.time - 1382400000), null),
            TodoItem("16", "Clean the garage. Sort through old items and organize the tools.", Importance.LOW, null, false, Date(currentTime.time - 1468800000), Date(currentTime.time - 1209600000)),
            TodoItem("17", "Attend conference on new technology trends. Network with other professionals and take notes during sessions.", Importance.HIGH, Date(currentTime.time + 864000000), false, Date(currentTime.time - 1555200000), null),
            TodoItem("18", "Fix the bike. Replace the broken chain and check the brakes.", MEDIUM, Date(currentTime.time + 172800000), true, Date(currentTime.time - 1641600000), null),
            TodoItem("19", "Donate clothes to the local shelter. Gather all unused items and drop them off.", Importance.LOW, null, false, Date(currentTime.time - 1728000000), null),
            TodoItem("20", "Gardening. Plant new flowers and trim the hedges in the backyard.", Importance.LOW, null, false, Date(currentTime.time - 1814400000), Date(currentTime.time - 1296000000)),
            TodoItem("21", "Learn Kotlin programming. Complete the online course and practice by building small projects.", Importance.HIGH, Date(currentTime.time + 259200000), false, Date(currentTime.time - 1900800000), null),
            TodoItem("22", "Family dinner on Friday. Plan the menu and make sure all ingredients are available.", MEDIUM, Date(currentTime.time + 432000000), false, Date(currentTime.time - 1987200000), null),
            TodoItem("23", "Walk the dog in the evening. Take a longer route through the park.", Importance.LOW, null, true, Date(currentTime.time - 2073600000), null),
            TodoItem("24", "Buy birthday gift for John. Consider his hobbies and preferences while choosing the gift.", Importance.HIGH, Date(currentTime.time + 604800000), false, Date(currentTime.time - 2160000000), currentTime),
            TodoItem("25", "Visit the museum over the weekend. Check for any special exhibits or events.", Importance.LOW, null, false, Date(currentTime.time - 2246400000), null),
            TodoItem("26", "Take online course on data science. Dedicate time each day to complete the modules.", MEDIUM, Date(currentTime.time + 864000000), false, Date(currentTime.time - 2332800000), null),
            TodoItem("27", "Repair the fence in the backyard. Replace the broken sections and apply a new coat of paint.", MEDIUM, Date(currentTime.time + 432000000), false, Date(currentTime.time - 2419200000), null),
            TodoItem("28", "Host a party this weekend. Invite friends, prepare snacks, and arrange some games.", Importance.LOW, null, true, Date(currentTime.time - 2505600000), null),
            TodoItem("29", "Update resume with recent job experiences and skills. Tailor it for the upcoming job applications.", Importance.HIGH, Date(currentTime.time + 259200000), false, Date(currentTime.time - 2592000000), null),
            TodoItem("30", "Volunteer work at the community center. Help with organizing events and activities.", Importance.LOW, null, false, Date(currentTime.time - 2678400000), Date(currentTime.time - 2332800000))
        )
    }
}
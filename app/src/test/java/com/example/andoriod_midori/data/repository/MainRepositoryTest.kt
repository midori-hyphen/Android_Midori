package com.example.andoriod_midori.data.repository

import com.example.andoriod_midori.data.models.WidgetData
import com.example.andoriod_midori.domain.common.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MainRepositoryTest {

    private lateinit var repository: MainRepositoryImpl

    @Before
    fun setup() {
        repository = MainRepositoryImpl()
    }

    @Test
    fun initialWidgetDataLoadsCorrectly() = runTest {
        val result = repository.getWidgets().first()
        
        assertTrue(result is Result.Success)
        val widgets = (result as Result.Success).data
        assertEquals(3, widgets.size)
        assertTrue(widgets.any { it.type == WidgetData.WidgetType.MUSIC_BIG })
        assertTrue(widgets.any { it.type == WidgetData.WidgetType.MEAL })
        assertTrue(widgets.any { it.type == WidgetData.WidgetType.ANNOUNCEMENT_SMALL })
    }

    @Test
    fun widgetAdditionWorksCorrectly() = runTest {
        val newWidget = WidgetData.Music(
            id = "test_widget_1",
            type = WidgetData.WidgetType.MUSIC_SMALL
        )
        
        val addResult = repository.addWidget(newWidget)
        assertTrue(addResult is Result.Success)
        
        val allWidgets = repository.getWidgets().first()
        assertTrue(allWidgets is Result.Success)
        val widgets = (allWidgets as Result.Success).data
        assertEquals(4, widgets.size)
        assertTrue(widgets.any { it.id == "test_widget_1" })
    }

    @Test
    fun duplicateWidgetAdditionReturnsError() = runTest {
        val widget1 = WidgetData.Music(
            id = "duplicate_id",
            type = WidgetData.WidgetType.MUSIC_BIG
        )
        
        repository.addWidget(widget1)
        val duplicateResult = repository.addWidget(widget1)
        
        assertTrue(duplicateResult is Result.Error)
    }

    @Test
    fun widgetDeletionWorksCorrectly() = runTest {
        val initialWidgets = repository.getWidgets().first()
        val widgetCount = (initialWidgets as Result.Success).data.size
        
        val widgetToDelete = (initialWidgets as Result.Success).data.first()
        val deleteResult = repository.deleteWidget(widgetToDelete.id)
        
        assertTrue(deleteResult is Result.Success)
        
        val afterDelete = repository.getWidgets().first()
        val newWidgetCount = (afterDelete as Result.Success).data.size
        assertEquals(widgetCount - 1, newWidgetCount)
    }

    @Test
    fun nonExistentWidgetDeletionReturnsError() = runTest {
        val deleteResult = repository.deleteWidget("non_existent_id")
        assertTrue(deleteResult is Result.Error)
    }

    @Test
    fun widgetReorderingWorksCorrectly() = runTest {
        val currentWidgets = repository.getWidgets().first()
        val widgets = (currentWidgets as Result.Success).data.toMutableList()
        
        widgets.reverse()
        
        val reorderResult = repository.reorderWidgets(widgets)
        assertTrue(reorderResult is Result.Success)
        
        val afterReorder = repository.getWidgets().first()
        val newOrder = (afterReorder as Result.Success).data
        assertEquals(widgets, newOrder)
    }

    @Test
    fun widgetUpdateWorksCorrectly() = runTest {
        val currentWidgets = repository.getWidgets().first()
        val widget = (currentWidgets as Result.Success).data.first()
        
        val updatedWidget = when (widget) {
            is WidgetData.Music -> widget.copy(id = widget.id + "_updated")
            is WidgetData.Meal -> widget.copy(id = widget.id + "_updated")  
            is WidgetData.Announcement -> widget.copy(id = widget.id + "_updated")
        }
        
        val updateResult = repository.updateWidget(updatedWidget)
        assertTrue(updateResult is Result.Success)
    }

    @Test
    fun nonExistentWidgetUpdateReturnsError() = runTest {
        val nonExistentWidget = WidgetData.Music(
            id = "non_existent",
            type = WidgetData.WidgetType.MUSIC_BIG
        )
        
        val updateResult = repository.updateWidget(nonExistentWidget)
        assertTrue(updateResult is Result.Error)
    }
} 
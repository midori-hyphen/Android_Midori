package com.example.andoriod_midori.domain.usecase

import com.example.andoriod_midori.data.models.WidgetData
import com.example.andoriod_midori.data.repository.MainRepository
import com.example.andoriod_midori.domain.common.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class GetWidgetsUseCaseTest {

    @Mock
    private lateinit var repository: MainRepository

    private lateinit var useCase: GetWidgetsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = GetWidgetsUseCase(repository)
    }

    @Test
    fun `invoke should return success when repository returns success`() = runTest {
        val mockWidgets = listOf(
            WidgetData.Music(
                id = "music_1",
                type = WidgetData.WidgetType.MUSIC_BIG
            ),
            WidgetData.Meal(
                id = "meal_1", 
                type = WidgetData.WidgetType.MEAL
            )
        )
        val successResult = Result.Success(mockWidgets)
        whenever(repository.getWidgets()).thenReturn(flowOf(successResult))

        val result = useCase().first()

        assertTrue(result is Result.Success)
        assertEquals(mockWidgets, (result as Result.Success).data)
    }

    @Test
    fun `invoke should return error when repository returns error`() = runTest {
        val errorResult = Result.Error(Exception("Test error"))
        whenever(repository.getWidgets()).thenReturn(flowOf(errorResult))

        val result = useCase().first()

        assertTrue(result is Result.Error)
        assertEquals("Test error", (result as Result.Error).message)
    }
} 
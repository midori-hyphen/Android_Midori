package com.example.andoriod_midori.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.andoriod_midori.data.models.WidgetData
import com.example.andoriod_midori.data.ui.MainUiAction
import com.example.andoriod_midori.data.ui.UiState
import com.example.andoriod_midori.domain.common.Result
import com.example.andoriod_midori.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var getUserInfoUseCase: GetUserInfoUseCase
    
    @Mock
    private lateinit var getWidgetsUseCase: GetWidgetsUseCase
    
    @Mock
    private lateinit var updateWidgetUseCase: UpdateWidgetUseCase
    
    @Mock
    private lateinit var reorderWidgetsUseCase: ReorderWidgetsUseCase
    
    @Mock
    private lateinit var addWidgetUseCase: AddWidgetUseCase
    
    @Mock
    private lateinit var deleteWidgetUseCase: DeleteWidgetUseCase

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialStateIsCorrectlySet() {
        whenever(getUserInfoUseCase()).thenReturn(flowOf(Result.Loading))
        whenever(getWidgetsUseCase()).thenReturn(flowOf(Result.Loading))

        viewModel = MainViewModel(
            getUserInfoUseCase,
            getWidgetsUseCase,
            updateWidgetUseCase,
            reorderWidgetsUseCase,
            addWidgetUseCase,
            deleteWidgetUseCase
        )

        val initialState = viewModel.uiState.value
        assertNotNull(initialState)
        assertEquals(false, initialState.isEditMode)
        assertEquals(false, initialState.showWidgetPicker)
    }

    @Test
    fun editModeToggleWorksCorrectly() = runTest {
        whenever(getUserInfoUseCase()).thenReturn(flowOf(Result.Loading))
        whenever(getWidgetsUseCase()).thenReturn(flowOf(Result.Loading))

        viewModel = MainViewModel(
            getUserInfoUseCase,
            getWidgetsUseCase,
            updateWidgetUseCase,
            reorderWidgetsUseCase,
            addWidgetUseCase,
            deleteWidgetUseCase
        )

        viewModel.onAction(MainUiAction.ToggleEditMode)
        
        assertTrue(viewModel.uiState.value.isEditMode)
        
        viewModel.onAction(MainUiAction.ToggleEditMode)
        
        assertFalse(viewModel.uiState.value.isEditMode)
    }

    @Test
    fun widgetAdditionUpdatesStateOnSuccess() = runTest {
        val mockWidget = WidgetData.Music(
            id = "test_music_1",
            type = WidgetData.WidgetType.MUSIC_BIG
        )
        
        whenever(getUserInfoUseCase()).thenReturn(flowOf(Result.Loading))
        whenever(getWidgetsUseCase()).thenReturn(flowOf(Result.Success(emptyList())))
        whenever(addWidgetUseCase(mockWidget)).thenReturn(Result.Success(Unit))

        viewModel = MainViewModel(
            getUserInfoUseCase,
            getWidgetsUseCase,
            updateWidgetUseCase,
            reorderWidgetsUseCase,
            addWidgetUseCase,
            deleteWidgetUseCase
        )

        viewModel.onAction(MainUiAction.AddWidget(WidgetData.WidgetType.MUSIC_BIG))

        assertTrue(viewModel.uiState.value.widgetOperationState is UiState.Success)
    }

    @Test
    fun errorClearActionWorksCorrectly() = runTest {
        whenever(getUserInfoUseCase()).thenReturn(flowOf(Result.Loading))
        whenever(getWidgetsUseCase()).thenReturn(flowOf(Result.Loading))

        viewModel = MainViewModel(
            getUserInfoUseCase,
            getWidgetsUseCase,
            updateWidgetUseCase,
            reorderWidgetsUseCase,
            addWidgetUseCase,
            deleteWidgetUseCase
        )

        viewModel.onAction(MainUiAction.ClearError)

        assertTrue(viewModel.uiState.value.loadingState is UiState.Idle)
    }
} 
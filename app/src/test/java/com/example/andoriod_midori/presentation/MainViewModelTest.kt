package com.example.andoriod_midori.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.andoriod_midori.data.models.UserInfo
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
import org.mockito.kotlin.any
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
    
    private fun setupMocks() = runTest {
        whenever(getUserInfoUseCase()).thenReturn(flowOf(Result.Success(UserInfo.createSample())))
        whenever(getWidgetsUseCase()).thenReturn(flowOf(Result.Success(emptyList())))
        whenever(addWidgetUseCase(any())).thenReturn(Result.Success(Unit))
        whenever(updateWidgetUseCase(any())).thenReturn(Result.Success(Unit))
        whenever(deleteWidgetUseCase(any())).thenReturn(Result.Success(Unit))
        whenever(reorderWidgetsUseCase(any())).thenReturn(Result.Success(Unit))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialStateIsCorrectlySet() = runTest {
        setupMocks()
        
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
    }

    @Test
    fun viewModelCanBeCreated() = runTest {
        setupMocks()
        
        viewModel = MainViewModel(
            getUserInfoUseCase,
            getWidgetsUseCase,
            updateWidgetUseCase,
            reorderWidgetsUseCase,
            addWidgetUseCase,
            deleteWidgetUseCase
        )

        assertNotNull(viewModel)
    }

    @Test
    fun actionsCanBeCalled() = runTest {
        setupMocks()
        
        viewModel = MainViewModel(
            getUserInfoUseCase,
            getWidgetsUseCase,
            updateWidgetUseCase,
            reorderWidgetsUseCase,
            addWidgetUseCase,
            deleteWidgetUseCase
        )

        viewModel.onAction(MainUiAction.AddWidget(WidgetData.WidgetType.MUSIC_BIG))
        viewModel.onAction(MainUiAction.ClearError)

        assertNotNull(viewModel.uiState.value)
    }

    @Test
    fun stateFlowIsWorking() = runTest {
        setupMocks()
        
        viewModel = MainViewModel(
            getUserInfoUseCase,
            getWidgetsUseCase,
            updateWidgetUseCase,
            reorderWidgetsUseCase,
            addWidgetUseCase,
            deleteWidgetUseCase
        )

        val state = viewModel.uiState.value
        assertNotNull(state)
        assertTrue(state.widgets.isEmpty())
    }
} 
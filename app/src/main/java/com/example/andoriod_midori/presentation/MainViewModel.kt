package com.example.andoriod_midori.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andoriod_midori.data.models.WidgetData
import com.example.andoriod_midori.data.ui.MainUiAction
import com.example.andoriod_midori.data.ui.MainUiState
import com.example.andoriod_midori.data.ui.UiState
import com.example.andoriod_midori.data.ui.MidoriError
import com.example.andoriod_midori.data.ui.isError
import com.example.andoriod_midori.domain.common.Result
import com.example.andoriod_midori.domain.usecase.GetUserInfoUseCase
import com.example.andoriod_midori.domain.usecase.GetWidgetsUseCase
import com.example.andoriod_midori.domain.usecase.UpdateWidgetUseCase
import com.example.andoriod_midori.domain.usecase.ReorderWidgetsUseCase
import com.example.andoriod_midori.domain.usecase.AddWidgetUseCase
import com.example.andoriod_midori.domain.usecase.DeleteWidgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getWidgetsUseCase: GetWidgetsUseCase,
    private val updateWidgetUseCase: UpdateWidgetUseCase,
    private val reorderWidgetsUseCase: ReorderWidgetsUseCase,
    private val addWidgetUseCase: AddWidgetUseCase,
    private val deleteWidgetUseCase: DeleteWidgetUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState.initial())
    
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainUiState.initial()
    )

    init {
        Timber.d("MainViewModel initialized")
        loadInitialData()
    }

    fun onAction(action: MainUiAction) {
        Timber.d("Handling action: ${action::class.simpleName}")
        
        when (action) {
            MainUiAction.RefreshData -> loadInitialData()
            MainUiAction.ClearError -> clearError()
            
            is MainUiAction.NavigateTo -> {
                _uiState.update { it.copy(currentNavigation = action.item) }
            }
            
            MainUiAction.ToggleEditMode -> {
                _uiState.update { it.copy(isEditMode = !it.isEditMode) }
            }
            
            MainUiAction.ShowWidgetPicker -> {
                _uiState.update { it.copy(showWidgetPicker = true) }
            }
            
            MainUiAction.HideWidgetPicker -> {
                _uiState.update { it.copy(showWidgetPicker = false) }
            }
            
            is MainUiAction.AddWidget -> {
                addWidget(action.widgetType)
            }
            
            is MainUiAction.UpdateWidget -> {
                updateWidget(action.widget)
            }
            
            is MainUiAction.DeleteWidget -> {
                deleteWidget(action.widgetId)
            }
            
            is MainUiAction.ReorderWidgets -> {
                reorderWidgets(action.widgets)
            }
            
            else -> {
                Timber.w("Unhandled action: ${action::class.simpleName}")
            }
        }
    }
    
    private fun clearError() {
        _uiState.update { 
            it.copy(
                loadingState = if (it.loadingState.isError()) UiState.Idle else it.loadingState,
                widgetOperationState = if (it.widgetOperationState.isError()) UiState.Idle else it.widgetOperationState
            )
        }
    }

    private fun loadInitialData() {
        Timber.d("Loading initial data")
        
        viewModelScope.launch {
            _uiState.update { it.copy(loadingState = UiState.Loading) }
            
            try {
                val (userInfoResult, widgetsResult) = combine(
                    getUserInfoUseCase(),
                    getWidgetsUseCase()
                ) { userInfoResult, widgetsResult ->
                    userInfoResult to widgetsResult
                }.first()
                
                handleDataLoadingResults(userInfoResult, widgetsResult)
            } catch (e: Exception) {
                Timber.e(e, "Failed to load initial data")
                _uiState.update { 
                    it.copy(
                        loadingState = UiState.Error(
                            MidoriError.AppError("Failed to load app data")
                        )
                    )
                }
            }
        }
    }
    
    private fun handleDataLoadingResults(
        userInfoResult: Result<com.example.andoriod_midori.data.models.UserInfo>,
        widgetsResult: Result<List<WidgetData>>
    ) {
        val userInfo = userInfoResult.getOrNull()
        val widgets = widgetsResult.getOrNull()
        
        when {
            userInfoResult is Result.Error -> {
                Timber.e("Failed to load user info: ${userInfoResult.message}")
                _uiState.update { 
                    it.copy(loadingState = UiState.Error(userInfoResult.exception))
                }
            }
            
            widgetsResult is Result.Error -> {
                Timber.e("Failed to load widgets: ${widgetsResult.message}")
                _uiState.update { 
                    it.copy(loadingState = UiState.Error(widgetsResult.exception))
                }
            }
            
            userInfo != null && widgets != null -> {
                Timber.d("Successfully loaded initial data: ${widgets.size} widgets")
                _uiState.update { currentState ->
                    currentState.copy(
                        userInfo = userInfo,
                        widgets = widgets,
                        loadingState = UiState.Success(Unit)
                    )
                }
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        Timber.d("MainViewModel cleared")
    }

    private fun addWidget(widgetType: WidgetData.WidgetType) {
        Timber.d("Adding widget of type: $widgetType")
        
        viewModelScope.launch {
            _uiState.update { it.copy(widgetOperationState = UiState.Loading) }
            
            val newWidget = createWidget(widgetType)
            val result = addWidgetUseCase(newWidget)
            
            when (result) {
                is Result.Success -> {
                    Timber.d("Successfully added widget: ${newWidget.id}")
                    _uiState.update { currentState ->
                        currentState.copy(
                            widgets = currentState.widgets + newWidget,
                            widgetOperationState = UiState.Success(Unit),
                            showWidgetPicker = false
                        )
                    }
                }
                is Result.Error -> {
                    Timber.e("Failed to add widget: ${result.message}")
                    _uiState.update { 
                        it.copy(widgetOperationState = UiState.Error(result.exception))
                    }
                }
                is Result.Loading -> {
                }
            }
        }
    }
    
    private fun updateWidget(widget: WidgetData) {
        Timber.d("Updating widget: ${widget.id}")
        
        viewModelScope.launch {
            _uiState.update { it.copy(widgetOperationState = UiState.Loading) }
            
            val result = updateWidgetUseCase(widget)
            
            when (result) {
                is Result.Success -> {
                    Timber.d("Successfully updated widget: ${widget.id}")
                    _uiState.update { currentState ->
                        currentState.copy(
                            widgets = currentState.widgets.map { 
                                if (it.id == widget.id) widget else it 
                            },
                            widgetOperationState = UiState.Success(Unit)
                        )
                    }
                }
                is Result.Error -> {
                    Timber.e("Failed to update widget: ${result.message}")
                    _uiState.update { 
                        it.copy(widgetOperationState = UiState.Error(result.exception))
                    }
                }
                is Result.Loading -> {
                }
            }
        }
    }
    
    private fun deleteWidget(widgetId: String) {
        Timber.d("Deleting widget: $widgetId")
        
        viewModelScope.launch {
            _uiState.update { it.copy(widgetOperationState = UiState.Loading) }
            
            val result = deleteWidgetUseCase(widgetId)
            
            when (result) {
                is Result.Success -> {
                    Timber.d("Successfully deleted widget: $widgetId")
                    _uiState.update { currentState ->
                        currentState.copy(
                            widgets = currentState.widgets.filter { it.id != widgetId },
                            widgetOperationState = UiState.Success(Unit)
                        )
                    }
                }
                is Result.Error -> {
                    Timber.e("Failed to delete widget: ${result.message}")
                    _uiState.update { 
                        it.copy(widgetOperationState = UiState.Error(result.exception))
                    }
                }
                is Result.Loading -> {
                }
            }
        }
    }
    
    private fun reorderWidgets(widgets: List<WidgetData>) {
        Timber.d("Reordering ${widgets.size} widgets")
        
        viewModelScope.launch {
            val result = reorderWidgetsUseCase(widgets)
            
            when (result) {
                is Result.Success -> {
                    Timber.d("Successfully reordered widgets")
                    _uiState.update { it.copy(widgets = widgets) }
                }
                is Result.Error -> {
                    Timber.e("Failed to reorder widgets: ${result.message}")
                    _uiState.update { 
                        it.copy(widgetOperationState = UiState.Error(result.exception))
                    }
                }
                is Result.Loading -> {
                }
            }
        }
    }
    
    private fun createWidget(widgetType: WidgetData.WidgetType): WidgetData {
        return when (widgetType) {
            WidgetData.WidgetType.MUSIC_BIG -> WidgetData.Music(
                id = "music_big_${System.currentTimeMillis()}",
                type = widgetType
            )
            WidgetData.WidgetType.MUSIC_SMALL -> WidgetData.Music(
                id = "music_small_${System.currentTimeMillis()}",
                type = widgetType
            )
            WidgetData.WidgetType.MEAL -> WidgetData.Meal(
                id = "meal_${System.currentTimeMillis()}",
                type = widgetType
            )
            WidgetData.WidgetType.ANNOUNCEMENT_SMALL -> WidgetData.Announcement(
                id = "announcement_small_${System.currentTimeMillis()}",
                type = widgetType
            )
            WidgetData.WidgetType.ANNOUNCEMENT_BIG -> WidgetData.Announcement(
                id = "announcement_big_${System.currentTimeMillis()}",
                type = widgetType
            )
        }
    }

} 
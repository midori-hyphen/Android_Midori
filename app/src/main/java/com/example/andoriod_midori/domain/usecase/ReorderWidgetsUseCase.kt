package com.example.andoriod_midori.domain.usecase

import com.example.andoriod_midori.data.models.WidgetData
import com.example.andoriod_midori.data.repository.MainRepository
import com.example.andoriod_midori.domain.common.Result
import javax.inject.Inject

class ReorderWidgetsUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(widgets: List<WidgetData>): Result<Unit> = repository.reorderWidgets(widgets)
} 
package com.example.andoriod_midori.domain.usecase

import com.example.andoriod_midori.data.models.WidgetData
import com.example.andoriod_midori.data.repository.MainRepository
import com.example.andoriod_midori.domain.common.Result
import javax.inject.Inject

class UpdateWidgetUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(widget: WidgetData): Result<Unit> = repository.updateWidget(widget)
} 
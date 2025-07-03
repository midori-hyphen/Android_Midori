package com.example.andoriod_midori.domain.usecase

import com.example.andoriod_midori.data.repository.MainRepository
import com.example.andoriod_midori.domain.common.Result
import javax.inject.Inject

class DeleteWidgetUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(widgetId: String): Result<Unit> = repository.deleteWidget(widgetId)
} 
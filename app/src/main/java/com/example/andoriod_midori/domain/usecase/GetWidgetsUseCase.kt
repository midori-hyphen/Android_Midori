package com.example.andoriod_midori.domain.usecase

import com.example.andoriod_midori.data.models.WidgetData
import com.example.andoriod_midori.data.repository.MainRepository
import com.example.andoriod_midori.domain.common.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWidgetsUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(): Flow<Result<List<WidgetData>>> = repository.getWidgets()
} 
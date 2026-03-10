package com.einshams.domain.usecase

import com.einshams.domain.model.HomeItem
import com.einshams.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomeItemsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<HomeItem>> {
        return homeRepository.getItems()
    }
}

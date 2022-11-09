package com.finance.android.viewmodels

import android.app.Application
import com.finance.android.domain.repository.BaseRepository
import com.finance.android.domain.repository.PointRepository
import javax.inject.Inject

//@HiltViewModel
class PointViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val pointRepository: PointRepository,
) {
}
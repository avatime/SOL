package com.finance.backend.attendance

import com.finance.backend.common.util.JwtUtils
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service("DailyService")
@RequiredArgsConstructor
class DailyServiceImpl(
        private val attendanceRepository: AttendanceRepository,
        private val jwtUtils: JwtUtils
) : DailyService {
    override fun check() {
        TODO("Not yet implemented")
    }
}
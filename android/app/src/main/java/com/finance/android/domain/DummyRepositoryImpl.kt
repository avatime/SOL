package com.finance.android.domain

import com.finance.android.domain.dto.request.RemitInfoRequestDto
import com.finance.android.domain.dto.request.RemitPhoneRequestDto
import com.finance.android.domain.dto.response.*
import com.finance.android.domain.repository.BankRepository
import com.finance.android.domain.repository.RemitRepository
import com.finance.android.domain.repository.SampleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DummyRepositoryImpl @Inject constructor() :
    SampleRepository,
    RemitRepository,
    BankRepository {
    override fun getSampleData(): Array<Int> {
        return arrayOf(1, 2, 3)
    }

    override suspend fun reissueToken(): ReissueTokenResponseDto {
        TODO("Not yet implemented")
    }

    override suspend fun getRecommendedAccount(): MutableList<RecentTradeResponseDto> {
        return mutableListOf(
            RecentTradeResponseDto(
                acReceive = "채윤선",
                acNo = "123456",
                cpName = "신한은행",
                bkStatus = true,
                cpLogo = "https://mblogthumb-phinf.pstatic.net/20160728_194/ppanppane_1469696183585pXt1k_PNG/KB%BC%D5%C7%D8%BA%B8%C7%E8_%283%29.png?type=w800",
                tdData = ""
            ),
            RecentTradeResponseDto(
                acReceive = "채윤선123123",
                acNo = "123456",
                cpName = "신한은행",
                bkStatus = false,
                cpLogo = "https://m.asunmall.kr/web/product/medium/201808/0ba1737eb6a71bf7efc6d17f51170260.jpg",
                tdData = ""
            ),
            RecentTradeResponseDto(
                acReceive = "채윤선",
                acNo = "123456",
                cpName = "신한은행",
                bkStatus = true,
                cpLogo = "https://mblogthumb-phinf.pstatic.net/20160728_194/ppanppane_1469696183585pXt1k_PNG/KB%BC%D5%C7%D8%BA%B8%C7%E8_%283%29.png?type=w800",
                tdData = ""
            ),
            RecentTradeResponseDto(
                acReceive = "채윤선123123",
                acNo = "123456",
                cpName = "신한은행",
                bkStatus = false,
                cpLogo = "http://m.asunmall.kr/web/product/medium/201808/0ba1737eb6a71bf7efc6d17f51170260.jpg",
                tdData = ""
            )
        )
    }

    override suspend fun postRemitToAccount(remitInfoRequestDto: RemitInfoRequestDto) {
        TODO("Not yet implemented")
    }

    override suspend fun postRemitToPhone(remitPhoneRequestDto: RemitPhoneRequestDto) {
        TODO("Not yet implemented")
    }

    override suspend fun putRemitBookmark(acNo: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountList(): MutableList<BankAccountResponseDto> {
        return MutableList(3) {
            BankAccountResponseDto(
                acNo = "acNo",
                balance = it,
                acName = "acName",
                cpName = "신한은행",
                cpLogo = "https://mblogthumb-phinf.pstatic.net/20160728_194/ppanppane_1469696183585pXt1k_PNG/KB%BC%D5%C7%D8%BA%B8%C7%E8_%283%29.png?type=w800",
                acReg = true
            )
        }
    }

    override suspend fun getRegisteredAccount(): MutableList<BankAccountResponseDto> {
        TODO("Not yet implemented")
    }

    override suspend fun putRegisterAccount(acNo: String) {
        TODO("Not yet implemented")
    }

    override suspend fun putRegisterMainAccount(acNo: String) {
        TODO("Not yet implemented")
    }

    override suspend fun putBookmarkAccount(acNo: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountSendDetail(
        acNo: String,
        type: Int
    ): MutableList<BankTradeResponseDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountDetail(acNo: String): MutableList<BankDetailResponseDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecentAccount(): MutableList<RecentTradeResponseDto> {
        TODO("Not yet implemented")
    }

    override suspend fun checkAccount(
        acNo: String,
        cdCode: Int
    ): String {
        TODO("Not yet implemented")
    }

    override suspend fun getAllBank(): MutableList<BankInfoResponseDto> {
        return mutableListOf(
            BankInfoResponseDto(
                cpName = "싱항응행",
                cpLogo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
            ),
            BankInfoResponseDto(
                cpName = "싱항응행",
                cpLogo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
            ),
            BankInfoResponseDto(
                cpName = "싱항응행",
                cpLogo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
            ),
            BankInfoResponseDto(
                cpName = "싱항응행",
                cpLogo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
            ),
            BankInfoResponseDto(
                cpName = "싱항응행",
                cpLogo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"

            ),
            BankInfoResponseDto(
                cpName = "싱항응행",
                cpLogo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
            ),
            BankInfoResponseDto(
                cpName = "싱항응행",
                cpLogo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
            ),
            BankInfoResponseDto(
                cpName = "싱항응행",
                cpLogo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
            ),
            BankInfoResponseDto(
                cpName = "싱항응행",
                cpLogo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
            ),
            BankInfoResponseDto(
                cpName = "싱항응행",
                cpLogo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
            ),
            BankInfoResponseDto(
                cpName = "싱항응행",
                cpLogo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
            )

        )
    }

    override suspend fun getAllMainAccount(): AccountRegisteredResponseDto {
        TODO("Not yet implemented")
    }
}

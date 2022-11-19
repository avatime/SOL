package com.finance.android.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.finance.android.domain.dto.request.AccountNumberDto
import com.finance.android.domain.dto.request.CardNumberDto
import com.finance.android.domain.dto.request.InsuranceIdRequestDto
import com.finance.android.domain.dto.request.StockAccountNumberDto
import com.finance.android.domain.dto.response.BankAccountResponseDto
import com.finance.android.domain.dto.response.CardInfoResponseDto
import com.finance.android.domain.dto.response.InsuranceInfoResponseDto
import com.finance.android.domain.repository.*
import com.finance.android.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAssetViewModel @Inject constructor(
    application: Application,
    baseRepository: BaseRepository,
    private val userRepository: UserRepository,
    private val bankRepository: BankRepository,
    private val cardRepository: CardRepository,
    private val insuranceRepository: InsuranceRepository,
    private val stockRepository: StockRepository
) : BaseViewModel(application, baseRepository) {
    val selectedAll = mutableStateOf(false)
    val accountList =
        mutableStateOf<Response<MutableList<BankAccountResponseDto>>>(Response.Loading)
    lateinit var accountCheckList: Array<MutableState<Boolean>>
    val cardList = mutableStateOf<Response<MutableList<CardInfoResponseDto>>>(Response.Loading)
    lateinit var cardCheckList: Array<MutableState<Boolean>>
    val stockAccountList =
        mutableStateOf<Response<MutableList<BankAccountResponseDto>>>(Response.Loading)
    lateinit var stockAccountCheckList: Array<MutableState<Boolean>>
    val insuranceList =
        mutableStateOf<Response<MutableList<InsuranceInfoResponseDto>>>(Response.Loading)
    lateinit var insuranceCheckList: Array<MutableState<Boolean>>

    val repAccountNumber = mutableStateOf("")

    private val registerStateAccount = mutableStateOf<Response<Unit>>(Response.Loading)
    private val registerStateCard = mutableStateOf<Response<Unit>>(Response.Loading)
    private val registerStateStockAccount = mutableStateOf<Response<Unit>>(Response.Loading)
    private val registerStateInsurance = mutableStateOf<Response<Unit>>(Response.Loading)

    val checkHasRepAccount = mutableStateOf<Response<Boolean>>(Response.Loading)

    fun init() {
        viewModelScope.launch {
            loadAccountList()
            loadCardList()
            loadStockAccountList()
            loadInsuranceList()
            loadCheckRepAccount()
        }
    }

    fun getLoadState(): Response<Unit> {
        val arr =
            arrayOf(
                accountList,
                cardList,
                stockAccountList,
                insuranceList,
                checkHasRepAccount
            )

        return if (arr.count { it.value is Response.Loading } != 0) {
            Response.Loading
        } else if (arr.count { it.value is Response.Failure } != 0) {
            Response.Failure(null)
        } else {
            Response.Success(Unit)
        }
    }

    fun getAddedAccountList(): List<BankAccountResponseDto> {
        if (accountList.value !is Response.Success) {
            return emptyList()
        }
        return (accountList.value as Response.Success).data
            .filterIndexed { idx, _ -> accountCheckList[idx].value }
    }

    fun getRegisterState(): Response<Unit> {
        val arr = arrayOf(
            registerStateAccount,
            registerStateCard,
            registerStateStockAccount,
            registerStateInsurance
        )

        return if (arr.any { it.value is Response.Failure }) {
            Response.Failure(null)
        } else if (arr.any { it.value is Response.Loading }) {
            Response.Loading
        } else {
            Response.Success(Unit)
        }
    }

    fun onClickSelectAll() {
        accountCheckList.forEach { it.value = !selectedAll.value }
        cardCheckList.forEach { it.value = !selectedAll.value }
        stockAccountCheckList.forEach { it.value = !selectedAll.value }
        insuranceCheckList.forEach { it.value = !selectedAll.value }
        selectedAll.value = !selectedAll.value
    }

    fun onClickAccountItem(index: Int) {
        accountCheckList[index].value = !accountCheckList[index].value
        calculateSelectAll()
    }

    fun onClickCardItem(index: Int) {
        cardCheckList[index].value = !cardCheckList[index].value
        calculateSelectAll()
    }

    fun onClickStockAccountItem(index: Int) {
        stockAccountCheckList[index].value = !stockAccountCheckList[index].value
        calculateSelectAll()
    }

    fun onClickInsuranceItem(index: Int) {
        insuranceCheckList[index].value = !insuranceCheckList[index].value
        calculateSelectAll()
    }

    fun onClickRepAccountItem(accountNumber: String) {
        repAccountNumber.value = accountNumber
        Log.i("LEEJY", accountNumber)
    }

    fun registerAsset() {
        viewModelScope.launch {
            registerAccount()
            registerCard()
            registerStockAccount()
            registerInsurance()
        }
    }

    fun hasAssetToRegister(): Boolean {
        return (accountList.value as Response.Success).data.isNotEmpty() ||
            (cardList.value as Response.Success).data.isNotEmpty() ||
            (stockAccountList.value as Response.Success).data.isNotEmpty() ||
            (insuranceList.value as Response.Success).data.isNotEmpty()
    }

    private fun calculateSelectAll() {
        selectedAll.value = accountCheckList.all { it.value } &&
            cardCheckList.all { it.value } &&
            stockAccountCheckList.all { it.value } &&
            insuranceCheckList.all { it.value }
    }

    private suspend fun loadAccountList() {
        this@AddAssetViewModel.run {
            bankRepository.getAccountList()
                .filter { !it.isRegister }
                .toMutableList()
        }
            .collect {
                accountList.value = it
                if (it is Response.Success) {
                    accountCheckList = Array(it.data.size) { mutableStateOf(false) }
                    if (it.data.isNotEmpty()) {
                        repAccountNumber.value = it.data[0].acNo
                    }
                }
            }
    }

    private suspend fun loadCardList() {
        this@AddAssetViewModel.run {
            cardRepository.getCardList()
                .filter { !it.isRegister }
                .toMutableList()
        }
            .collect {
                cardList.value = it
                if (it is Response.Success) {
                    cardCheckList = Array(it.data.size) { mutableStateOf(false) }
                }
            }
    }

    private suspend fun loadStockAccountList() {
        this@AddAssetViewModel.run {
            stockRepository.getStockAccountList()
                .filter { !it.isRegister }
                .toMutableList()
        }
            .collect {
                stockAccountList.value = it
                if (it is Response.Success) {
                    stockAccountCheckList = Array(it.data.size) { mutableStateOf(false) }
                }
            }
    }

    private suspend fun loadInsuranceList() {
        this@AddAssetViewModel.run {
            insuranceRepository.getInsuranceList()
                .filter { !it.isRegister }
                .toMutableList()
        }
            .collect {
                insuranceList.value = it
                if (it is Response.Success) {
                    insuranceCheckList = Array(it.data.size) { mutableStateOf(false) }
                }
            }
    }

    private suspend fun registerAccount() {
        if (accountList.value !is Response.Success) {
            return
        }

        this@AddAssetViewModel.run {
            bankRepository.putRegisterAccount(
                (accountList.value as Response.Success).data
                    .filterIndexed { idx, _ -> accountCheckList[idx].value }
                    .map { AccountNumberDto(it.acNo) }
                    .toTypedArray()
            )
        }
            .collect {
                registerStateAccount.value = it
            }
    }

    private suspend fun registerCard() {
        if (cardList.value !is Response.Success) {
            return
        }

        this@AddAssetViewModel.run {
            cardRepository.putRegisterCard(
                (cardList.value as Response.Success).data
                    .filterIndexed { idx, _ -> cardCheckList[idx].value }
                    .map { CardNumberDto(it.cardNumber) }
                    .toTypedArray()
            )
        }
            .collect {
                registerStateCard.value = it
            }
    }

    private suspend fun registerStockAccount() {
        if (stockAccountList.value !is Response.Success) {
            return
        }

        this@AddAssetViewModel.run {
            stockRepository.putRegisterStockAccount(
                (stockAccountList.value as Response.Success).data
                    .filterIndexed { idx, _ -> stockAccountCheckList[idx].value }
                    .map { StockAccountNumberDto(it.acNo) }
                    .toTypedArray()
            )
        }
            .collect {
                registerStateStockAccount.value = it
            }
    }

    private suspend fun registerInsurance() {
        if (insuranceList.value !is Response.Success) {
            return
        }

        this@AddAssetViewModel.run {
            insuranceRepository.putRegisterInsurance(
                (insuranceList.value as Response.Success).data
                    .filterIndexed { idx, _ -> insuranceCheckList[idx].value }
                    .map { InsuranceIdRequestDto(it.isId) }
                    .toTypedArray()
            )
        }
            .collect {
                registerStateInsurance.value = it
            }
    }

    suspend fun registerRepAccount(onSuccess: () -> Unit) {
        if (accountList.value !is Response.Success ||
            checkHasRepAccount.value !is Response.Success ||
            (checkHasRepAccount.value as Response.Success<Boolean>).data
        ) {
            return
        }

        this@AddAssetViewModel.run {
            val accountNumberDto = AccountNumberDto(repAccountNumber.value)
            bankRepository.putRegisterMainAccount(accountNumberDto)
        }
            .collect {
                onSuccess()
            }
    }

    private suspend fun loadCheckRepAccount() {
        this@AddAssetViewModel.run {
            userRepository.checkRepAccount()
        }
            .collect {
                checkHasRepAccount.value = it
            }
    }

    fun getCountSelectedAssetToAdd(): Int {
        return accountCheckList.count { it.value } +
            cardCheckList.count { it.value } +
            insuranceCheckList.count { it.value } +
            stockAccountCheckList.count { it.value }
    }
}

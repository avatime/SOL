package com.finance.android.ui.fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Mapping
import com.anychart.enums.MarkerType
import com.finance.android.R
import com.finance.android.domain.dto.response.FinanceDetailResponseDto
import com.finance.android.ui.components.*
import com.finance.android.ui.theme.DarkBlueGrey
import com.finance.android.ui.theme.Disabled
import com.finance.android.viewmodels.PeriodType
import com.finance.android.viewmodels.StockDetailViewModel
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
<<<<<<< HEAD
fun StockDetailFragment(
    stockDetailViewModel: StockDetailViewModel = hiltViewModel(),
    onClose: () -> Unit
=======
fun StockDetailFragment (
    financeDetailViewModel: FinanceDetailViewModel = hiltViewModel(),
    onClose: () -> Unit,
    fnName: String,
    close: Int,
    per: Float
>>>>>>> 31dc155 (feat: insurance detail 연동 확인)
) {
    LaunchedEffect(Unit) {
        stockDetailViewModel.launch()
    }

    Scaffold(
        topBar = {
            BackHeaderBar(
                onClickBack = onClose,
                text = "",
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
        ) {
            if (stockDetailViewModel.stockDetailList.value.isNotEmpty()) {
                val after = stockDetailViewModel.stockDetailList.value.last()
                val before =
                    stockDetailViewModel.stockDetailList.value[
                        max(
                            0,
                            stockDetailViewModel.stockDetailList.value.lastIndex - stockDetailViewModel.periodType.value.period
                        )
                    ]
                val percentage = (after.close - before.close).toFloat() / before.close * 100

                Screen(
                    fnName = stockDetailViewModel.fnName,
                    close = after.close,
                    per = round(percentage * 100) / 100,
                    diff = abs(after.close - before.close),
                    stockDetailInfoList = stockDetailViewModel.stockDetailList.value,
                    periodType = stockDetailViewModel.periodType.value,
                    onClickPeriodType = { type -> stockDetailViewModel.onClickPeriod(type) }
                )
            }

            if (stockDetailViewModel.loading.value) {
                AnimatedLoading()
            }

            if (stockDetailViewModel.error.value != null) {
                CustomDialog(
                    dialogType = DialogType.ERROR,
                    dialogActionType = DialogActionType.ONE_BUTTON,
                    title = stringResource(id = R.string.msg_server_error),
                    onPositive = { stockDetailViewModel.launch() }
                )
            }
        }
    }
}

@Composable
private fun Screen(
    fnName: String,
    close: Int,
    per: Float,
    diff: Int,
    stockDetailInfoList: Array<FinanceDetailResponseDto>,
    periodType: PeriodType,
    onClickPeriodType: (periodType: PeriodType) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_large)))
        Title(
            fnName = fnName,
            close = close,
            per = per,
            diff = diff,
            periodType = periodType
        )
        Spacer(modifier = Modifier.height(30.dp))
        // 그래프 라이브러리에 버그가 있는지 데이터 갱신이 안돼서
        // 불가피하게 이렇게 작성함 ㅠㅠ
        when (periodType) {
            PeriodType.WEEK -> DrawGraph(
                stockDetailInfoList = stockDetailInfoList,
                per = per,
                periodType = periodType
            )
            PeriodType.MONTH -> DrawGraph(
                stockDetailInfoList = stockDetailInfoList,
                per = per,
                periodType = periodType
            )
            PeriodType.THREE_MONTH -> DrawGraph(
                stockDetailInfoList = stockDetailInfoList,
                per = per,
                periodType = periodType
            )
            PeriodType.HALF_YEAR -> DrawGraph(
                stockDetailInfoList = stockDetailInfoList,
                per = per,
                periodType = periodType
            )
            PeriodType.YEAR -> DrawGraph(
                stockDetailInfoList = stockDetailInfoList,
                per = per,
                periodType = periodType
            )
            PeriodType.TWO_YEAR -> DrawGraph(
                stockDetailInfoList = stockDetailInfoList,
                per = per,
                periodType = periodType
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Controller(
            periodType = periodType,
            onClickPeriodType = onClickPeriodType
        )
    }
}

@Preview
@Composable
private fun Title(
    fnName: String = "오만전자",
    close: Int = 50000,
    per: Float = 5.5f,
    diff: Int = 1000,
    periodType: PeriodType = PeriodType.WEEK
) {
    Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))) {
        Text(
            text = fnName,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Text(
            text = DecimalFormat("#,###원").format(close),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium))
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Text(
                text = "${periodType.value} 전보다",
                color = Disabled
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${if (per > 0) "+" else "-"}${DecimalFormat("#,###원").format(diff)} (${
                abs(
                    per
                )
                }%)",
                color = if (per > 0) Color.Red else Color.Blue
            )
        }
    }
}

@SuppressLint("InflateParams")
@Composable
private fun DrawGraph(
    stockDetailInfoList: Array<FinanceDetailResponseDto>,
    periodType: PeriodType,
    per: Float
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = {
            LayoutInflater.from(it).inflate(R.layout.stock_graph, null, false)
        },
        update = {
            setupChartView(
                view = it,
                stockDetailInfoList = stockDetailInfoList.takeLast(periodType.period)
                    .toTypedArray(),
                periodType = periodType,
                per = per
            )
        }
    )
}

private fun setupChartView(
    view: View,
    stockDetailInfoList: Array<FinanceDetailResponseDto>,
    periodType: PeriodType,
    per: Float
) {
    val anyChartView = view.findViewById<AnyChartView>(R.id.any_chart_view)

    val cartesian = AnyChart.line().apply {
        animation(true)
        legend(false)
        labels(true)
        xAxis(false)
        yAxis(false)
    }

    val max = stockDetailInfoList.maxOfOrNull { data -> data.close } ?: 0
    val min = stockDetailInfoList.minOfOrNull { data -> data.close } ?: 0
    var flagMax = false
    var flagMin = false

    val seriesData =
        stockDetailInfoList
            .map { data ->
                val tooltip = DecimalFormat("#,###원").format(data.close)
                CustomValueEntry(
                    x = data.fnDate,
                    value = data.close,
                    tooltip = tooltip,
                    label = if (data.close == max && !flagMax) {
                        flagMax = true
                        "최고 $tooltip"
                    } else if (data.close == min && !flagMin) {
                        flagMin = true
                        "최저 $tooltip"
                    } else ""
                )
            }

    val set = com.anychart.data.Set.instantiate()
    set.data(seriesData)
    val series1Mapping: Mapping =
        set.mapAs("{ x: 'x', value: 'value', tooltip: 'tooltip', label: 'label' }")

    val series = cartesian.line(series1Mapping)
    series.stroke("${periodType.stroke} ${if (0 < per) "red" else "blue"}")
    series.hovered().markers().enabled(true)
    series.hovered().markers()
        .type(MarkerType.CIRCLE)
        .fill(if (0 < per) "red" else "blue", 1)
        .size(0.5)

    series.tooltip()
        .format("{%tooltip}")

    series.labels()
        .format("{%label}")
        .fontColor(if (0 < per) "red" else "blue")

    series.tooltip().background()
        .corners(5)

    anyChartView.setChart(cartesian)
}

@Preview
@Composable
private fun Controller(
    periodType: PeriodType = PeriodType.WEEK,
    onClickPeriodType: (periodType: PeriodType) -> Unit = { }
) {
    Row(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            PeriodType.values().forEach {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            onClickPeriodType(it)
                        }
                        .background(if (periodType == it) DarkBlueGrey else Color.Transparent)
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it.value,
                        color = if (periodType == it) Color.Unspecified else Disabled
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = 5.dp
                )
        ) {
            Text("얍")
        }
    }
}

private class CustomValueEntry(
    x: String,
    value: Number,
    tooltip: String,
    label: String
) : ValueDataEntry(x, value) {
    init {
        setValue("tooltip", tooltip)
        setValue("label", label)
    }
}

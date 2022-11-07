package com.finance.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.domain.dto.response.DailyProfileResponseDto


@Composable
fun showProfileList(
    profileList: MutableList<DailyProfileResponseDto>,
    onClickImage: (profileId : Int) -> Unit = {}
) {
    MyGrid(content = profileList , columnSize = 3, onClickImage)
}

/**
 * @param content : grid로 표현하려는 아이템 리스트
 * @param columnSize : grid column 개수
 */
@Composable
fun MyGrid(content: MutableList<DailyProfileResponseDto>, columnSize: Int, onClickImage: (profileId: Int) -> Unit){
    val rowsCount = 1 + (content.size -1)/columnSize // row 개수
    BoxWithConstraints {
        val maxWidth = this.maxWidth
        Column(

        ) {
            repeat(rowsCount) { rowIndex ->
                val rangeStart = rowIndex * columnSize
                var rangeEnd = rangeStart + columnSize -1
                if (rangeEnd > content.lastIndex) rangeEnd = content.lastIndex // row로 표현될 list의 range를 계산, slice하여 row 생성
                RowOfGrid(content.slice(rangeStart..rangeEnd), maxWidth/columnSize, onClickImage)
            }
        }
    }
}

/**
 * @param rowList : row로 표현될 아이템 리스트
 * @param columnWidth : row item들을 동일 width로 보여주기 위해 maxWidth를 column으로 나눈 dp 값
 */
@Composable
fun RowOfGrid(rowList: List<DailyProfileResponseDto>, columnWidth: Dp, onClickImage: (profileId : Int) -> Unit){
    Row (
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(25.dp),
    ) {
        repeat(rowList.size) { index ->
            val item = rowList[index]
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.url)
                    .crossfade(true)
                    .build(),
                contentDescription = item.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = dimensionResource(R.dimen.padding_small))
                    .clickable { onClickImage(item.id) }
            )
            if(index != 2) Spacer(modifier = Modifier.padding(20.dp))
        }
    }
}

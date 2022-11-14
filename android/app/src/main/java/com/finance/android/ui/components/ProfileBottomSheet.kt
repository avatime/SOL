package com.finance.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.domain.dto.response.DailyProfileResponseDto


@Composable
fun ShowProfileList(
    profileList: MutableList<DailyProfileResponseDto>,
    onClickImage: (profileId : Int) -> Unit = {}
) {
    MyGrid(content = profileList , columnSize = 3, onClickImage)
}


@Composable
fun MyGrid(content: MutableList<DailyProfileResponseDto>, columnSize: Int, onClickImage: (profileId: Int) -> Unit){
    val rowsCount = 1 + (content.size -1)/columnSize // row 개수
    BoxWithConstraints {
        Column{
            repeat(rowsCount) { rowIndex ->
                val rangeStart = rowIndex * columnSize
                var rangeEnd = rangeStart + columnSize -1
                if (rangeEnd > content.lastIndex) rangeEnd = content.lastIndex // row로 표현될 list의 range를 계산, slice하여 row 생성
                RowOfGrid(content.slice(rangeStart..rangeEnd), onClickImage)
            }
        }
    }
}

@Composable
fun RowOfGrid(rowList: List<DailyProfileResponseDto>, onClickImage: (profileId: Int) -> Unit){
    Row (
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.padding(bottom = 25.dp, top = 10.dp),
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
                    .clip(CircleShape)
                    .clickable { onClickImage(item.id) }
            )
            if(index != 2) Spacer(modifier = Modifier.padding(20.dp))
        }
    }
}

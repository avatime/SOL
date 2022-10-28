package com.finance.android.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.finance.android.R
import com.finance.android.ui.components.ButtonType
import com.finance.android.ui.components.RoundedTextButton
import com.finance.android.ui.components.TextButton
import com.finance.android.utils.Const

@Composable
fun HomeScreen(navController: NavController) {

    Column (modifier = Modifier
        .verticalScroll(rememberScrollState())
        .background(color = Color(R.color.light_gray))) {
        CardContainer(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(color = Color.White, shape = RoundedCornerShape(10)),
            navController = navController
        )
        CardContainer2(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(color = Color.White, shape = RoundedCornerShape(10)),
            navController = navController
        )
    }
}

@Composable
fun CardContainer(modifier: Modifier, navController: NavController) {
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium))
        ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "자산",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(text = "4", color = Color.Gray, modifier = Modifier.padding(start = 8.dp))
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(onClick = {
                navController.navigate(Const.ASSET_SCREEN)
            },
                modifier = Modifier.size(30.dp)) {
                Image(painter = painterResource(R.drawable.arrow_forward_ios),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        AccountListItem()
        AccountListItem()
        AccountListItem()
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Divider()
        InsuranceListItem()
    }
}

@Composable
fun CardContainer2 (modifier: Modifier, navController: NavController) {
    Column(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "쌀",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(30.dp)) {
                Image(painter = painterResource(R.drawable.arrow_forward_ios),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(R.drawable.ssal),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { /*TODO*/ },
                    text = "만보기",
                    modifier = Modifier
                        .height(40.dp)
                        .width(120.dp)
                        .padding(start = 20.dp),
                    buttonType = ButtonType.CIRCULAR,
                )
                Spacer(modifier = Modifier.weight(1.0f))
                TextButton(onClick = { /*TODO*/ },
                    text = "출석체크",
                    modifier = Modifier
                        .height(40.dp)
                        .width(120.dp)
                        .padding(end = 20.dp),
                    buttonType = ButtonType.CIRCULAR,
                )
            }
        }
    }
}

@Composable
fun AccountListItem() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Column (modifier = Modifier
            .padding(start = 8.dp)) {
            Text(text = "나라사랑행복계좌")
            Text(text = "10,000원", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(onClick = { /*TODO*/ },
            text = "송금",
            modifier = Modifier
                .height(30.dp)
                .width(50.dp)
                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
            buttonType = ButtonType.ROUNDED,
            fontSize = 10.sp
        )
    }
}

@Composable
fun InsuranceListItem() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Column (modifier = Modifier
            .padding(start = 8.dp)) {
            Text(text = "신종단체상해보험", fontSize = 12.sp)
            Text(text = "보험료: 10,000원", fontWeight = FontWeight.Bold)
            Text(text = "계약자: 홍길동|피보험자: 홍길동", fontSize = 10.sp)
        }
    }
}
package com.supunsulakshana.kmpapp1

import android.graphics.drawable.Icon
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.supunsulakshana.kmpapp1.data.RandomUser
import com.supunsulakshana.kmpapp1.data.RandomUserResponse

class MainActivity : ComponentActivity() {

    val homeVideModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val users = homeVideModel.users.collectAsStateWithLifecycle()
            Scaffold(
                content = { innerPadding ->
                    Box(
                        modifier = Modifier
                            .systemBarsPadding()
                            .navigationBarsPadding()
                            .padding(innerPadding)
                    ){
                        AndroidApp(users = users.value)
                    }
                }
            )
        }
    }
}


@Composable
fun AndroidApp(users: List<RandomUser>){
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if(users.isEmpty()){
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No users found",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
        items(users, key = {user -> user.login?.uuid.toString()}){ user ->
            Card(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),

            ){
                Row{
                    Image(
                        painter = rememberAsyncImagePainter(model = user.picture?.thumbnail),
                        contentDescription ="",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(50.dp)
                    )
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "${user.name?.first} ${user.name?.last}",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif,
                                fontSize = 20.sp
                            ),
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text =  user.phone.toString(),
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 16.sp
                            ),
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
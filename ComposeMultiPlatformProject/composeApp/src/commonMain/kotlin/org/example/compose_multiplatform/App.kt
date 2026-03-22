package org.example.compose_multiplatform

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import org.jetbrains.compose.resources.painterResource

import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.launch
import org.example.compose_multiplatform.data.Product
import org.example.compose_multiplatform.list.ListComponent



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    products: State<ListComponent.Model>,
    lazyListScrollBar: (@Composable (LazyListState, Modifier) -> Unit)? = null,
    lazyGridScrollBar: (@Composable (LazyGridState, Modifier) -> Unit)? = null,
    scrollBar: (@Composable (ScrollState, Modifier) -> Unit)? = null,
    onItemClicked: (Product) -> Unit
) {


    BoxWithConstraints {
        val scope = this
        val maxWidth = scope.maxWidth
        var cols = 2
        var modifier = Modifier.fillMaxWidth()
        if (maxWidth > 840.dp) {
            cols = 3
            modifier = Modifier.widthIn(max = 1080.dp)
        }

        val scrollState = rememberLazyGridState()
        val coroutineScope = rememberCoroutineScope()

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(cols),
                state = scrollState,
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                modifier = Modifier.draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(-delta)
                        }
                    }
                )
            ) {
                item(span = { GridItemSpan(cols) }) {
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(),
                        query = "",
                        active = false,
                        onActiveChange = {},
                        onQueryChange = {},
                        onSearch = {},
                        placeholder = {
                            Text("Search Products")
                        }
                    ) {}
                }
                items(
                    items = products.value.items,
                    key = { product -> product.id.toString() }) { product ->
                    Card(
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            val painter = rememberImagePainter(url = product.image.toString())
                            Image(
                                painter,
                                modifier = Modifier.height(130.dp).padding(8.dp),
                                contentDescription = product.title
                            )
                            Text(
                                product.title ?: "N/A",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(8.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.W500,
                                    fontSize = 14.sp
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    "$" + product.price.toString(),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(8.dp),
                                    color = Color.Red,
                                    style = TextStyle(
                                        fontWeight = FontWeight.W500,
                                        fontSize = 12.sp
                                    ),
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
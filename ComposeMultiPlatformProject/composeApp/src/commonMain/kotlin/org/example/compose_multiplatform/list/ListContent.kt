package org.example.compose_multiplatform.list

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.compose_multiplatform.AppContent
import org.example.compose_multiplatform.NavigationType

@Composable
fun ListContent(
    component: ListComponent,
    lazyListScrollBar: (@Composable (LazyListState, Modifier) -> Unit)? = null,
    lazyGridScrollBar: (@Composable (LazyGridState, Modifier) -> Unit)? = null,
    scrollBar: (@Composable (ScrollState, Modifier) -> Unit)? = null,
    modifier: Modifier = Modifier
) {

    val products = component.model.subscribeAsState()

    AppContent(
        products,
        lazyListScrollBar,
        lazyGridScrollBar,
        scrollBar) {
        component.onItemClicked(it)
    }
}
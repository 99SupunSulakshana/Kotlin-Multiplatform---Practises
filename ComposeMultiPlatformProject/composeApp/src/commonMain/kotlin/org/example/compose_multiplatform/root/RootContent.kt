package org.example.compose_multiplatform.root

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import org.example.compose_multiplatform.NavigationType
import org.example.compose_multiplatform.details.DetailContent
import org.example.compose_multiplatform.list.ListContent

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RootContent(
    component: RootComponent,
    lazyListScrollBar: (@Composable (LazyListState, Modifier) -> Unit)? = null,
    lazyGridScrollBar: (@Composable (LazyGridState, Modifier) -> Unit)? = null,
    scrollBar: (@Composable (ScrollState, Modifier) -> Unit)? = null,
    modifier: Modifier = Modifier
) {

    AppContentList(component,  lazyListScrollBar, lazyGridScrollBar, scrollBar, modifier)

}


@Composable
fun AppContentList(
    component: RootComponent,
    lazyListScrollBar: (@Composable (LazyListState, Modifier) -> Unit)? = null,
    lazyGridScrollBar: (@Composable (LazyGridState, Modifier) -> Unit)? = null,
    scrollBar: (@Composable (ScrollState, Modifier) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    MaterialTheme {
        Children(
            stack = component.stack,
            modifier = modifier,
            animation = stackAnimation(fade())
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.ListChild -> ListContent(
                    child.component,
                    lazyListScrollBar,
                    lazyGridScrollBar,
                    scrollBar,
                    modifier
                )

                is RootComponent.Child.DetailChild -> DetailContent(
                    child.component
                )
            }
        }
    }
}
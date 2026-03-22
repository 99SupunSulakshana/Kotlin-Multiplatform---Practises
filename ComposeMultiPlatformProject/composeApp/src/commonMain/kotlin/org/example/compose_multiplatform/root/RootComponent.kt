package org.example.compose_multiplatform.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.example.compose_multiplatform.HomeViewModel
import org.example.compose_multiplatform.data.Product
import org.example.compose_multiplatform.details.DefaultDetailComponent
import org.example.compose_multiplatform.details.DetailComponent
import org.example.compose_multiplatform.list.DefaultListComponent
import org.example.compose_multiplatform.list.ListComponent
import org.example.compose_multiplatform.root.DefaultRootComponent.Config.*
import org.example.compose_multiplatform.root.RootComponent.Child.*

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onBackPressed()

    sealed class Child {
        class ListChild(val component: ListComponent): Child()
        class DetailChild(val component: DetailComponent): Child()
    }
}

class DefaultRootComponent(
    private val componentContext: ComponentContext,
    private val homeViewModel: HomeViewModel,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.List,
            handleBackButton = true,
            serializer = Config.serializer(),
            childFactory = ::childFactory
        )


    @OptIn(DelicateDecomposeApi::class)
    private fun childFactory(config: Config, componentContext: ComponentContext) : RootComponent.Child {
        return when(config){
            is Config.List -> ListChild(
                DefaultListComponent(componentContext, homeViewModel){ item ->
                    navigation.push(Detail(item = item))
                }
            )
            is Config.Detail -> DetailChild(
                DefaultDetailComponent(componentContext, config.item){
                    onBackPressed()
                }
            )
        }
    }

    override fun onBackPressed() {
        navigation.pop()
    }

    @Serializable
    sealed interface Config {
        data object List: Config
        data class Detail(val item: Product): Config
    }
}
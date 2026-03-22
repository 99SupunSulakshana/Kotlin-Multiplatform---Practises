package org.example.compose_multiplatform.list

import androidx.compose.runtime.MutableState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.compose_multiplatform.HomeViewModel
import org.example.compose_multiplatform.data.Product

interface ListComponent {
    val model: Value<Model>

    fun onItemClicked(item: Product)

    data class Model(
        val items: List<Product>
    )

}


class DefaultListComponent(
    private val componentContext: ComponentContext,
    private val homeViewModel: HomeViewModel,
    private val onItemSelected: (item: Product) -> Unit,
): ListComponent, ComponentContext by componentContext {

    private val _model = MutableValue<ListComponent.Model>(ListComponent.Model(items = emptyList()))

   override val model: Value<ListComponent.Model> = _model

    override fun onItemClicked(item: Product) {
        onItemClicked(item)
    }


    init {
        CoroutineScope(Dispatchers.Default).launch {
            homeViewModel.products.collect {
                _model.value = ListComponent.Model(items = it)
            }
        }
    }



}
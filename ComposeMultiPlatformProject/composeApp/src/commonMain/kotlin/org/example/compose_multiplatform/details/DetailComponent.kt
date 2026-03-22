package org.example.compose_multiplatform.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.compose_multiplatform.data.Product

interface DetailComponent {
    val model: Value<Model>

    fun onBackPressed()

    data class Model(
        val items: Product
    )
}

class DefaultDetailComponent(
    private val componentContext: ComponentContext,
    private val item: Product,
    private val onBack: () -> Unit,
): DetailComponent, ComponentContext by componentContext {

    private val _model = MutableValue<DetailComponent.Model>(DetailComponent.Model(items = item))

    override val model: Value<DetailComponent.Model> = _model

    override fun onBackPressed() {
        onBack()
    }


    init {
        CoroutineScope(Dispatchers.Default).launch {

        }
    }



}
package org.example.compose_multiplatform

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import androidx.compose.ui.window.ComposeViewport
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.cache.memory.MemoryCache
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import org.jetbrains.compose.resources.configureWebResources
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main(){
    configureWebResources {
        resourcePathMapping { path -> "./$path" }
    }
    onWasmReady {
        ComposeViewport(content = {
                    CompositionLocalProvider(
                        LocalImageLoader provides remember { generateImageLoader() },
                    ) {
                        App()
                    }
                })
    }
}

private fun generateImageLoader(): ImageLoader {
    return ImageLoader {
        components {
            setupDefaultComponents()
        }
        interceptor {
            bitmapMemoryCacheConfig {
                maxSize(32 * 1024 * 1024) // 32MB
            }
        }
    }
}
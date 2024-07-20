package com.example.todoapp.feature.about

import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.getSystemService
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.todoapp.feature.about.databinding.AboutScreenBinding
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.picasso.PicassoDivImageLoader

@Composable
fun AboutScreen(navigateBack: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Screen(navigateBack)
    }
}

@Composable
private fun Screen(navigateBack: () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        factory = { context ->
            with(context) {
                val layoutInflater = context.getSystemService<LayoutInflater>()
                val binding = AboutScreenBinding.inflate(layoutInflater!!)
                val assetReader = AssetReader(this)

                val divJson = assetReader.read("screen.json")
                val templatesJson = divJson.optJSONObject("templates")
                val cardJson = divJson.getJSONObject("card")

                val divContext = Div2Context(
                    baseContext = getActivity()!!,
                    configuration = createDivConfiguration(navigateBack),
                    lifecycleOwner = lifecycleOwner,
                )

                val divView = Div2ViewFactory(divContext, templatesJson).createView(cardJson)
                binding.root.addView(divView)

                binding.root
            }
        }
    )
}

private fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

private fun Context.createDivConfiguration(navigateBack: () -> Unit): DivConfiguration {
    val imageLoader = PicassoDivImageLoader(this)
    val configuration = DivConfiguration
        .Builder(imageLoader)
        .actionHandler(DivActionHandler(navigateBack))
        .build()
    return configuration
}
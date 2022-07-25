package com.example.myapplication.ui.activities

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.data.ResultData
import com.example.myapplication.utils.Resource
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by msaycon on 17,Jul,2022
 */
class MainViewModel @Inject constructor(private val context: Context) : BaseViewModel(),
    OnSuccessListener<Text> {

    private val recognizer: TextRecognizer by lazy {
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    fun processInputImage(mediaUri: Uri) {
        try {
            val image = InputImage.fromFilePath(context, mediaUri)
            recognizer.process(image).addOnSuccessListener(this)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun onSuccess(visionText: Text?) {
        viewModelScope.launch {
            visionText?.let {
                it.textBlocks.forEach { block ->
                    block.lines
                        .filter { line ->
                            line.text.isNotEmpty() && line.text.isNotBlank() && !Pattern.matches(
                                ".*[a-z].*|.*[A-Z].*",
                                line.text
                            )
                        }.map { line ->
                            line.text.replace("\\s".toRegex(), "")
                        }
                        .forEach { line ->
                            try {
                                val result = ExpressionBuilder(line)
                                    .build().validate()
                                if (result.isValid) {
                                    val expressionResult = ExpressionBuilder(line)
                                        .build().evaluate()

                                    val resultData =
                                        ResultData(line, expressionResult.toString())
                                    resource.postValue(Resource.success(resultData))
                                    return@launch
                                }
                            } catch (e: Exception) {
                                Timber.e(e)
                            }
                        }
                }
            }
        }
    }
}
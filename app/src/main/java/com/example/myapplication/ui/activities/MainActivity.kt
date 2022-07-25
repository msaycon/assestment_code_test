package com.example.myapplication.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.data.ResultData
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.utils.Status
import com.example.myapplication.utils.showErrorDialog
import com.google.mlkit.vision.common.InputImage
import com.jakewharton.rxbinding4.view.clicks
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.xml.xpath.XPathExpression

/**
 * Created by msaycon on 17,Jul,2022
 */

@RuntimePermissions
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private lateinit var binding: ActivityMainBinding

    private var mediaUri: Uri? = null

    private var temporaryFile: File? = null

    private val chooseImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                mediaUri = data?.data
                mediaUri?.let {
                    viewModel.processInputImage(it)
                }
            }
        }

    private val capturePhoto =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                mediaUri?.let {
                    viewModel.processInputImage(it)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        initializedViews()
        initializedListeners()
    }

    override fun initializedViews() {
        if (BuildConfig.FLAVOR == "redCamera" || BuildConfig.FLAVOR == "greenCamera") {
            binding.btnAction.text = getString(R.string.lbl_open_camera)
        } else {
            binding.btnAction.text = getString(R.string.lbl_choose_photo)
        }

        subscriptionsWhileInMemory.addAll(
            binding.btnAction.clicks()
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe {
                    if (BuildConfig.FLAVOR == "redCamera" || BuildConfig.FLAVOR == "greenCamera") {
                        openCameraCaptureWithPermissionCheck()
                    } else {
                        showImageChooserWithPermissionCheck()
                    }

                }
        )
    }

    override fun initializedListeners() {
        viewModel.resource.observe(this) {
            when (it.status) {
                Status.LOADING -> showLoadingProgress(it.data as Boolean)
                Status.ERROR -> {
                    context.showErrorDialog(it.message!!)
                }
                Status.SUCCESS -> {
                    if (it.data is ResultData) {
                        val resultData = it.data
                        binding.expression.text =
                            getString(R.string.input_format, resultData.expression)
                        binding.result.text = getString(R.string.answer_format, resultData.result)
                    }
                }
            }
        }
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showImageChooser() {
        val gallery =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        chooseImage.launch(gallery)
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onStoragePermissionDenied() {

    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun openCameraCapture() {
        temporaryFile = createTempMediaFile()
        temporaryFile?.let {
            mediaUri = getFileUri(it)
            capturePhoto.launch(mediaUri)
        }
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraPermissionDenied() {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun createTempMediaFile(): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date(System.currentTimeMillis()))
        val fileName = "JPEG_" + timeStamp + "_"

        val storageDir = filesDir
        return File.createTempFile(
            fileName,  /* prefix */".jpg",  /* suffix */
            storageDir /* directory */
        )
    }

    private fun getFileUri(mediaFile: File): Uri {
        return FileProvider.getUriForFile(
            this,
            "${this.packageName}.provider",
            mediaFile
        )
    }
}
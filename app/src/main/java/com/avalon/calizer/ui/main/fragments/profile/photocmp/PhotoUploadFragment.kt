package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.databinding.FragmentPhotoUploadBinding
import com.avalon.calizer.utils.showSnackBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class PhotoUploadFragment : Fragment() {
    private lateinit var binding: FragmentPhotoUploadBinding

    @Inject
    lateinit var viewModel: PhotoAnalyzeViewModel

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUriList = ArrayList<Uri>()
                result.data?.let { itdata ->
                    itdata.clipData?.let { itClipData ->
                        val itemCount = itClipData.itemCount
                        for (i in 0 until itemCount) {
                            val imageUri: Uri = itClipData.getItemAt(i).uri
                            imageUriList.add(imageUri)

                        }
                    } ?: kotlin.run {
                        //single image selected
                        itdata.data?.let { itUri ->
                            val imageUri: Uri = itUri
                            imageUriList.add(imageUri)
                            Log.d("ImageUri", "$imageUri")
                        }

                    }.also {
                        createListBitmap(imageUriList)
                    }

                }

            }
        }

    private fun createListBitmap(uriList: List<Uri>) {
        if (uriList.isNotEmpty()) {
            val bitmapList = ArrayList<Bitmap>()

            uriList.forEach { itUri ->
                itUri.path?.let { itPath ->


                }.also {
                    setDataAnalyze(bitmapList)
                }
            }

        } else {
            Toast.makeText(requireContext(), "RESIM YOK", Toast.LENGTH_SHORT).show()
        }

    }
    fun uriToBitmapGlide(imagePath: String):Bitmap{

         Glide.with(this).asBitmap().load(File(imagePath))
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {

                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }


    private fun getGalleryImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startForResult.launch(intent)

    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getGalleryImage()
        } else {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("package:${requireActivity().packageName}")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            requireActivity().startActivity(intent)
        }

    }

    private fun onclickRequestPermission(view: View) {

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                getGalleryImage()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                binding.clTopComp.showSnackBar(
                    view,
                    "PERMISSION REQUIRED",
                    Snackbar.LENGTH_INDEFINITE,
                    "OK"
                ) {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

    }

    fun initData() {
        binding.btnUploadImage.setOnClickListener {

            onclickRequestPermission(it)

        }
    }

    fun setDataAnalyze(bitmapList: List<Bitmap>) {
        Log.d("TestLog","BITMAPLIST")
        val list = ArrayList<PhotoAnalyzeData>()
        for (i in 0..bitmapList.size) {
            list.add(PhotoAnalyzeData(i.toLong(), bitmapList[i], false))
        }
        val newList = list.toTypedArray()
        val action =
            PhotoUploadFragmentDirections.actionPhotoUploadFragmentToPhotoAnalyzeLoadingFragment(
                newList
            )
        findNavController().navigate(action)
    }

}



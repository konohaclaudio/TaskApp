package com.example.taskapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.taskapp.R
import com.example.taskapp.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.initToolbar(toolbar: Toolbar) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

}

fun Fragment.showBottomSheet (
    titleDialog: Int? = null,
    titleButon: Int? = null,
    message: String,
    onClick: () -> Unit = {}

)

{   val bottomSheetDialog = BottomSheetDialog(requireContext(), )
    val bottomSheetBinding: BottomSheetBinding =
        BottomSheetBinding.inflate(layoutInflater, null, false)

    bottomSheetBinding.txtTitle.text = getText(titleDialog ?: R.string.attention)
    bottomSheetBinding.txtMessage.text = message
    bottomSheetBinding.btnOk.text = getText(titleButon?: R.string.text_warning)
    bottomSheetBinding.btnOk.setOnClickListener{
            onClick()
            bottomSheetDialog.dismiss()
    }

    bottomSheetDialog.setContentView(bottomSheetBinding.root)
    bottomSheetDialog.show() }

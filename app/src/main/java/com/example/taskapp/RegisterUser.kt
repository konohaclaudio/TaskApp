package com.example.taskapp

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.taskapp.databinding.ActivityRegisterUserBinding

class RegisterUser : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnRegister.setOnClickListener {

//           verifyFields()
//            InsertData()

        }

        binding.btnBack.setOnClickListener(View.OnClickListener {
           onBackPressed()
        })


        binding.showHideBtn.setOnClickListener {
            if (binding.showHideBtn.text.toString().equals("Show")) {
                binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                binding.showHideBtn.text = "Hide"
            } else {
                binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance())
                binding.showHideBtn.text = "Show"
            }
        }







    }

    private fun clearFields() {
        binding.edtEmail.setText("")
        binding.edtName.setText("")
        binding.edtPassword.setText("")
        binding.edtAge.setText("")
    }

//    private fun verifyFields() {
//
//        if(binding.edtEmail.text.isNullOrBlank()) {
//            binding.edtEmail.setError ("Write the Email")
//        }
//        if(binding.edtName.text!!.isEmpty()) {
//            binding.edtName.setError("Write the Name")
//        }
//        if(binding.edtPassword.text.isEmpty()) {
//            binding.edtPassword.setError("Write the Password")
//        }
//        if(binding.edtAge.text.isEmpty()) {
//            binding.edtAge.setError("Write the Age")
//        }

//        if (binding.edtEmail.text.isEmpty() ||binding.edtName.text.isEmpty() || binding.edtPassword.text.isEmpty() || binding.edtAge.text.isEmpty())
//        {
//            Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show()
//        }
//
//        else {
//        }
    }

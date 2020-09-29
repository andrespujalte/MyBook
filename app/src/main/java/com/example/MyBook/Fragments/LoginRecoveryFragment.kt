package com.example.MyBook.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.MyBook.R
import com.google.android.material.snackbar.Snackbar


class LoginRecoveryFragment : Fragment() {

    lateinit var bt_login_recover_ok : Button
    lateinit var bt_login_recover_cancel : Button
    lateinit var et_login_recovery_email: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v : View = inflater.inflate(R.layout.fragment_login_recovery, container, false)
        var password : String = LoginRecoveryFragmentArgs.fromBundle(requireArguments()).password.toString()
        var email : String = LoginRecoveryFragmentArgs.fromBundle(requireArguments()).email.toString()
        et_login_recovery_email = v.findViewById(R.id.et_login_recovery_email)
        bt_login_recover_ok = v.findViewById(R.id.bt_login_recover_ok)
        bt_login_recover_cancel = v.findViewById(R.id.bt_login_recover_cancel)
        bt_login_recover_ok.setOnClickListener(){
            if(email == et_login_recovery_email.text.toString()){
                sendEmail(
                    email,
                    "Recuperacion de contraseña de MyBook App",
                    "Su contraseña actual es: " + password
                )
                Snackbar.make(v, "EMAIL ENVIADO", Snackbar.LENGTH_SHORT)
            }else
            {
                Snackbar.make(v, "EMAIL INCORRECTO", Snackbar.LENGTH_SHORT)
            }

        }
        bt_login_recover_cancel.setOnClickListener(){

        }
        return v
    }
    private fun sendEmail(recipient: String, subject: String, message: String) {

        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)
        try {
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){ }
    }
}
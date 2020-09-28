package com.example.MyBook.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.MyBook.Clases.User
import com.example.MyBook.Database.appDatabase
import com.example.MyBook.Database.userDao
import com.example.MyBook.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login_password.*


class LoginPasswordFragment : Fragment() {

    lateinit var v : View
    lateinit var bt_login_password_login : Button
    lateinit var bt_login_password_back : Button
    lateinit var tv_login_password_password : TextView
    lateinit var iv_login_password : ImageView

    private var db : appDatabase? = null
    private var userDao : userDao? = null
    private lateinit var user : User
    var password : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate((R.layout.fragment_login_password),container,false)
        bt_login_password_login = v.findViewById(R.id.bt_login_password_login)
        bt_login_password_back = v.findViewById(R.id.bt_login_password_back)

        iv_login_password = v.findViewById(R.id.iv_login_password)
        tv_login_password_password = v.findViewById(R.id.tv_login_password_password)

        bt_login_password_login.text = getString(R.string.bt_login_password_login_Español)
        bt_login_password_back.text = getString(R.string.bt_login_password_back_Español)
        tv_login_password_password.text = getString(R.string.tv_login_password_password_Español)

        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()

        return v
    }
    override fun onStart() {
        super.onStart()


        bt_login_password_login.setOnClickListener {
            val action_loginPasswordFragment_to_main_navgraph = LoginPasswordFragmentDirections.actionLoginPasswordFragmentToMainNavgraph()
            password = LoginPasswordFragmentArgs.fromBundle(requireArguments()).password
            if(password==et_login_password.text.toString()){(v.findNavController().navigate(action_loginPasswordFragment_to_main_navgraph))}else{Snackbar.make(v,getString(R.string.sb_login_password_error_Español),Snackbar.LENGTH_SHORT).show()}
        }

        bt_login_password_back.setOnClickListener {
            val actionLoginPasswordFragmentToLoginUserFragment =LoginPasswordFragmentDirections.actionLoginPasswordFragmentToLoginUserFragment()
            (v.findNavController().navigate(actionLoginPasswordFragmentToLoginUserFragment))
        }
    }

}
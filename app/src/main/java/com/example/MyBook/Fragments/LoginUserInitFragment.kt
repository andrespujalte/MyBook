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
import com.bumptech.glide.Glide
import com.example.MyBook.Database.appDatabase
import com.example.MyBook.Database.userDao
import com.example.MyBook.R
import com.example.MyBook.Clases.User
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.fragment_login_user.*

class LoginUserInitFragment : Fragment() {

    lateinit var v : View
    lateinit var bt_login_user_next : Button
    lateinit var bt_login_user_create : Button
    lateinit var bt_login_user_recover : Button
    lateinit var tv_login_user : TextView
    lateinit var iv_login_user : ImageView
    private var db : appDatabase? = null
    private var userDao : userDao? = null
    private lateinit var user : User
    var userid: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate((R.layout.fragment_login_user),container,false)
        bt_login_user_next = v.findViewById(R.id.bt_login_user_next)
        bt_login_user_create = v.findViewById(R.id.bt_login_user_create)
        bt_login_user_recover = v.findViewById(R.id.bt_login_user_recover)
        iv_login_user = v.findViewById(R.id.iv_login_user)
        tv_login_user = v.findViewById(R.id.tv_login_user)
        bt_login_user_next.text = getString(R.string.bt_next_Español)
        bt_login_user_create.text = getString(R.string.bt_create_Español)
        bt_login_user_recover.text = getString(R.string.bt_recover_Español)
        tv_login_user.text = getString(R.string.tv_user_Español)
        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()
        user = User(1,"admin","Administrador","Administrador","1234","")
        userDao?.insertPerson(user)
        val img : ImageView = v.findViewById(R.id.iv_login_user)
        Glide.with(img.context).load("data:image/jpeg;base64,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").into(img)
        return v
    }
    override fun onStart() {
        super.onStart()

        val actionLoginUserFragmentToLoginNewUserFragment = LoginUserInitFragmentDirections.actionLoginUserFragmentToLoginNewUserFragment()

        bt_login_user_create.setOnClickListener {
            //RoomExplorer.show(context, appDatabase::class.java, "myDB")
            (v.findNavController().navigate(actionLoginUserFragmentToLoginNewUserFragment))}


        bt_login_user_recover.setOnClickListener {
            userid = et_login_user_name.text.toString().toLowerCase()
            var useridDB : String =  userDao?.loadPersonByUserId(userid)?.userid.toString()
            val actionLoginUserFragmentToLoginRecoveryFragment = LoginUserInitFragmentDirections.actionLoginUserFragmentToLoginRecoveryFragment(useridDB)
            if(useridDB == userid){(v.findNavController().navigate(actionLoginUserFragmentToLoginRecoveryFragment))}
            else{Snackbar.make(v,getString(R.string.sb_recover_error_Español),Snackbar.LENGTH_SHORT).show()}
        }

        bt_login_user_next.setOnClickListener {
            userid = et_login_user_name.text.toString().toLowerCase()
            var useridDB : String =  userDao?.loadPersonByUserId(userid)?.userid.toString()
            var passworDB: String = userDao?.loadPersonByUserId(userid)?.password.toString()
            val actionLoginUserFragmentToLoginPasswordFragment = LoginUserInitFragmentDirections.actionLoginUserFragmentToLoginPasswordFragment(passworDB)
            if(useridDB == userid){(v.findNavController().navigate(actionLoginUserFragmentToLoginPasswordFragment))}
            else{Snackbar.make(v,getString(R.string.sb_login_error_Español),Snackbar.LENGTH_SHORT).show()}
        }

    }

}
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
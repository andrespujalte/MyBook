package com.example.MyBook.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.MyBook.Clases.User
import com.example.MyBook.Database.appDatabase
import com.example.MyBook.Database.userDao
import com.example.MyBook.R
import com.google.android.material.snackbar.Snackbar


class LoginNewUserFragment : Fragment() {

    lateinit var v : View

    lateinit var et_login_newuser_userid : EditText
    lateinit var et_login_newuser_firstname : EditText
    lateinit var et_login_newuser_lastname : EditText
    lateinit var et_login_newuser_email : EditText
    lateinit var et_login_newuser_password : EditText
    lateinit var et_login_newuser_passwordconfirm: EditText
    lateinit var bt_login_newuser_add : Button
    lateinit var bt_login_newuser_cancel : Button
    lateinit var userid : String
    lateinit var email : String
    private var db : appDatabase? = null
    private var userDao : userDao? = null

    //private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        v = inflater.inflate((R.layout.fragment_login_new_user),container,false)
        et_login_newuser_userid = v.findViewById(R.id.et_login_newuser_userid)
        et_login_newuser_firstname = v.findViewById(R.id.et_login_newuser_firstname)
        et_login_newuser_lastname = v.findViewById(R.id.et_login_newuser_lastname)
        et_login_newuser_email = v.findViewById(R.id.et_login_newuser_email)
        et_login_newuser_password = v.findViewById(R.id.et_login_newuser_password)
        et_login_newuser_passwordconfirm = v.findViewById(R.id.et_login_newuser_passwordconfirm)

        bt_login_newuser_add = v.findViewById(R.id.bt_login_newuser_add)
        bt_login_newuser_cancel = v.findViewById(R.id.bt_login_newuser_cancel)
        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()

        return v
    }

    override fun onStart() {
        super.onStart()



        bt_login_newuser_add.setOnClickListener{
            userid = et_login_newuser_userid.text.toString().toLowerCase()
            email = et_login_newuser_email.text.toString().toLowerCase()
            if((et_login_newuser_userid.text.toString().trim() != "")&&(et_login_newuser_email.text.toString().toLowerCase().trim()!="")) {
                if(userDao?.loadPersonByUserId(userid)?.userid.isNullOrEmpty()){
                    if(userDao?.loadPersonByemail(email)?.email.isNullOrEmpty()){
                        if(et_login_newuser_password.text.toString() == et_login_newuser_passwordconfirm.text.toString() ) {
                            val maxid : Int? = userDao?.getMaxIndex()
                            if (maxid != null) {
                                userDao?.insertPerson(
                                    User(
                                        maxid.plus(1),
                                        et_login_newuser_userid.text.toString(),
                                        et_login_newuser_firstname.text.toString(),
                                        et_login_newuser_lastname.text.toString(),
                                        et_login_newuser_password.text.toString(),
                                        et_login_newuser_email.text.toString()
                                    )
                                )
                            }
                            else{
                                userDao?.insertPerson(
                                    User(
                                        0,
                                        et_login_newuser_userid.text.toString(),
                                        et_login_newuser_firstname.text.toString(),
                                        et_login_newuser_lastname.text.toString(),
                                        et_login_newuser_password.text.toString(),
                                        et_login_newuser_email.text.toString()
                                    )
                                )
                            }
                            Snackbar.make(v,"Volviendo al menu de inicio",Snackbar.LENGTH_SHORT).show()
                            val actionLoginNewUserFragmentToLoginUserFragment = LoginNewUserFragmentDirections.actionLoginNewUserFragmentToLoginUserFragment()
                            (v.findNavController().navigate(actionLoginNewUserFragmentToLoginUserFragment))

                        }
                        else{
                            Snackbar.make(v,"Verifique contraseña",Snackbar.LENGTH_SHORT).show()
                        }

                    }
                    else{
                        Snackbar.make(v,"Email en uso o Invalido",Snackbar.LENGTH_SHORT).show()
                    }
                }
                else{
                    Snackbar.make(v,"USER ID EN USO O INVALIDO",Snackbar.LENGTH_SHORT).show()
                }
            }else{
                Snackbar.make(v, "Los Campos Email/UserID no pueden estar vacios",Snackbar.LENGTH_SHORT)
            }

        }
        bt_login_newuser_cancel.setOnClickListener{
            //RoomExplorer.show(context, appDatabase::class.java, "myDB")
            val actionLoginNewUserFragmentToLoginUserFragment = LoginNewUserFragmentDirections.actionLoginNewUserFragmentToLoginUserFragment()
            (v.findNavController().navigate(actionLoginNewUserFragmentToLoginUserFragment))

        }
    }

}
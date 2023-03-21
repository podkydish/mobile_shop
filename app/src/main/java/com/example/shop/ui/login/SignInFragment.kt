package com.example.shop.ui.login

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.shop.databinding.SigninFragmentBinding
import com.example.shop.logic.DataBase
import com.example.shop.ui.LoginActivity

class SignInFragment : Fragment() {

    private var _binding: SigninFragmentBinding? = null

    private lateinit var email: EditText
    private lateinit var firstname: EditText
    private lateinit var lastname: EditText
    private lateinit var signIn: Button
    private lateinit var googleSign: Button


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = SigninFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firstname = binding.firstName
        lastname = binding.lastName
        googleSign = binding.signGoogle
        email = binding.email
        email.addTextChangedListener {
            if (!email.text.matches("^[-\\w.]+@([A-z\\d][-A-z\\d]+\\.)+[A-z]{2,4}\$".toRegex())) {
                email.setTextColor(Color.RED)
            } else {
                email.setTextColor(Color.BLACK)
            }
        }
        signIn = binding.signIn
        signIn.setOnClickListener {
            if (email.text.isNotEmpty() && firstname.text.isNotEmpty() && lastname.text.isNotEmpty()) {

                val dbHelper = DataBase(requireContext())

                val cursor = dbHelper.getAllData()
                var found = false
                if (cursor.moveToFirst()) {
                    do {
                        if (cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_NAME)) == firstname.text.toString() && cursor.getString(
                                cursor.getColumnIndex(DataBase.COLUMN_SURNAME)
                            ) == lastname.text.toString() && cursor.getString(
                                cursor.getColumnIndex(
                                    DataBase.COLUMN_EMAIL
                                )
                            ) == email.text.toString()
                        ) {
                            Toast.makeText(
                                requireContext(),
                                "Такой пользователь уже существует",
                                Toast.LENGTH_SHORT
                            ).show()
                            found = true
                            break
                        }
                    } while (cursor.moveToNext())
                }
                if (!found) {
                    dbHelper.insertData(
                        firstname.text.toString(),
                        lastname.text.toString(),
                        email.text.toString()
                    )
                    (activity as LoginActivity).toMain()
                }


            }
        }

        val login: TextView = binding.textView4
        login.setOnClickListener {
            val nextFrag = LoginFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this@SignInFragment)
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        googleSign.setOnClickListener {
            (activity as LoginActivity).toMain()
        }
        return root
    }
}
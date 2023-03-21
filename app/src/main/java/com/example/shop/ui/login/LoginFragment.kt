package com.example.shop.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shop.databinding.LoginFragmentBinding
import com.example.shop.logic.DataBase
import com.example.shop.ui.LoginActivity


class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var firstname: EditText
    private lateinit var lastname: EditText

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        firstname = binding.firstName
        lastname = binding.lastName
        val toSign: TextView = binding.textView4
        val loginBtn: Button = binding.login
        toSign.setOnClickListener {
            val nextFrag = SignInFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this@LoginFragment)
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        loginBtn.setOnClickListener {
            var found = false
            val dbHelper = DataBase(requireContext())
            val cursor = dbHelper.getAllData()
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_NAME)) == firstname.text.toString() && cursor.getString(
                            cursor.getColumnIndex(DataBase.COLUMN_SURNAME)
                        ) == lastname.text.toString()
                    ) {
                        (activity as LoginActivity).toMain()
                        found = true
                    }
                } while (cursor.moveToNext())
            }
            if (!found) {
                Toast.makeText(context, "Такого пользователя не существует", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return root
    }
}
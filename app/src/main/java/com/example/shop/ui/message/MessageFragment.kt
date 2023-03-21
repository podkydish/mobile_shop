package com.example.shop.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shop.databinding.MessageFragmentBinding

class MessageFragment : Fragment() {
    private var _binding: MessageFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MessageFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
}
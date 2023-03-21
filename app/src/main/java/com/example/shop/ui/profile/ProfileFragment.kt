package com.example.shop.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.shop.ui.MainActivity
import com.example.shop.R
import com.example.shop.databinding.ProfileFragmentBinding

import com.example.shop.ui.home.HomeFragment


class ProfileFragment : Fragment() {
    private var _binding: ProfileFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var profileImage: ImageView
    private lateinit var actionBtn: ImageButton
    private lateinit var actionText: TextView
    private lateinit var logOut: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        (activity as MainActivity).supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        val colorDrawable = ColorDrawable(Color.parseColor("#FFFFFFFF"))
        (activity as MainActivity).supportActionBar!!.setBackgroundDrawable(colorDrawable)
        // Displaying the custom layout in the ActionBar
        (activity as MainActivity).supportActionBar!!.setDisplayShowCustomEnabled(true)
        (activity as MainActivity).supportActionBar!!.setCustomView(R.layout.action_bar)


        logOut = binding.logOut

        actionBtn = requireActivity().findViewById(R.id.action_button)

        actionBtn.visibility = View.VISIBLE
        actionText = requireActivity().findViewById(R.id.action_text)
        actionText.text = "Profile"
        logOut.setOnClickListener {
            (activity as MainActivity).toSign()
        }
        actionBtn.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, HomeFragment()).commit()
            actionBtn.visibility = View.INVISIBLE
            actionText.text = ""
        }
        profileImage = binding.imageView
        //BUTTON CLICK
        profileImage.setOnClickListener {
            //check runtime permission
            if (checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) ==
                PermissionChecker.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                //permission already granted
                pickImageFromGallery();
            }
        }


        return root
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000;

        //Permission code
        private const val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(
                        this.requireContext(),
                        "Permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    //handle result of picked image
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            profileImage.setImageURI(data?.data)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
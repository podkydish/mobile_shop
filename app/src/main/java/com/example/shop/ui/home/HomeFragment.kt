package com.example.shop.ui.home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

import com.example.shop.R
import com.example.shop.databinding.FragmentHomeBinding
import com.example.shop.ui.MainActivity

import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.File

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var saleLinear: LinearLayout
    private lateinit var latestLinear: LinearLayout

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        (activity as MainActivity).supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        val colorDrawable = ColorDrawable(Color.parseColor("#FFFFFFFF"))
        (activity as MainActivity).supportActionBar!!.setBackgroundDrawable(colorDrawable)
        // Displaying the custom layout in the ActionBar
        (activity as MainActivity).supportActionBar!!.setDisplayShowCustomEnabled(true)
        (activity as MainActivity).supportActionBar!!.setCustomView(R.layout.home_action_bar)
        latestLinear = binding.latestLinear



        saleLinear = binding.saleLinear
        if (File(
                requireContext().filesDir,
                "latest.json"
            ).exists() && File(requireContext().filesDir, "sale.json").exists()
        ) {
            val file = File(requireContext().filesDir, "latest.json")
            val jsonString = file.readText()
            val jsonObject = JSONObject(jsonString)
            val jsonArray = jsonObject.getJSONArray("latest")
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val latestCard = inflater.inflate(R.layout.product_card, null) as CardView
                val imageView = latestCard.findViewById<ImageView>(R.id.product_image)
                val productName = latestCard.findViewById<TextView>(R.id.product_name)
                val productPrice = latestCard.findViewById<TextView>(R.id.product_price)
                val imageUrl = jsonObject.getString("image_url")
                productName.text = jsonObject.getString("name")
                productPrice.text = jsonObject.getString("price")
                Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            // Do something on success
                        }

                        override fun onError(e: Exception?) {
                            Log.e(TAG, "Error loading image: ${e?.message}")
                        }
                    })
                latestLinear.addView(latestCard)
            }
            val file2 = File(requireContext().filesDir, "sale.json")
            val jsonString2 = file2.readText()
            val jsonObject2 = JSONObject(jsonString2)
            val jsonArray2 = jsonObject2.getJSONArray("flash_sale")
            for (i in 0 until jsonArray2.length()) {
                val jsonObject = jsonArray2.getJSONObject(i)
                val saleCard = inflater.inflate(R.layout.product_sale, null) as CardView
                saleCard.id = i
                val imageView = saleCard.findViewById<ImageView>(R.id.sale_image)
                val productName = saleCard.findViewById<TextView>(R.id.sale_name)
                val productPrice = saleCard.findViewById<TextView>(R.id.sale_price)
                val productSale = saleCard.findViewById<TextView>(R.id.product_sale)
                val imageUrl = jsonObject.getString("image_url")
                productName.text = jsonObject.getString("name")
                productPrice.text = jsonObject.getString("price")
                productSale.text = jsonObject.getString("discount") + "% off"
                Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            // Do something on success
                        }

                        override fun onError(e: Exception?) {
                            Log.e(TAG, "Error loading image: ${e?.message}")
                        }
                    })
                saleLinear.addView(saleCard)
            }
        } else {
            val file = File(requireContext().filesDir, "latest.json")
            file.writeText(
                """{
                "latest": [
                {
                    "category": "Phones",
                    "name": "Samsung S10",
                    "price": 1000,
                    "image_url": "https://www.dhresource.com/0x0/f2/albu/g8/M01/9D/19/rBVaV1079WeAEW-AAARn9m_Dmh0487.jpg"
                },
                {
                    "category": "Games",
                    "name": "Sony Playstation 5",
                    "price": 700,
                    "image_url": "https://avatars.mds.yandex.net/get-mpic/6251774/img_id4273297770790914968.jpeg/orig"
                },
                {
                    "category": "Games",
                    "name": "Xbox ONE",
                    "price": 500,
                    "image_url": "https://www.tradeinn.com/f/13754/137546834/microsoft-xbox-xbox-one-s-1tb-console-additional-controller.jpg"
                },
                {
                    "category": "Cars",
                    "name": "BMW X6M",
                    "price": 35000,
                    "image_url": "https://mirbmw.ru/wp-content/uploads/2022/01/manhart-mhx6-700-01.jpg"
                }
                ]
            }"""
            )

            val file2 = File(requireContext().filesDir, "sale.json")
            file2.writeText(
                """{
  "flash_sale": [
    {
      "category": "Kids",
      "name": "New Balance Sneakers",
      "price": 22.5,
      "discount": 30,
      "image_url": "https://newbalance.ru/upload/iblock/697/iz997hht_nb_02_i.jpg"
    },
    {
      "category": "Kids",
      "name": "Reebok Classic",
      "price": 24,
      "discount": 30,
      "image_url": "https://assets.reebok.com/images/h_2000,f_auto,q_auto,fl_lossy,c_fill,g_auto/3613ebaf6ed24a609818ac63000250a3_9366/Classic_Leather_Shoes_-_Toddler_White_FZ2093_01_standard.jpg"
    }
  ]
}"""
            )
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
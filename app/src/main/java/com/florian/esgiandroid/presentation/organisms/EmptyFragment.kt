package com.florian.esgiandroid.presentation.organisms

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.florian.esgiandroid.R


class EmptyFragment : Fragment() {

    companion object{
        val TAG = this.toString()
    }

    private lateinit var myView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_empty, container, false)
        myView = v
        onClickListener(v)
        return v
    }


    private fun onClickListener(view : View) {
        val button = view.findViewById<Button>(R.id.fragment_empty_button)
        button.setOnClickListener {
            val intent = Intent()
            intent.action = "com.google.zxing.client.android.SCAN"
            intent.putExtra("SCAN_FORMATS", "EAN_13")
            getResult.launch(intent)
        }
    }


    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            when(it.resultCode){
                Activity.RESULT_CANCELED -> Log.w(TAG, "Scan cancelled !")
                Activity.RESULT_OK -> {
                    val format = it.data?.getStringExtra("SCAN_RESULT_FORMAT")
                    val res = it.data?.getStringExtra("SCAN_RESULT")
                    Toast.makeText(context,  "New code : $res", Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "Scanned, bar code is $res")
                    val action = EmptyFragmentDirections.actionEmptyFragmentToProductsFragment(res!!)
                    myView.findNavController().navigate(action)
                }
            }
        }

}

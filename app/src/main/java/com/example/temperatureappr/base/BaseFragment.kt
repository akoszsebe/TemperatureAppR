package com.example.temperatureappr.base

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.temperatureappr.utils.LoaderDialog
import com.example.temperatureappr.utils.SharedPrefs

abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel>(private var layoutResId: Int) : Fragment() {
    lateinit var sharedPrefs: SharedPrefs
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    private lateinit var progressBar : Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        sharedPrefs = SharedPrefs(this.requireContext())
        progressBar = LoaderDialog.buildLoader(this.requireContext())
        return binding.root
    }

    fun showToastMessage(message: String?){
        Toast.makeText(this.requireContext(),message, Toast.LENGTH_SHORT).show()
    }

    fun showDialog(message: String?) {
        AlertDialog.Builder(this.requireContext())
            .setTitle("Info")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    fun showErrorDialog(message: String?) {
        AlertDialog.Builder(this.requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    fun showDialogNoGps() {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                startActivityForResult(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    , 11)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    fun showLoader(){
        progressBar.show()
    }

    fun hideLoader(){
        progressBar.dismiss()
    }
}
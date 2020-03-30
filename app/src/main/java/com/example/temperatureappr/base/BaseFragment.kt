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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.temperatureappr.di.module.util.viewModel
import com.example.temperatureappr.utils.LoaderDialog
import com.example.temperatureappr.utils.SharedPrefs
import org.koin.core.qualifier.TypeQualifier
import kotlin.reflect.KClass


abstract class BaseFragment<B : ViewDataBinding, out VM : ViewModel>(
    private var layoutResId: Int,
    vmClass: KClass<VM>
) : Fragment() {
    lateinit var sharedPrefs: SharedPrefs
    protected lateinit var binding: B
    protected val viewModel: VM by viewModel(vmClass, qualifier = TypeQualifier(vmClass))
    private lateinit var progressBar: Dialog

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

    abstract fun initViewModel()

    fun showToastMessage(message: String?) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
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
            .setPositiveButton("Yes") { _, _ ->
                startActivityForResult(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    , 11
                )
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    fun setToolbar(toolbar: Toolbar?, showHomeButton: Boolean) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(showHomeButton)
            it.setDisplayShowHomeEnabled(showHomeButton)
        }
    }

    fun showLoader() {
        progressBar.show()
    }

    fun hideLoader() {
        progressBar.dismiss()
    }
}
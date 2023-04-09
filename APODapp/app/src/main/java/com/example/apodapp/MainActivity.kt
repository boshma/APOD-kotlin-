package com.example.apodapp

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.apodapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory(applicationContext)).get(MainViewModel::class.java)
    }

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.apodData.observe(this, Observer { apodEntity ->
            binding.titleTextView.text = apodEntity.title
            binding.descriptionTextView.text = apodEntity.explanation
            binding.copyrightTextView.text = apodEntity.copyright ?: "Copyright not available"

            Glide.with(this)
                .load(apodEntity.url)
                .into(binding.apodImageView)

            if (apodEntity.media_type == "video") {
                Toast.makeText(this, "Video not supported", Toast.LENGTH_SHORT).show()
            }
        })


        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        binding.selectDateButton.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val date = Calendar.getInstance()
                date.set(year, month, dayOfMonth)
                if (date.timeInMillis > System.currentTimeMillis()) {
                    Toast.makeText(this, "Cannot select future dates", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.fetchApodData(dateFormat.format(date.time))
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}

class MainViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }
}


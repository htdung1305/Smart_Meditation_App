package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Scanface : AppCompatActivity() {

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var capturedImageView: ImageView
    private lateinit var scanboxButton: Button

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanface)

        scanboxButton = findViewById(R.id.scanbox_button)
        capturedImageView = findViewById(R.id.captured_image)

        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                capturedImageView.setImageBitmap(imageBitmap) // Hiển thị hình ảnh trong ImageView
                capturedImageView.visibility = View.VISIBLE // Hiển thị ImageView
                scanboxButton.visibility = View.INVISIBLE // Ẩn Button nếu cần
            }
        }

        scanboxButton.setOnClickListener {
            val intent = Intent(this@Scanface, FaceVerification::class.java)
            startActivity(intent)
        }
    }
}

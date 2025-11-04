
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
import com.example.myapplication.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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
                capturedImageView.setImageBitmap(imageBitmap)
                capturedImageView.visibility = View.VISIBLE
                scanboxButton.visibility = View.INVISIBLE
                saveImageToExternalStorage(imageBitmap)
            }
        }

        scanboxButton.setOnClickListener {
            if (checkCameraPermission()) {
                openCamera()
            } else {
                requestCameraPermission()
            }
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            cameraLauncher.launch(intent)
        }
    }

    private fun saveImageToExternalStorage(imageBitmap: Bitmap) {
        val filename = "captured_image.jpg"
        val storageDir = File(getExternalFilesDir(null), "input_image")
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        val file = File(storageDir, filename)
        try {
            val fos = FileOutputStream(file)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            // Sau khi lưu, thực hiện sao chép ảnh vào máy tính
            copyImageToPC(file.absolutePath, "D:/MyApplication/app/application_data/input_image/")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun copyImageToPC(sourceFilePath: String, destinationDirPath: String) {
        try {
            // Lệnh adb pull để sao chép file từ máy ảo sang máy tính
            val command = "adb pull $sourceFilePath $destinationDirPath"
            val process = Runtime.getRuntime().exec(command)
            process.waitFor()
            // Xác nhận rằng file đã được sao chép
            println("Image copied to PC: $destinationDirPath")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                // Xử lý khi người dùng từ chối quyền
            }
        }
    }
}

package com.example.challenge.view.screen

import android.Manifest
import android.content.Intent
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge.R
import com.example.challenge.databinding.ActivityRecordBinding
import com.example.challenge.view.SendVideoActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import java.text.SimpleDateFormat

class RecordActivity : AppCompatActivity(), SurfaceHolder.Callback {
    private lateinit var binding: ActivityRecordBinding

    private lateinit var camera: Camera
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var surfaceHolder: SurfaceHolder
    private var record: Boolean = false
    private var mCameraFacing: Int = Camera.CameraInfo.CAMERA_FACING_BACK

    private var path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPermission()
        recordLogic()
        surfaceHolder = binding.surfaceView.holder
        binding.exitRecord.setOnClickListener { finish() }
        binding.changeMode.setOnClickListener {
            mCameraFacing =
                if (mCameraFacing === Camera.CameraInfo.CAMERA_FACING_BACK)
                    Camera.CameraInfo.CAMERA_FACING_FRONT
                else Camera.CameraInfo.CAMERA_FACING_BACK

            camera = Camera.open()
            camera.setDisplayOrientation(90)
            surfaceHolder.addCallback(this@RecordActivity)
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        }
    }

    private fun recordLogic() {
        binding.recordButton.setOnClickListener {
            if (record) {
                mediaRecorder.stop()
                mediaRecorder.release()
                camera.lock()
                record = false

                setRecordImage()
                startActivity(Intent(this, SendVideoActivity::class.java)
                    .putExtra("path", path)
                    .putExtra("auth", intent.getStringExtra("token")))
            } else {
                try {
                    mediaRecorder = MediaRecorder()
                    camera.unlock()
                    mediaRecorder.setCamera(camera)
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)   // 녹화 소리
                    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA)
                    mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P))  // 해상도
                    mediaRecorder.setOrientationHint(90)    // 촬영각도

                    val currentTime = System.currentTimeMillis()
                    val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
                    mediaRecorder.setOutputFile("$path/${dateFormat.format(currentTime)}.mp4")
                    mediaRecorder.setPreviewDisplay(surfaceHolder.surface)
                    mediaRecorder.prepare()
                    mediaRecorder.start()
                    record = true

                    path = "$path/${dateFormat.format(currentTime)}.mp4"
                    setRecordImage()
                } catch (e: Exception) {
                    e.printStackTrace()
                    mediaRecorder.release()
                }
            }
        }
    }

    private fun setRecordImage() {
        if (record) binding.recordButton.setImageResource(R.drawable.record_stop)
        else binding.recordButton.setImageResource(R.drawable.record_start)
    }

    private val permission = object :PermissionListener {
        override fun onPermissionGranted() {    // 권한이 허용되었을 때
            Log.d("permission", "onPermissionGranted: permission checked.")

            camera = Camera.open()
            camera.setDisplayOrientation(90)
            surfaceHolder.addCallback(this@RecordActivity)
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        }

        // 권한이 허용되지 않았을때
        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            Log.d("permission", "onPermissionDenied: permission denied.")
        }
    }

    private fun setPermission() {
        TedPermission.create()
            .setPermissionListener(permission)
            .setRationaleMessage("녹화를 위하여 권한을 허용해주세요.")
            .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용해주세요.")
            .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
            .check()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {    // surface view 가 생성되었을때

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        refreshCamera(camera)
    }

    private fun refreshCamera(camera: Camera) {
        if (surfaceHolder.surface == null) {
            return
        }

        try {
            camera.stopPreview()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setCamera(camera)
    }

    private fun setCamera(cam: Camera) {
        camera = cam
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d("TAG", "surfaceDestroyed: surface destroyed")
    }
}
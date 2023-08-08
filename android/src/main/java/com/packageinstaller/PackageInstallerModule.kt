package com.packageinstaller

import android.app.PendingIntent
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.facebook.react.bridge.*
import java.io.*

class PackageInstallerModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private val mDevicePolicyManager = reactContext.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

  private val isAdmin by lazy { mDevicePolicyManager.isDeviceOwnerApp(reactApplicationContext.packageName) }

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun install(path: String, packageName: String? = reactApplicationContext.packageName, promise: Promise) {
    if (isAdmin) {
      val apkFile = File(path)

      val packageInstaller = reactApplicationContext.packageManager.packageInstaller
      val packageParams = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
      packageParams.setAppPackageName(packageName)

      val sessionId = packageInstaller.createSession(packageParams)
      val session = packageInstaller.openSession(sessionId)
      val outputStream = session.openWrite("myapp", 0, -1)
      val fileInputStream = FileInputStream(apkFile)

      val buffer = ByteArray(4096)
      var len = fileInputStream.read(buffer)
      while (len > 0) {
        outputStream.write(buffer, 0, len)
        len = fileInputStream.read(buffer)
      }

      fileInputStream.close()
      outputStream.close()

      session.commit(PendingIntent.getBroadcast(reactApplicationContext, sessionId, Intent("android.intent.action.INSTALL_PACKAGE"), 0).intentSender)
    } else {
     val intent = Intent(Intent.ACTION_VIEW)
     intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK


     val file = File(path)
     var fileUri = Uri.fromFile(file)


     if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
       fileUri = FileProvider.getUriForFile(reactApplicationContext, reactApplicationContext.packageName + ".fileprovider",
         file)
     }

     intent.setDataAndType(fileUri, "application/vnd.android" + ".package-archive")
     intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
     reactApplicationContext.startActivity(intent)
    }
    promise.resolve(true)
  }

  companion object {
    const val NAME = "PackageInstaller"
  }
}

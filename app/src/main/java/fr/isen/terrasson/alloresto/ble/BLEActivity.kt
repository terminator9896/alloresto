package fr.isen.terrasson.alloresto.ble

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import fr.isen.terrasson.alloresto.R
import fr.isen.terrasson.alloresto.databinding.ActivityBLEBinding
import android.Manifest
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.os.Handler

class BLEActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBLEBinding
    private var isScanning = false

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private var bluetoothLeScanner: BluetoothLeScanner? = null
    private var scanning = false
    private val handler = Handler()
    private val SCAN_PERIOD: Long = 10000

    val bluetoothAdapter: BluetoothAdapter? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBLEBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter
        bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner


        startBLEIfok()

        binding.imageButton.setOnClickListener {
            togglePlayPauseAction()
        }


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startBLEIfok() {
        when {
            !isDeviceHasBLESupport() || bluetoothAdapter == null -> {
                Toast.makeText(this, "Cet appareil n'est pas compatible avec BLE", Toast.LENGTH_SHORT).show()
                finish()
            }
            !(bluetoothAdapter?.isEnabled ?: false) -> {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ->{
                ActivityCompat.requestPermissions( this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
            }
            else -> {
                scanLeDevice()

            }

        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK)
        {
            startBLEIfok()
        }
    }

    private val leDeviceListAdapter = LeDeviceListAdapter()
    private val leScanCallback: ScanCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            leDeviceListAdapter.addDevice(result.device)
            leDeviceListAdapter.notifyDataSetChanged()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun scanLeDevice() {
        bluetoothLeScanner?.let { scanner ->
            if (!scanning) { // Stops scanning after a pre-defined scan period.
                handler.postDelayed({
                    scanning = false
                    scanner.stopScan(leScanCallback)
                }, SCAN_PERIOD)
                scanning = true
                scanner.startScan(leScanCallback)
            } else {
                scanning = false
                scanner.stopScan(leScanCallback)
            }
        }
    }

    private fun isDeviceHasBLESupport() : Boolean =
        packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)


    private fun togglePlayPauseAction(){
        isScanning = !isScanning

        if(isScanning)
        {
            binding.scanBLE.text = getString(R.string.scan_play_ble)
            binding.imageButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            binding.progressBarBLE.isVisible = false
        }
        else
        {
            binding.scanBLE.text = getString(R.string.scan_scan_ble)
            binding.imageButton.setImageResource(R.drawable.ic_baseline_pause_24)
            binding.progressBarBLE.isVisible = true
        }
    }
    companion object{
        const private val REQUEST_ENABLE_BT = 33
        const private val REQUEST_PERMISSION_LOCATION = 22

    }
}
package fr.isen.terrasson.alloresto.ble

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.terrasson.alloresto.R
import fr.isen.terrasson.alloresto.databinding.ActivityBLEBinding



//request id
const val BLE__REQUEST_ENABLE = 1

//scan period (for timeout call)
const val BLE__SCAN_PERIOD: Long = 10000



class BLEActivity : AppCompatActivity() {



    //binding
    private lateinit var binding : ActivityBLEBinding

    //BLE info
    private var isScanning : Boolean = false
    private var BLEavailable: Boolean = false
    private lateinit var BLEManager: BluetoothManager
    private var BLEAdapter: BluetoothAdapter? = null
    private var BLEScanner: BluetoothLeScanner? = null
    private lateinit var BLEHandler : Handler
    private var BLEScanList : MutableList<ScanResult> = mutableListOf()






    // INITIALIZATION
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        // LAYOUT

        //init binding instance
        binding = ActivityBLEBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //bind scan button
        binding.imageButton.setOnClickListener {
            if(isScanning){
                BLEStopScan()
            }else{
                BLEStartScan()
            }
        }

        //bind also title
        binding.scanBLE.setOnClickListener {
            if(isScanning){
                BLEStopScan()
            }else{
                BLEStartScan()
            }
        }



        //BLE

        //set BLE variables
        setBLEVariables()

        //check BLE availability
        if(!BLEavailable){
            Toast.makeText(this, "BLE is not available for this device", Toast.LENGTH_SHORT).show()
            Log.i("","ERROR : BLE is not available for this device.")
        }else{
            //debug
            Toast.makeText(this, "Ble is available.", Toast.LENGTH_SHORT).show()

            //check if BLE is enable
            promptEnableBluetooth()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setBLEVariables() {
        BLEManager = getSystemService(BluetoothManager::class.java)
        BLEAdapter = BLEManager?.adapter
        BLEavailable = (
            packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) &&
                    BLEManager != null
            )
        BLEScanner = BLEAdapter?.bluetoothLeScanner
        BLEHandler = Handler()
    }



    // PERMISSIONS
    private fun promptEnableBluetooth() {
        if(! (BLEAdapter!!.isEnabled) ){
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, BLE__REQUEST_ENABLE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            BLE__REQUEST_ENABLE -> {
                if (resultCode != Activity.RESULT_OK) {
                    promptEnableBluetooth()
                }
            }
        }
    }



    // SCAN

    //callback
    private val BLEScanCallback: ScanCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)

            if (!result.scanRecord?.deviceName.isNullOrEmpty()) {
                //check if device already exists in mutable list
                var deviceFound = false
                BLEScanList.forEachIndexed { idx, sr ->
                    //device already registered

                    if (sr.device.address == result.device.address) {
                        BLEScanList[idx] = result
                        deviceFound = true
                        Log.i("bleDevice", "same device")
                    }
                }
                //add device
                if (!deviceFound) {
                    BLEScanList.add(result)
                    Log.i("bleDevice", "add device")
                }
                BLEUpdateRecView()
            }
        }
    }

    //start - stop
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun BLEStartScan() {

        //display
        binding.scanBLE.text = getString(R.string.scan_pause_ble);
        binding.imageButton.setImageResource(R.drawable.ic_baseline_pause_24)
        binding.progressBarBLE.visibility = View.VISIBLE
        BLEScanList = mutableListOf()
        //launch scanner
        BLEScanner?.let { scanner ->
            isScanning = true

            //launch timer
            BLEHandler.postDelayed(
                {
                    //scan timed out
                    BLEStopScan()
                },
                BLE__SCAN_PERIOD
            )

            //start scan
            scanner.startScan(BLEScanCallback)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun BLEStopScan(){

        //display
        binding.scanBLE.text = getString(R.string.scan_play_ble);
        binding.imageButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        binding.progressBarBLE.visibility = View.INVISIBLE

        //stop scanner
        BLEScanner?.let { scanner ->
            isScanning = false

            //stop scan
            scanner.stopScan(BLEScanCallback)
        }
    }



    // DISPLAY
    private fun BLEUpdateRecView(){

        //update recycler view
        binding.recycleBLE.layoutManager = LinearLayoutManager(this)
        binding.recycleBLE.adapter = DeviceAdapter(
            BLEScanList
        ) { result ->
            val intent = Intent(this, BLEDetailsActivity::class.java)
            intent.putExtra("BLEDeviceInfo", result.device)
            startActivity(intent)
        }
    }
}

package fr.isen.terrasson.alloresto.ble

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import fr.isen.terrasson.alloresto.R
import fr.isen.terrasson.alloresto.databinding.ActivityBLEDetailsBinding



class BLEDetailsActivity : AppCompatActivity() {

    //binding
    private lateinit var binding : ActivityBLEDetailsBinding
    var bluetoothGatt: BluetoothGatt? = null

    //init
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //init binding instance
        binding = ActivityBLEDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get device info
        val device = intent.getParcelableExtra<BluetoothDevice>("BLEDeviceInfo")

        //display device info
        binding.deviceDetailBle.text = device?.name ?: "Appareil inconnue"
        binding.deviceStatut.text = getString(R.string.ble_device_status, getString(R.string.ble_device_status_connecting))


        connectToDevice(device)

    }
    private fun connectToDevice(device: BluetoothDevice?)
    {
        bluetoothGatt = device?.connectGatt(this, false, object : BluetoothGattCallback(){
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                super.onConnectionStateChange(gatt,status,newState)
                connectionStateChange(newState, gatt)
            }


            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {

            }

            override fun onCharacteristicRead(gatt: BluetoothGatt,
                                              characteristic: BluetoothGattCharacteristic,
                                              status: Int) {

            }
        })

    }
    private fun connectionStateChange(newState: Int, gatt: BluetoothGatt?){
        BLEConnexionState.getBLEConnexionStateFromState(newState)?.let {
            runOnUiThread {
                binding.deviceStatut.text = getString(R.string.ble_device_status, getString(it.text))
            }
        }
    }
}
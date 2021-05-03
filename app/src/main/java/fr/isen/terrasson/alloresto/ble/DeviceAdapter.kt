package fr.isen.terrasson.alloresto.ble

import android.bluetooth.le.ScanResult
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.terrasson.alloresto.R
import fr.isen.terrasson.alloresto.databinding.LyoCellBleBinding

//binding
private lateinit var binding : LyoCellBleBinding




class DeviceAdapter(
    private val scanList : MutableList<ScanResult>,
    private val onItemClickListener:(ScanResult) -> Unit
) : RecyclerView.Adapter<DeviceAdapter.BLEScanViewHolder>() {

    //init
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : DeviceAdapter.BLEScanViewHolder {

        //debug
        Log.i("", "Created RecyclerViewCell.")
        return BLEScanViewHolder(
            LyoCellBleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    //viewHolder
    class BLEScanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cellTitle   : TextView = view.findViewById(R.id.device_ble)
        val cellContent : TextView = view.findViewById(R.id.detail_ble)
        val cellName    : TextView = view.findViewById(R.id.device_name)
    }

    //utilities
    override fun getItemCount() = scanList.size
    //events
    override fun onBindViewHolder(holder: BLEScanViewHolder, position: Int) {
        holder.cellTitle.text   = scanList[position].device.address
        if(scanList[position].scanRecord?.txPowerLevel != null){
            holder.cellContent.text = scanList[position].scanRecord?.txPowerLevel.toString()
            Log.i( " " ,scanList[position].scanRecord?.txPowerLevel.toString())

        }
        else{
            holder.cellContent.text = " "
        }
        holder.cellName.text = scanList[position].scanRecord?.deviceName
        holder.cellTitle.setOnClickListener(){
            onItemClickListener(scanList[position])
        }
    }
}
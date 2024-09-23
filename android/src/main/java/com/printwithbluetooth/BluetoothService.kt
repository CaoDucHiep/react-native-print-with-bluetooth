package com.printwithbluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import java.util.*


class BluetoothService {
  // Debugging
  private val TAG = "BluetoothService"
  private val DEBUG = true

  // Name for the SDP record when creating server socket
  private val NAME = "BTPrinter"

  //UUID must be this
  // Unique UUID for this application
  private val MY_UUID = UUID.fromString(
    "00001101-0000-1000-8000-00805F9B34FB"
  )

  // Member fields
  private var mAdapter: BluetoothAdapter? = null

  private var mConnectedThread: ConnectedThread? = null
  private var mState = 0

  // Constants that indicate the current connection state
  val STATE_NONE = 0 // we're doing nothing

  // public static final int STATE_LISTEN = 1;     // now listening for incoming connections //feathure removed.
  val STATE_CONNECTING = 2 // now initiating an outgoing connection

  val STATE_CONNECTED = 3 // now connected to a remote device


  val MESSAGE_STATE_CHANGE = 4
  val MESSAGE_READ = 5
  val MESSAGE_WRITE = 6
  val MESSAGE_DEVICE_NAME = 7
  val MESSAGE_CONNECTION_LOST = 8
  val MESSAGE_UNABLE_CONNECT = 9

  // Key names received from the BluetoothService Handler
  val DEVICE_NAME = "device_name"
  val DEVICE_ADDRESS = "device_address"
  val TOAST = "toast"

  var ErrorMessage = "No_Error_Message"

  private val observers: List<BluetoothServiceStateObserver> =
    ArrayList<BluetoothServiceStateObserver>()
  private val mLastConnectedDeviceAddress = ""

  fun BluetoothService(context: Context?) {
    mAdapter = BluetoothAdapter.getDefaultAdapter()
    mState = STATE_NONE
  }

  fun addStateObserver(observer: BluetoothServiceStateObserver?) {
    observers.add(observer)
  }

  fun removeStateObserver(observer: BluetoothServiceStateObserver?) {
    observers.remove(observer)
  }

  @Synchronized
  private fun setState(state: Int, bundle: Map<String, Any>) {
    if (DEBUG) Log.d(
      TAG,
      "setState() " + getStateName(mState).toString() + " -> " + getStateName(state)
    )
    mState = state
    infoObervers(state, bundle)
  }

  private fun getStateName(state: Int): String? {
    var name = "UNKNOW:$state"
    if (STATE_NONE == state) {
      name = "STATE_NONE"
    } else if (STATE_CONNECTED == state) {
      name = "STATE_CONNECTED"
    } else if (STATE_CONNECTING == state) {
      name = "STATE_CONNECTING"
    }
    return name
  }

  @Synchronized
  private fun infoObervers(code: Int, bundle: Map<String, Any>) {
    for (ob in observers) {
      ob.onBluetoothServiceStateChanged(code, bundle)
    }
  }

  /**
   * Return the current connection state.
   */
  //todo: get the method in react to check the current connection state
  @Synchronized
  fun getState(): Int {
    return mState
  }

  /**
   * Start the ConnectThread to initiate a connection to a remote device.
   *
   * @param device The BluetoothDevice to connect
   */
  @Synchronized
  fun connect(device: BluetoothDevice) {
    if (DEBUG) Log.d(TAG, "connect to: $device")
    var connectedDevice: BluetoothDevice? = null
    if (mConnectedThread != null) {
      connectedDevice = mConnectedThread.bluetoothDevice()
    }
    if (mState === STATE_CONNECTED && connectedDevice != null && connectedDevice.address == device.address) {
      // connected already
      val bundle: MutableMap<String, Any> = HashMap()
      bundle[DEVICE_NAME] = device.name
      bundle[DEVICE_ADDRESS] = device.address
      setState(STATE_CONNECTED, bundle)
    } else {
      // Cancel any thread currently running a connection
      this.stop()
      // Start the thread to manage the connection and perform transmissions
      mConnectedThread = ConnectedThread(device)
      mConnectedThread.start()
      setState(STATE_CONNECTING, null)
    }
  }
}

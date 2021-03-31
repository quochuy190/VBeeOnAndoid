package com.vn.vbeeon.utils.discovery;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/* renamed from: com.google.android.tv.support.remote.discovery.BluetoothAgent */
/* compiled from: Unknown */
public class BluetoothAgent extends DiscoveryAgent {
    private static final int CANCEL_DISCOVERY_MSG = 1;
    private static final boolean DEBUG = false;
    public static final int DISCOVERY_FAILED_BLUETOOTH_DISABLED = -2;
    public static final int DISCOVERY_FAILED_NO_BLUETOOTH_ADAPTER = -1;
    private static final int DISCOVERY_TIMEOUT_MS = 5000;
    private static final int FETCH_UUID_MSG = 2;
    private static final String TAG = "AtvRemote.BluetoothAgnt";
    /* access modifiers changed from: private */
    public final BluetoothAdapter mBluetoothAdapter;
    /* access modifiers changed from: private */
    public Set<BluetoothDevice> mCached = new HashSet();
    /* access modifiers changed from: private */
    public final Handler mCallbackHandler = new Handler(Looper.getMainLooper());
    private final Context mContext;
    /* access modifiers changed from: private */
    public boolean mDiscovering = false;
    /* access modifiers changed from: private */
    public final Handler mHandler;
    private final HandlerThread mHandlerThread = new HandlerThread(TAG);
    /* access modifiers changed from: private */
    public Set<BluetoothDevice> mKnown = new HashSet();
    /* access modifiers changed from: private */
    public CountDownLatch mLatch;
    /* access modifiers changed from: private */
    public Listener mListener;
    /* access modifiers changed from: private */
    public List<BluetoothDevice> mPending;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.bluetooth.adapter.action.DISCOVERY_STARTED".equals(action)) {
                BluetoothAgent.this.mCallbackHandler.post(new Runnable() {
                    public void run() {
                        if (BluetoothAgent.this.mListener != null) {
                            BluetoothAgent.this.mListener.onDiscoveryStarted();
                        }
                    }
                });
            } else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                BluetoothAgent.this.mDiscovering = false;
                BluetoothAgent.this.mHandler.removeMessages(1);
                BluetoothAgent.this.mHandler.sendEmptyMessage(2);
            } else if ("android.bluetooth.device.action.FOUND".equals(action)) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (bluetoothDevice == null) {
                    Log.e(BluetoothAgent.TAG, "Device is null for ACTION_FOUND");
                } else if (bluetoothDevice.getBluetoothClass() != null) {
                    if (bluetoothDevice.getBluetoothClass().getMajorDeviceClass() == 1024) {
                        if (!BluetoothAgent.this.mCached.contains(bluetoothDevice)) {
                            BluetoothAgent.this.mPending.add(bluetoothDevice);
                        } else {
                            BluetoothAgent.this.mKnown.add(bluetoothDevice);
                            final BluetoothDeviceInfo bluetoothDeviceInfo = new BluetoothDeviceInfo(bluetoothDevice);
                            BluetoothAgent.this.mCallbackHandler.post(new Runnable() {
                                public void run() {
                                    if (BluetoothAgent.this.mListener != null) {
                                        BluetoothAgent.this.mListener.onDeviceFound(bluetoothDeviceInfo);
                                    }
                                }
                            });
                        }
                    }
                    BluetoothAgent.this.mHandler.removeMessages(1);
                    BluetoothAgent.this.mHandler.sendEmptyMessageDelayed(1, 5000);
                } else {
                    Log.e(BluetoothAgent.TAG, "Device's Bluetooth class is null");
                }
            } else if ("android.bluetooth.device.action.UUID".equals(action)) {
                BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (bluetoothDevice2 != null && BluetoothAgent.this.mResolving != null && TextUtils.equals(bluetoothDevice2.getAddress(), BluetoothAgent.this.mResolving.getAddress())) {
                    if (!BluetoothAgent.this.mDiscovering) {
                        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("android.bluetooth.device.extra.UUID");
                        if (parcelableArrayExtra != null) {
                            for (Parcelable obj : parcelableArrayExtra) {
                                if (BluetoothConstants.MY_UUID.equals(UUID.fromString(obj.toString().toUpperCase()))) {
                                    BluetoothAgent.this.mKnown.add(bluetoothDevice2);
                                    final BluetoothDeviceInfo bluetoothDeviceInfo2 = new BluetoothDeviceInfo(bluetoothDevice2);
                                    BluetoothAgent.this.mCallbackHandler.post(new Runnable() {
                                        public void run() {
                                            if (BluetoothAgent.this.mListener != null) {
                                                BluetoothAgent.this.mListener.onDeviceFound(bluetoothDeviceInfo2);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                    BluetoothAgent.this.mLatch.countDown();
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public BluetoothDevice mResolving;

    public BluetoothAgent(Context context) {
        this.mContext = context;
        this.mPending = new ArrayList();
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
        intentFilter.addAction("android.bluetooth.device.action.UUID");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper()) {
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        if (BluetoothAgent.this.mBluetoothAdapter.isDiscovering()) {
                            BluetoothAgent.this.mBluetoothAdapter.cancelDiscovery();
                        }
                        BluetoothAgent.this.mDiscovering = false;
                        break;
                    case 2:
                        if (BluetoothAgent.this.mDiscovering) {
                            return;
                        }
                        if (!BluetoothAgent.this.mPending.isEmpty()) {
                            BluetoothAgent.this.mLatch = new CountDownLatch(1);
                            BluetoothAgent.this.mResolving = (BluetoothDevice) BluetoothAgent.this.mPending.remove(0);
                            if (!BluetoothAgent.this.mKnown.contains(BluetoothAgent.this.mResolving)) {
                                BluetoothAgent.this.mResolving.fetchUuidsWithSdp();
                                try {
                                    BluetoothAgent.this.mLatch.await();
                                    if (BluetoothAgent.this.mDiscovering) {
                                        if (!BluetoothAgent.this.mBluetoothAdapter.isDiscovering()) {
                                            BluetoothAgent.this.mBluetoothAdapter.startDiscovery();
                                            BluetoothAgent.this.mHandler.sendEmptyMessageDelayed(1, 5000);
                                            break;
                                        }
                                    } else {
                                        BluetoothAgent.this.mHandler.sendEmptyMessage(2);
                                        break;
                                    }
                                } catch (InterruptedException e) {
                                    Log.w(BluetoothAgent.TAG, "Interrupted while waiting on UUIDs");
                                    return;
                                }
                            } else {
                                final BluetoothDeviceInfo bluetoothDeviceInfo = new BluetoothDeviceInfo(BluetoothAgent.this.mResolving);
                                BluetoothAgent.this.mCallbackHandler.post(new Runnable() {
                                    public void run() {
                                        if (BluetoothAgent.this.mListener != null) {
                                            BluetoothAgent.this.mListener.onDeviceFound(bluetoothDeviceInfo);
                                        }
                                    }
                                });
                                BluetoothAgent.this.mHandler.sendEmptyMessage(2);
                                return;
                            }
                        } else {
                            BluetoothAgent.this.mResolving = null;
                            return;
                        }
                        break;
                }
            }
        };
    }

    public void destroy() {
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandlerThread.quit();
    }

    public void startDiscovery(Listener listener, Handler handler) {
        this.mListener = listener;
        if (this.mBluetoothAdapter == null) {
            this.mCallbackHandler.post(new Runnable() {
                public void run() {
                    if (BluetoothAgent.this.mListener != null) {
                        BluetoothAgent.this.mListener.onStartDiscoveryFailed(-1);
                    }
                }
            });
        } else if (this.mBluetoothAdapter.isEnabled()) {
            if (this.mBluetoothAdapter.isDiscovering()) {
                this.mBluetoothAdapter.cancelDiscovery();
            }
            this.mDiscovering = true;
            this.mHandler.removeMessages(2);
            this.mCached.addAll(this.mKnown);
            this.mKnown.clear();
            this.mPending.clear();
            if (this.mResolving == null) {
                this.mBluetoothAdapter.startDiscovery();
                this.mHandler.sendEmptyMessageDelayed(1, 5000);
            }
        } else {
            this.mCallbackHandler.post(new Runnable() {
                public void run() {
                    if (BluetoothAgent.this.mListener != null) {
                        BluetoothAgent.this.mListener.onStartDiscoveryFailed(-2);
                    }
                }
            });
        }
    }

    public void stopDiscovery() {
        if (this.mBluetoothAdapter != null) {
            if (this.mBluetoothAdapter.isDiscovering()) {
                this.mBluetoothAdapter.cancelDiscovery();
            }
            this.mDiscovering = false;
            final Listener listener = this.mListener;
            this.mListener = null;
            this.mCallbackHandler.post(new Runnable() {
                public void run() {
                    if (listener != null) {
                        listener.onDiscoveryStopped();
                    }
                }
            });
        }
    }
}

package com.vn.vbeeon.utils.discovery;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter.Builder;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

@TargetApi(21)
/* renamed from: com.google.android.tv.support.remote.discovery.BluetoothLeAgent */
/* compiled from: Unknown */
public class BluetoothLeAgent extends DiscoveryAgent {
    private static final boolean DEBUG = false;
    private static final long MAX_RESULTS_PER_SCAN = 10;
    private static final long REPEATED_DISCOVERY_DELAY = 5000;
    private static final String TAG = "AtvRemote.BleDiscoverer";
    private ScanCallback mCallback;
    private Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public int mSeenResults = 0;
    private Runnable mStartDiscovery;

    private BluetoothAdapter getAdapter() {
        return BluetoothAdapter.getDefaultAdapter();
    }

    private BluetoothLeScanner getScanner() {
        if (getAdapter() != null) {
            return getAdapter().getBluetoothLeScanner();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void postDelayedStartDiscovery(final Listener listener, final Handler handler) {
        if (this.mStartDiscovery != null) {
            this.mHandler.removeCallbacks(this.mStartDiscovery);
        }
        this.mStartDiscovery = new Runnable() {
            public void run() {
                BluetoothLeAgent.this.startDiscovery(listener, handler);
            }
        };
        this.mHandler.postDelayed(this.mStartDiscovery, REPEATED_DISCOVERY_DELAY);
    }

    public /* bridge */ /* synthetic */ void destroy() {
        super.destroy();
    }

    public void startDiscovery(final Listener listener, final Handler handler) {
        if (getScanner() == null) {
            Log.w(TAG, "Bluetooth LE scanner unavailable");
        } else if (!getAdapter().isEnabled()) {
            Log.w(TAG, "Bluetooth LE adapter not enabled");
        } else if (this.mCallback == null) {
            this.mSeenResults = 0;
            ArrayList arrayList = new ArrayList();
            arrayList.add(new Builder().setServiceUuid(new ParcelUuid(BluetoothConstants.MY_UUID)).build());
            ScanSettings build = new ScanSettings.Builder().build();
            this.mCallback = new ScanCallback() {
                public void onBatchScanResults(List<ScanResult> list) {
                    super.onBatchScanResults(list);
                }

                public void onScanFailed(int i) {
                    super.onScanFailed(i);
                    Log.e(BluetoothLeAgent.TAG, "LE Scan failed");
                }

                public void onScanResult(int i, final ScanResult scanResult) {
                    super.onScanResult(i, scanResult);
                    handler.post(new Runnable() {
                        public void run() {
                            listener.onDeviceFound(new BluetoothDeviceInfo(scanResult.getDevice()));
                        }
                    });
                    BluetoothLeAgent.this.mSeenResults = BluetoothLeAgent.this.mSeenResults + 1;
                    if (!(((long) BluetoothLeAgent.this.mSeenResults) <= BluetoothLeAgent.MAX_RESULTS_PER_SCAN)) {
                        BluetoothLeAgent.this.stopDiscovery();
                        BluetoothLeAgent.this.postDelayedStartDiscovery(listener, handler);
                    }
                }
            };
            getScanner().startScan(arrayList, build, this.mCallback);
        }
    }

    public void stopDiscovery() {
        if (getScanner() == null) {
            Log.w(TAG, "Bluetooth LE scanner unavailable");
        } else if (getAdapter().isEnabled()) {
            if (this.mCallback != null) {
                getScanner().stopScan(this.mCallback);
                this.mCallback = null;
            }
            if (this.mStartDiscovery != null) {
                this.mHandler.removeCallbacks(this.mStartDiscovery);
                this.mStartDiscovery = null;
            }
        } else {
            Log.w(TAG, "Bluetooth LE adapter not enabled");
        }
    }
}

package com.vn.vbeeon.utils.discovery;

import android.bluetooth.BluetoothDevice;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;

/* renamed from: com.google.android.tv.support.remote.discovery.BluetoothDeviceInfo */
/* compiled from: Unknown */
public class BluetoothDeviceInfo extends DeviceInfo {
    final BluetoothDevice mDevice;
    final String mName;

    BluetoothDeviceInfo(BluetoothDevice bluetoothDevice) {
        this(bluetoothDevice, bluetoothDevice.getName());
    }

    BluetoothDeviceInfo(BluetoothDevice bluetoothDevice, String str) {
        this.mDevice = bluetoothDevice;
        this.mName = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        return this.mDevice.equals(((BluetoothDeviceInfo) obj).mDevice);
    }

    /* access modifiers changed from: 0000 */
    public String getAddress() {
        return this.mDevice.getAddress();
    }

    public CharSequence getName() {
        String name = this.mDevice.getName();
        return this.mOtherInfo == null ? (TextUtils.isEmpty(name) && TextUtils.isEmpty(this.mName)) ? this.mDevice.getAddress() : TextUtils.isEmpty(name) ? this.mName : name : this.mOtherInfo.getName();
    }

    public Uri getUri() {
        return new Builder().scheme("bt").encodedAuthority(this.mDevice.getAddress()).fragment(getName().toString()).build();
    }

    public int hashCode() {
        return (super.hashCode() * 31) + this.mDevice.hashCode();
    }
}

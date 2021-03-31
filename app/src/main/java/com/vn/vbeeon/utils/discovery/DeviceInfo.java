package com.vn.vbeeon.utils.discovery;

import android.bluetooth.BluetoothAdapter;
import android.net.Uri;

import java.net.InetAddress;
import java.net.UnknownHostException;

/* renamed from: com.google.android.tv.support.remote.discovery.DeviceInfo */
/* compiled from: Unknown */
public abstract class DeviceInfo implements Comparable<DeviceInfo> {
    DeviceInfo mOtherInfo;

    public static DeviceInfo fromUri(Uri uri) {
        if ("tcp".equals(uri.getScheme())) {
            try {
                byte[] bArr = new byte[4];
                String[] split = uri.getHost().split("\\.");
                for (int i = 0; i < 4; i++) {
                    bArr[i] = (byte) ((byte) Integer.parseInt(split[i]));
                }
                InetAddress byAddress = InetAddress.getByAddress(bArr);
                int port = uri.getPort();
                if (6465 == port) {
                    port = 6466;
                }
                return new WifiDeviceInfo(byAddress, port, uri.getPath(), uri.getFragment());
            } catch (UnknownHostException e) {
                throw new IllegalArgumentException("Unsupported URI");
            }
        } else if (!"bt".equals(uri.getScheme())) {
            throw new IllegalArgumentException("Unsupported URI");
        } else {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                return new BluetoothDeviceInfo(defaultAdapter.getRemoteDevice(uri.getAuthority()), uri.getFragment());
            }
            throw new IllegalStateException("No bluetooth");
        }
    }

    public int compareTo(DeviceInfo deviceInfo) {
        return getUri().compareTo(deviceInfo.getUri());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceInfo)) {
            return false;
        }
        return getUri().equals(((DeviceInfo) obj).getUri());
    }

    public abstract CharSequence getName();

    public abstract Uri getUri();

    public int hashCode() {
        return getUri().hashCode();
    }

    /* access modifiers changed from: 0000 */
    public void setOtherDeviceInfo(DeviceInfo deviceInfo) {
        this.mOtherInfo = deviceInfo;
    }

    public String toString() {
        return String.format("%s (%s)", new Object[]{getName().toString(), getUri()});
    }
}

package com.vn.vbeeon.utils.discovery;

import android.net.Uri;
import android.net.Uri.Builder;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.android.tv.support.remote.discovery.WifiDeviceInfo */
/* compiled from: Unknown */
final class WifiDeviceInfo extends DeviceInfo {
    private final InetAddress mHost;
    private final int mPort;
    private final String mServiceName;
    private final String mServiceType;
    private final Map<String, String> mTxtEntries;

    WifiDeviceInfo(InetAddress inetAddress, int i, String str, String str2) {
        this(inetAddress, i, str, str2, null);
    }

    WifiDeviceInfo(InetAddress inetAddress, int i, String str, String str2, List<String> list) {
        this.mHost = inetAddress;
        if (6465 != i) {
            this.mPort = i;
        } else {
            this.mPort = 6466;
        }
        this.mServiceType = str;
        this.mServiceName = str2;
        this.mTxtEntries = new HashMap();
        if (list != null) {
            for (String str3 : list) {
                int indexOf = str3.indexOf(61);
                if (indexOf >= 0) {
                    this.mTxtEntries.put(str3.substring(0, indexOf), str3.substring(indexOf + 1));
                }
            }
        }
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WifiDeviceInfo)) {
            return false;
        }
        WifiDeviceInfo wifiDeviceInfo = (WifiDeviceInfo) obj;
        if (this.mHost != null && wifiDeviceInfo.mHost != null && !this.mHost.equals(wifiDeviceInfo.mHost)) {
            return false;
        }
        if (this.mServiceType != null && wifiDeviceInfo.mServiceType != null && !this.mServiceType.equals(wifiDeviceInfo.mServiceType)) {
            return false;
        }
        if (this.mServiceName != null && wifiDeviceInfo.mServiceName != null && !this.mServiceName.equals(wifiDeviceInfo.mServiceName)) {
            return false;
        }
        if (this.mPort == wifiDeviceInfo.mPort) {
            z = true;
        }
        return z;
    }

    public CharSequence getName() {
        return this.mServiceName;
    }

    /* access modifiers changed from: 0000 */
    public String getTxtEntry(String str) {
        return (String) this.mTxtEntries.get(str);
    }

    public Uri getUri() {
        return new Builder().scheme("tcp").encodedAuthority(this.mHost.getHostAddress() + ":" + this.mPort).encodedPath(this.mServiceType).fragment(this.mServiceName).build();
    }

    public int hashCode() {
        int i = 0;
        if (this.mHost != null) {
            i = this.mHost.hashCode();
        }
        return i ^ this.mPort;
    }
}

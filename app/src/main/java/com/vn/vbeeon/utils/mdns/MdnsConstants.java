package com.vn.vbeeon.utils.mdns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

/* renamed from: com.google.android.tv.support.remote.mdns.MdnsConstants */
/* compiled from: Unknown */
final class MdnsConstants {
    public static InetAddress MDNS_ADDRESS;

    static {
        try {
            MDNS_ADDRESS = InetAddress.getByName("224.0.0.251");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static final int MDNS_PORT = 5353;
    public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
}

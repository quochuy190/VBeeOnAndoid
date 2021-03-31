package com.vn.vbeeon.utils.mdns;

import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.android.tv.support.remote.mdns.MdnsPacketReader */
/* compiled from: Unknown */
class MdnsPacketReader {
    private final byte[] mBuf;
    private final int mCount;
    private final Map<Integer, List<String>> mLabelDictionary;
    private int mLimit;
    private int mPos;

    public MdnsPacketReader(MdnsPacketReader mdnsPacketReader) {
        this.mBuf = mdnsPacketReader.mBuf;
        this.mCount = mdnsPacketReader.mCount;
        this.mPos = mdnsPacketReader.mPos;
        this.mLimit = mdnsPacketReader.mLimit;
        this.mLabelDictionary = new HashMap(mdnsPacketReader.mLabelDictionary);
    }

    public MdnsPacketReader(DatagramPacket datagramPacket) {
        this.mBuf = datagramPacket.getData();
        this.mCount = datagramPacket.getLength();
        this.mPos = 0;
        this.mLimit = -1;
        this.mLabelDictionary = new HashMap();
    }

    private void checkRemaining(int i) throws EOFException {
        if (getRemaining() < i) {
            throw new EOFException();
        }
    }

    public void clearLimit() {
        this.mLimit = -1;
    }

    public int getPosition() {
        return this.mPos;
    }

    public int getRemaining() {
        return (this.mLimit < 0 ? this.mCount : this.mLimit) - this.mPos;
    }

    public byte peekByte() throws EOFException {
        checkRemaining(1);
        return this.mBuf[this.mPos];
    }

    public void readBytes(byte[] bArr) throws EOFException {
        checkRemaining(bArr.length);
        System.arraycopy(this.mBuf, this.mPos, bArr, 0, bArr.length);
        this.mPos += bArr.length;
    }

    public String[] readLabels() throws IOException {
        List list;
        HashMap<Integer, List<String>> hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        while (true) {
            if (getRemaining() > 0) {
                byte peekByte = peekByte();
                if (peekByte == 0) {
                    this.mPos++;
                    break;
                }
                boolean z = (peekByte & 192) == 192;
                int i = this.mPos;
                if (!z) {
                    String readString = readString();
                    list = new ArrayList();
                    list.add(readString);
                } else {
                    list = (List) this.mLabelDictionary.get(Integer.valueOf(((readUInt8() & 63) << 8) | (readUInt8() & 255)));
                    if (list == null) {
                        throw new IOException("invalid label pointer");
                    }
                }
                List list2 = list;
                arrayList.addAll(list2);
                for (Integer num : hashMap.keySet()) {
                    ((List) hashMap.get(num)).addAll(list2);
                }
                hashMap.put(Integer.valueOf(i), list2);
                if (z) {
                    break;
                }
            } else {
                break;
            }
        }
        this.mLabelDictionary.putAll(hashMap);
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public String readString() throws EOFException {
        int readUInt8 = readUInt8();
        checkRemaining(readUInt8);
        String str = new String(this.mBuf, this.mPos, readUInt8, MdnsConstants.UTF8_CHARSET);
        this.mPos = readUInt8 + this.mPos;
        return str;
    }

    public int readUInt16() throws EOFException {
        checkRemaining(2);
        byte[] bArr = this.mBuf;
        int i = this.mPos;
        this.mPos = i + 1;
        int i2 = (bArr[i] & 255) << 8;
        byte[] bArr2 = this.mBuf;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        return i2 | (bArr2[i3] & 255);
    }

    public long readUInt32() throws EOFException {
        checkRemaining(4);
        byte[] bArr = this.mBuf;
        int i = this.mPos;
        this.mPos = i + 1;
        long j = ((long) (bArr[i] & 255)) << 24;
        byte[] bArr2 = this.mBuf;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        long j2 = j | (((long) (bArr2[i2] & 255)) << 16);
        byte[] bArr3 = this.mBuf;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        long j3 = j2 | (((long) (bArr3[i3] & 255)) << 8);
        byte[] bArr4 = this.mBuf;
        int i4 = this.mPos;
        this.mPos = i4 + 1;
        return j3 | ((long) (bArr4[i4] & 255));
    }

    public int readUInt8() throws EOFException {
        checkRemaining(1);
        byte[] bArr = this.mBuf;
        int i = this.mPos;
        this.mPos = i + 1;
        return bArr[i] & 255;
    }

    public void setLimit(int i) {
        if (i >= 0) {
            this.mLimit = this.mPos + i;
        }
    }

    public void skip(int i) throws EOFException {
        checkRemaining(i);
        this.mPos += i;
    }
}

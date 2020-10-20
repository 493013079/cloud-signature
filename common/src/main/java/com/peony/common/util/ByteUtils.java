package com.peony.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@UtilityClass
public class ByteUtils {

    /**
     * 适用于T-BOX
     * DWORD 无符号四字节整型（双字，32 位）
     * WORD  无符号双字节整型（字，16 位）
     *
     * @param bytes bytes
     * @return int
     */
    public int bytesToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value += (bytes[i] & 0xFF) << 8 * (bytes.length - 1 - i);
        }
        return value;
    }

    public long bytesToLong(byte[] bytes) {
        long value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value += (bytes[i] & 0xFF) << 8 * (bytes.length - 1 - i);
        }
        return value;
    }

    /**
     * 如要获取bit0，则i=0
     *
     * @param b 字节
     * @param i 位数
     * @return 0或者1
     */
    public int getBit(byte b, int i) {
        return ((b >> i) & 0x1);
    }

    /**
     * b为传入的字节，start是起始位，length是长度，如要获取bit0-bit4的值，则start为0，length为5
     *
     * @param b 字节
     */
    public int getBits(byte b, int start, int length) {
        return ((b >> start) & (0xFF >> (8 - length)));
    }

    /**
     * 解码消息
     * eg:       7E 30 7D 02 08 7D 01 55 7E
     * decode:   7E 30  7E   08   7d  55 7E
     *
     * @param bytes 接收的消息
     * @return 解密后的消息
     */
    public List<Byte> decodeMessage(List<Byte> bytes) {
        List<Byte> decodeMessage = new ArrayList<>();
        decodeMessage.add((byte) 126);
        for (int i = 1; i < bytes.size() - 1; i += 2) {
            int first = bytes.get(i);
            int second = bytes.get(i + 1);
            if (first == 125 && second == 1) {
                decodeMessage.add((byte) 125);
            } else if (first == 125 && second == 2) {
                decodeMessage.add((byte) 126);
            } else {
                decodeMessage.add((byte) first);
                decodeMessage.add((byte) second);
            }
        }
        decodeMessage.add((byte) 126);
        return decodeMessage;
    }

    public Long bcdToLong(List<Byte> bytes) {
        StringBuilder temp = new StringBuilder(bytes.size() * 2);
        for (byte aByte : bytes) {
            temp.append((byte) ((aByte & 0xf0) >>> 4));
            temp.append((byte) (aByte & 0x0f));
        }
        return Long.valueOf(temp.toString(), 16);
    }

    /**
     * long转换成字节数组
     *
     * @param value 待转换的long
     * @return 转换之后的字节数组
     */
    public List<Byte> longToBcd(long value) {
        List<Byte> bytes = new ArrayList<>();
        bytes.add((byte) (value >> 40));
        bytes.add((byte) (value >> 32));
        bytes.add((byte) (value >> 24));
        bytes.add((byte) (value >> 16));
        bytes.add((byte) (value >> 8));
        bytes.add((byte) value);
        return bytes;
    }

    /**
     * int转换成word字节数组
     *
     * @param value 待转换的int
     * @return 转换之后的字节数组
     */
    public List<Byte> intToWord(int value) {
        List<Byte> bytes = new ArrayList<>();
        bytes.add((byte) (value >> 8));
        bytes.add((byte) value);
        return bytes;
    }

    /**
     * int转换成word字节数组
     *
     * @param value 待转换的int
     * @return 转换之后的字节数组
     */
    public List<Byte> longToWord(long value) {
        List<Byte> bytes = new ArrayList<>();
        bytes.add((byte) (value >> 8));
        bytes.add((byte) value);
        return bytes;
    }

    /**
     * int转换成Dword字节数组
     *
     * @param value 待转换的int
     * @return 转换之后的字节数组
     */
    public List<Byte> intToDword(int value) {
        List<Byte> bytes = new ArrayList<>();
        bytes.add((byte) (value >> 24));
        bytes.add((byte) (value >> 16));
        bytes.add((byte) (value >> 8));
        bytes.add((byte) value);
        return bytes;
    }

    /**
     * 校验消息
     *
     * @param bytes 消息体+校验码
     * @return 校验结果
     */
    public Boolean verifyMessage(List<Byte> bytes) {
        int xorResult = bytes.get(1);
        for (int i = 2; i < bytes.size() - 2; i++) {
            xorResult ^= (bytes.get(i) & 0xFF);
        }
        return xorResult == bytes.get(bytes.size() - 2);
    }

    /**
     * 计算校验码
     *
     * @param body 消息体
     * @return 校验码
     */
    public int getCheckCode(List<Byte> body) {
        int checkCode = (body.get(0) & 0xFF);
        for (int i = 1; i < body.size(); i++) {
            int bit = body.get(i);
            checkCode ^= (bit & 0xFF);
        }
        return checkCode;
    }

    public String fillZero(String no, Integer len) {
        StringBuilder sb = new StringBuilder();
        if (no.length() < len) {
            for (int i = 0; i < len - no.length(); i++) {
                sb.append("0");
            }
        }
        return sb.append(no).toString();
    }

}

package com.yzl.spring.test;

import org.springframework.util.Base64Utils;

import java.beans.PropertyEditorSupport;

/**
 * @author yinzuolong
 */
public class ByteArrayBase64PropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        setValue(text != null ? Base64Utils.decodeFromString(text) : null);
    }

    @Override
    public String getAsText() {
        byte[] value = (byte[]) getValue();
        return (value != null ? Base64Utils.encodeToString(value) : "");
    }
}

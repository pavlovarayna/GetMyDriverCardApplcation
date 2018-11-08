package com.mystique.rt.getmydrivercardapplcation.parsers.json;

import android.util.Base64;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ByteArrayToBase64TypeAdapter implements JsonDeserializer<byte[]> {
    @Override
    public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Base64.decode(json.getAsString(), Base64.NO_WRAP);
    }

//    @Override
//    public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
//        return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));    }
}
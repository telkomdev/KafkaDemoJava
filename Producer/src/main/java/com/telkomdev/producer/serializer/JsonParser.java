/*
 * Copyright 2019 wuriyanto.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.telkomdev.producer.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParser<T> {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private T t;
    private Class<T> clazz;

    public JsonParser(T t, Class<T> clazz) {
        this.t = t;
        this.clazz = clazz;
    }

    public JsonParser(Class<T> clazz) {
        this.clazz = clazz;
    }

    public byte[] serialize() {
        return gson.toJson(t).getBytes();
    }

    public T deserialize(byte[] in) {
        return gson.fromJson(new String(in), clazz);
    }

    public void setEntity(T t) {
        this.t = t;
    }
}

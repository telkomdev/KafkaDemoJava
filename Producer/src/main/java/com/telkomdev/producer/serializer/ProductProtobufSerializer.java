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

import com.telkomdev.producer.model.Product;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ProductProtobufSerializer implements Serializer<Product> {

    public ProductProtobufSerializer() {}

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, Product product) {
        // serialize to protocol buffer
        byte[] protoByte = product == null ? null:product.toProto().toByteArray();
        return protoByte;
    }

    @Override
    public void close() {

    }
}

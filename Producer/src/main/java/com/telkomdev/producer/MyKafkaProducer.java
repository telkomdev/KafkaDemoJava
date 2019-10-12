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

package com.telkomdev.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MyKafkaProducer<K, V> {

    private Producer<K, V> producer;

    public MyKafkaProducer() {
    }

    public MyKafkaProducer(String clientId, String keySerializerClass, String valueSerializerClass, String brokers) {
        Properties producerConfig = new Properties();

        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        producerConfig.put(ProducerConfig.RETRIES_CONFIG, 0);
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");

        // kafka brokers
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializerClass);

        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerClass);

        this.producer = new KafkaProducer(producerConfig);
    }

    public void send(String topic, V v) {
        ProducerRecord<K, V> record = new ProducerRecord(topic, v);
        this.producer.send(record);
    }

    public Producer<K, V> getProducer() {
        return this.producer;
    }


}

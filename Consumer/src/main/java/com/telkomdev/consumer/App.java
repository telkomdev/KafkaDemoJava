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

package com.telkomdev.consumer;

import com.telkomdev.consumer.deserializer.ProductAvroDeserializer;
import com.telkomdev.consumer.deserializer.ProductJsonDeserializer;
import com.telkomdev.consumer.deserializer.ProductProtobufDeserializer;
import com.telkomdev.consumer.model.Product;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class App {

    public static void main(String[] args) {

        String brokers = System.getenv("BROKERS");
        String topic = System.getenv("TOPIC");


        if (brokers == null) {
            System.out.println("required brokers");
            System.exit(0);
        }

        if (topic == null) {
            System.out.println("required topic name");
            System.exit(0);
        }

        Properties consumerConfig = new Properties();

        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-1");

        // kafka brokers
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        consumerConfig.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // kafka deserializer
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.ByteArrayDeserializer.class.getName());

        // receive String data
        //consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class.getName());

        // receive Protocol Buffer data
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProductProtobufDeserializer.class.getName());

        // receive JSON data
        //consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProductJsonDeserializer.class.getName());

        // receive AVRO data
        //consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProductAvroDeserializer.class.getName());

        Consumer consumer = new KafkaConsumer<String, String>(consumerConfig);


        // subscribe to topic
        consumer.subscribe(Collections.singletonList(topic));

        int noMessageFound = 0;

        try {

            while (true) {

                // wait for 1000 ms if there is no message at broker
                ConsumerRecords<String, Product> records = consumer.poll(Duration.ofMillis(1000));

                records.forEach(record -> {
                    System.out.println("Record Key " + record.key());
                    System.out.println("Record value " + record.value());
                    System.out.println("Record partition " + record.partition());
                    System.out.println("Record offset " + record.offset());
                });

                // commits the offset of record to broker.
                consumer.commitAsync();
            }

        } catch (WakeupException ex) {

        } finally {
            consumer.close();
        }

    }
}

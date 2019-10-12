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

import com.telkomdev.consumer.model.Product;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ProductKafkaConsumer implements Runnable {

    private String topic;
    private Consumer<String, Product> consumer;

    public ProductKafkaConsumer() {

    }

    public ProductKafkaConsumer(String brokers, String topic, String groupId, String keyDeserializer, String valueDeserializer) {
        this.topic = topic;

        Properties consumerConfig = new Properties();

        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        // kafka brokers
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        consumerConfig.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // key deserializer
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);

        // value deserializer
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);

        this.consumer = new KafkaConsumer<>(consumerConfig);
    }

    @Override
    public void run() {

        // subscribe to topic
        consumer.subscribe(Collections.singletonList(topic));

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
            System.out.println("Exception caught " + ex.getMessage());
        } finally {
            consumer.close();
        }
    }

    public Consumer<String, Product> getConsumer() {
        return consumer;
    }
}

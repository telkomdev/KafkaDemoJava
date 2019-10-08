package com.telkomdev.producer;

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

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

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

        Properties producerConfig = new Properties();

        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "consumer-group-1");
        producerConfig.put(ProducerConfig.RETRIES_CONFIG, 0);
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");

        // kafka brokers
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer producer = new KafkaProducer<String, String>(producerConfig);

        // read input
        Scanner in = new Scanner(System.in);

        System.out.println("Type Message (type 'exit' to quit)");
        String input = in.nextLine();

        while (!input.equals("exit")) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, input);

            try {
                System.out.println(input);

                producer.send(record);
                input = in.nextLine();
            } catch (Exception ex) {
                System.out.println("error send data to kafka: "+ex.getMessage());
                break;
            }
        }

        in.close();
        producer.close();
    }
}

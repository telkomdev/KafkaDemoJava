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

import com.telkomdev.producer.model.Product;
import com.telkomdev.producer.serializer.ProductAvroSerializer;
import com.telkomdev.producer.serializer.ProductJsonSerializer;
import com.telkomdev.producer.serializer.ProductProtobufSerializer;

import java.util.ArrayList;
import java.util.List;
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

        // send String data
        //String valueSerializer = org.apache.kafka.common.serialization.StringSerializer.class.getName();

        // send Protocol Buffer data
        String valueSerializer = ProductProtobufSerializer.class.getName();

        // send JSON data
        // String valueSerializer = ProductJsonSerializer.class.getName());

        // send AVRO data
        // String valueSerializer = producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProductAvroSerializer.class.getName();

        final MyKafkaProducer<String, Product> producer = new MyKafkaProducer("client-1",
                org.apache.kafka.common.serialization.StringSerializer.class.getName(),
                valueSerializer, brokers);


        // read input
        Scanner in = new Scanner(System.in);

        System.out.println("Click Enter to send message (type 'exit' to quit)");
        String input = in.nextLine();

        while (!input.equals("exit")) {
            //ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, input);

            Product p = new Product();
            p.setId("001");
            p.setName("Nokia 6");
            p.setQuantity(5);

            List<String> images = new ArrayList<>();
            images.add("wuriyanto.com/img1");
            images.add("wuriyanto.com/img2");
            p.setImages(images);


            try {
                System.out.println(input);

                producer.send(topic, p);
                input = in.nextLine();
            } catch (Exception ex) {
                System.out.println("error send data to kafka: " + ex.getMessage());
                break;
            }
        }

        in.close();
        producer.getProducer().close();
    }
}

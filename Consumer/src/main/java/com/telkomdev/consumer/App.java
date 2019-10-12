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

import java.util.Scanner;

public class App {

    private static Scanner in;

    public static void main(String[] args) throws Exception {

        in = new Scanner(System.in);

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

        ProductKafkaConsumer productKafkaConsumer = new ProductKafkaConsumer(brokers, topic, "consumer-group-1");
        Thread threadConsumer = new Thread(productKafkaConsumer, "productKafkaConsumer");
        threadConsumer.start();

        String line = "";
        while (!line.equals("exit")) {
            line = in.next();
        }

        productKafkaConsumer.getConsumer().wakeup();
        System.out.println("stopping consumer..");

        //
        threadConsumer.join();
    }
}

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

        Scanner in = new Scanner(System.in);

        System.out.println("Type Message (type 'exit' to quit)");
        String input = in.nextLine();

        while(!input.equals("exit")) {
            System.out.println(input);
            input = in.nextLine();
        }

        in.close();
    }
}

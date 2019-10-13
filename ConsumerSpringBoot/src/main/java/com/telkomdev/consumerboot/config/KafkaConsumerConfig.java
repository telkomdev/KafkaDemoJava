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

package com.telkomdev.consumerboot.config;

 import com.telkomdev.consumerboot.deserializer.ProductProtobufDeserializer;
 import com.telkomdev.consumerboot.model.Product;
 import org.apache.kafka.clients.consumer.ConsumerConfig;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.kafka.annotation.EnableKafka;
 import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
 import org.springframework.kafka.core.ConsumerFactory;
 import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

 import java.util.HashMap;
 import java.util.Map;

 @EnableKafka
 @Configuration
public class KafkaConsumerConfig {

     @Bean
     public ConsumerFactory<String, Product> productKafkaConsumerFactory() {
         Map<String, Object> props = new HashMap<>();

         props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
         props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-1");
         props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
         props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
         props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

         // key deserializer
         props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class.getName());

         // value deserializer
         props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProductProtobufDeserializer.class.getName());

         return new DefaultKafkaConsumerFactory<>(props);
     }

     @Bean
     public ConcurrentKafkaListenerContainerFactory<String, Product> productKafkaListenerContainerFactory() {
         ConcurrentKafkaListenerContainerFactory<String, Product> factory = new ConcurrentKafkaListenerContainerFactory<>();
         factory.setConsumerFactory(productKafkaConsumerFactory());
         return factory;
     }
}

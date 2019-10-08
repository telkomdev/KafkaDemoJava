## Kafka Demo With Java

### Run Kafka
```shell
$ docker-compose up
```

Show Kafka `Topic` list
```shell
$ ./opt/kafka/bin/kafka-topics.sh --list --zookeeper zookeeper:2181
```

Create Kafka `Topic` with name `demo`
```shell
$ ./opt/kafka/bin/kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 100 --topic demo
```

Show Kafka `Topic` information from topic `demo`
```shell
$ ./opt/kafka/bin/kafka-topics.sh --describe --topic demo --zookeeper zookeeper:2181
```

### Run Producer
```shell
$ cd Producer
```

Build
```shell
$ mvn clean package
```

Send message to broker
```shell
$ BROKERS=localhost:9092 TOPIC=demo java -jar target/Producer-1.0-SNAPSHOT.jar
$ Type Message (type 'exit' to quit)
$ hello
```
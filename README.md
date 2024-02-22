# Read Me First

The original idea is from  
https://medium.com/@dornala.amar/embedded-kafka-with-spring-boot-5cab7d8cf831

# EmbeddedKafkaBroker within Application

In this project, an **EmbeddedKafkaBroker** is created when the application starts.
The **EmbeddedKafkaBroker** utilizes the **"dev"** profile, enabling communication among its components using Kafka.

We can send test messages to this broker using KafkaProducerTest

# EmbeddedKafkaBroker for Consumer Testing

An **EmbeddedKafkaBroker** is also created within the EmbeddedKafkaIntegrationTest.
A test listener is set up, allowing us to perform unit tests on Kafka records.



package com.stavros.rabbitmq;


import java.util.concurrent.TimeUnit;

import com.stavros.rabbitmq.receivers.Receiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner{

	private final  RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	public Runner(RabbitTemplate rabbitTemplate, Receiver receiver) {
		this.rabbitTemplate = rabbitTemplate;
		this.receiver = receiver;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Sending message...");
		rabbitTemplate.convertAndSend(RabbitmqApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!"); //template routes the message to the exchange, with a routing key of foo.bar.baz which matches the binding.
		receiver.getLatch().await(1000, TimeUnit.MICROSECONDS);
	}
}

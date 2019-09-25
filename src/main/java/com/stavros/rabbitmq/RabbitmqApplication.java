package com.stavros.rabbitmq;

import com.stavros.rabbitmq.receivers.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqApplication {

	static final String topicExchangeName = "spring-boot-exchange";

	static final String queueName = "spring-boot";



//	Spring AMQP requires that the Queue, the TopicExchange,
//	and the Binding be declared as top level Spring beans in order to be set up properly.

	@Bean
	Queue queue() {
		return new Queue(queueName, false); //create an AMQP queue
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName); //create a topic exchange
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) { // Bind queue and exchange
		return BindingBuilder
				.bind(queue)
				.to(exchange)
				.with("foo.bar.#"); //Bind with key in Runner class
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
											 MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) { // listen for messages on the "spring-boot" queue.
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}


	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);
	}

}

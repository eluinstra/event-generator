package dev.luin.monitor.log.eventgenerator.lumberjack;

import java.time.Instant;

import org.apache.camel.Message;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class EventHandler
{
	@Bean
	public RoutesBuilder eventHandlerRouter() throws Exception
	{
		return new RouteBuilder()
		{
			public void configure()
			{
				from("lumberjack:0.0.0.0:5044")
						.setBody(simple("${body[message]}"))
						.transform().message(m -> transformMessage(m))
						.filter(m -> m != null)
						//to("stream:out");
						.to("vm:transformMessage");
			}

			private String transformMessage(Message m)
			{
				try
				{
					String logMsg = m.getBody(String.class);
					val event = new Event();
					event.setTimestamp(parseTimestamp(logMsg));
					event.setEventType(parseEventType(logMsg));
					event.setMessageId(parseMessageId(logMsg));
					return new ObjectMapper().writeValueAsString(event);
				}
				catch (JsonProcessingException e)
				{
					log.error("",e);
					return null;
				}
			}

			private Instant parseTimestamp(String body)
			{
				//2020-07-04 16:35:12,532
				return null;
			}

			private EventType parseEventType(String logMsg)
			{
				// TODO Auto-generated method stub
				return null;
			}

			private String parseMessageId(String logMsg)
			{
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}

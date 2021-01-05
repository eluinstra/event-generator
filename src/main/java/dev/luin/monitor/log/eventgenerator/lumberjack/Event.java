package dev.luin.monitor.log.eventgenerator.lumberjack;

import java.time.Instant;

import lombok.Data;

@Data
public class Event
{
	Instant timestamp;
	EventType eventType;
	String messageId;
}

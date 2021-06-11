package com.github.cb0s.gs9dof.messages.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record Header(
		@JsonProperty
		int length,
		@JsonProperty
		int id,
		@JsonProperty
		int msgId) implements JsonMessage {

	private Header() {
		this(0, 0, 0);
	}

	@Override
	public String toString() {
		return (String.valueOf(length) + id + msgId);
	}
}

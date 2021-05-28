package com.github.cb0s.gs9dof.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record UpdateCom(
		@JsonProperty
		String com,
		@JsonProperty
		int baudRate) implements JsonMessage {
}

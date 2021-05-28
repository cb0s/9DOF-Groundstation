package com.github.cb0s.gs9dof.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record AvailableComs(
		@JsonProperty
		String[] coms) implements JsonMessage {
}

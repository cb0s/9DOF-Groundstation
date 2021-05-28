package com.github.cb0s.gs9dof.messages.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record RawPacket(
		@JsonProperty
		byte[] raw) implements JsonMessage {
}

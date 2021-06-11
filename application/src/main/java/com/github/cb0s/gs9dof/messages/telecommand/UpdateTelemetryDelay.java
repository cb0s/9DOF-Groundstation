package com.github.cb0s.gs9dof.messages.telecommand;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cb0s.gs9dof.messages.UartMsgInfo;

@UartMsgInfo(msgId=0x2)
public record UpdateTelemetryDelay(
		@JsonProperty
		long delay
) implements TelecommandMsg {

	private UpdateTelemetryDelay() {
		this(0);
	}

	@Override
	public String toString() {
		return String.valueOf(delay);
	}
}

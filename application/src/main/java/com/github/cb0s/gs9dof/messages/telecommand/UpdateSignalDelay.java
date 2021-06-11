package com.github.cb0s.gs9dof.messages.telecommand;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cb0s.gs9dof.messages.UartMsgInfo;

@UartMsgInfo(msgId=0x3)
public record UpdateSignalDelay(
		@JsonProperty
		long delay
) implements TelecommandMsg {

	private UpdateSignalDelay() {
		this(0);
	}

	@Override
	public String toString() {
		return String.valueOf(delay);
	}
}

package com.github.cb0s.gs9dof.messages.telecommand;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cb0s.gs9dof.messages.UartMsgInfo;

@UartMsgInfo(msgId=0x1)
public record ChangeState(
		@JsonProperty
		int state
) implements TelecommandMsg {
	private ChangeState() {
		this(0);
	}

	@Override
	public String toString() {
		return String.valueOf(state);
	}
}

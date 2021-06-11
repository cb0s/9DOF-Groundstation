package com.github.cb0s.gs9dof.messages.telemetry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cb0s.gs9dof.messages.UartMsgInfo;

@UartMsgInfo(msgId=0x12)
public record ReadingError(
		@JsonProperty
		long internalTime,
		@JsonProperty
		int state
) implements TelemetryMsg {

	private ReadingError() {
		this(0, 0);
	}
}

package com.github.cb0s.gs9dof.messages.telemetry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cb0s.gs9dof.messages.UartMsgInfo;

@UartMsgInfo(msgId=0x13)
public record AliveSignal(
		@JsonProperty
		long internalTime,
		@JsonProperty
		int state
) implements TelemetryMsg {
}

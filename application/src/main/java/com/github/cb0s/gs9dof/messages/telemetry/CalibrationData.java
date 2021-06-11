package com.github.cb0s.gs9dof.messages.telemetry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cb0s.gs9dof.messages.UartMsgInfo;

@UartMsgInfo(msgId=0x11)
public record CalibrationData(
		@JsonProperty
		float accelOffX,
		@JsonProperty
		float accelOffY,
		@JsonProperty
		float accelOffZ,

		@JsonProperty
		float gyroOffX,
		@JsonProperty
		float gyroOffY,
		@JsonProperty
		float gyroOffZ,

		@JsonProperty
		float magnetOffMinX,
		@JsonProperty
		float magnetOffMinY,
		@JsonProperty
		float magnetOffMinZ,

		@JsonProperty
		float magnetOffMaxX,
		@JsonProperty
		float magnetOffMaxY,
		@JsonProperty
		float magnetOffMaxZ,

		@JsonProperty
		long internalTime,
		@JsonProperty
		int state
) implements TelemetryMsg {

	private CalibrationData() {
		this(0, 0, 0,
				0, 0, 0,
				0, 0, 0,
				0, 0, 0,
				0, 0);
	}
}

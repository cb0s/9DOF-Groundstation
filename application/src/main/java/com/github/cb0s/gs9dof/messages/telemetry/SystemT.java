package com.github.cb0s.gs9dof.messages.telemetry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cb0s.gs9dof.messages.UartMsgInfo;

@UartMsgInfo(msgId=0x10)
public record SystemT(
		@JsonProperty
		float accelX,
		@JsonProperty
		float accelY,
		@JsonProperty
		float accelZ,

		@JsonProperty
		float gyroX,
		@JsonProperty
		float gyroY,
		@JsonProperty
		float gyroZ,

		@JsonProperty
		float gyroSpeedX,
		@JsonProperty
		float gyroSpeedY,
		@JsonProperty
		float gyroSpeedZ,

		@JsonProperty
		float gyroGaussX,
		@JsonProperty
		float gyroGaussY,
		@JsonProperty
		float gyroGaussZ,

		@JsonProperty
		float magnetX,
		@JsonProperty
		float magnetY,
		@JsonProperty
		float magnetZ,

		@JsonProperty
		float rotMat11,
		@JsonProperty
		float rotMat12,
		@JsonProperty
		float rotMat13,
		@JsonProperty
		float rotMat21,
		@JsonProperty
		float rotMat22,
		@JsonProperty
		float rotMat23,
		@JsonProperty
		float rotMat31,
		@JsonProperty
		float rotMat32,
		@JsonProperty
		float rotMat33,

		@JsonProperty
		float temp,

		@JsonProperty
		long internalTime,
		@JsonProperty
		int systemState
) implements TelemetryMsg {

	private SystemT() {
		this(0, 0, 0,
				0, 0, 0,
				0, 0, 0,
				0, 0, 0,
				0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0,
				0,
				0, 0);
	}
}

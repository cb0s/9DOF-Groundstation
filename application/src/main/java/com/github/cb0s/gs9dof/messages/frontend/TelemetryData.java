package com.github.cb0s.gs9dof.messages.frontend;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record TelemetryData(
		@JsonProperty
		double[] gyroscope,
		@JsonProperty
		double[] accelerometer,
		@JsonProperty
		double[] magnetometer) implements JsonMessage {
}

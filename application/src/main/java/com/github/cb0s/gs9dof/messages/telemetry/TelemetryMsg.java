package com.github.cb0s.gs9dof.messages.telemetry;

import com.github.cb0s.gs9dof.messages.UartMsg;

import java.util.Arrays;

public interface TelemetryMsg extends UartMsg {
	Class[] TELEMETRY_INDEX = new Class[] {
			AliveSignal.class,
			CalibrationData.class,
			ReadingError.class,
			SystemT.class
	};
}

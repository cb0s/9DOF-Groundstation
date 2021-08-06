package com.github.cb0s.gs9dof;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cb0s.gs9dof.messages.UartMsgInfo;
import com.github.cb0s.gs9dof.messages.internal.ParsingPacket;
import com.github.cb0s.gs9dof.messages.telemetry.*;
import de.wuespace.telestion.api.config.Config;
import de.wuespace.telestion.api.message.JsonMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Parser extends AbstractVerticle {
	@Override
	public void start(Promise<Void> startPromise) {
		this.config = Config.get(this.config, config(), Configuration.class);

		vertx.eventBus().consumer(config.inAddress(), rawMsg -> JsonMessage.on(ParsingPacket.class, rawMsg, msg -> {
			var annoCount = Arrays.stream(TelemetryMsg.TELEMETRY_INDEX)
					.filter(c -> c.isAnnotationPresent(UartMsgInfo.class))
					.count();

			if (TelemetryMsg.TELEMETRY_INDEX.length < annoCount) {
				logger.error("Not all Telemetry-Msgs have the required UartMsgInfo - wrong setup");
				return;
			}

			var fields = (String[]) null;

			try {
				fields = new String(msg.payload(), "ISO-8859-1").replace('/', '0').split(";");
			} catch (UnsupportedEncodingException e) {
				logger.error("Unsupported encoding - wrong setup", e);
				return;
			}

			switch(msg.header().msgId()) {
			case 0x10:
				parseSystemT(fields);
				break;
			case 0x11:
				parseCalibrationData(fields);
				break;
			case 0x12:
				parseReadingError(fields);
				break;
			case 0x13:
				parseAliveSignal(fields);
				break;
			default:
				logger.info("Unsupported message received (id={})", msg.header().msgId());
			}
		}));

		startPromise.complete();
	}

	@Override
	public void stop(Promise<Void> stopPromise) {
		stopPromise.complete();
	}

	public record Configuration(
			@JsonProperty
			String inAddress,
			@JsonProperty
			String outAddress) implements JsonMessage {

		private Configuration() {
			this(null, null);
		}
	}

	public Parser() {
		this(null);
	}

	public Parser(Configuration config) {
		this.config = config;
	}

	private void parseSystemT(String[] fields) {
		if (fields.length != SystemT.class.getRecordComponents().length) {
			logger.info("Broken SystemT msg - invalid size");
		}

		var accelX = Float.parseFloat(fields[0]);
		var accelY = Float.parseFloat(fields[1]);
		var accelZ = Float.parseFloat(fields[2]);

		var gyroX = Float.parseFloat(fields[3]);
		var gyroY = Float.parseFloat(fields[4]);
		var gyroZ = Float.parseFloat(fields[5]);

		var gyroSpeedX = Float.parseFloat(fields[6]);
		var gyroSpeedY = Float.parseFloat(fields[7]);
		var gyroSpeedZ = Float.parseFloat(fields[8]);

		var gyroGaussX = Float.parseFloat(fields[9]);
		var gyroGaussY = Float.parseFloat(fields[10]);
		var gyroGaussZ = Float.parseFloat(fields[11]);

		var magnetX = Float.parseFloat(fields[12]);
		var magnetY = Float.parseFloat(fields[13]);
		var magnetZ = Float.parseFloat(fields[14]);

		var rotMat11 = Float.parseFloat(fields[15]);
		var rotMat12 = Float.parseFloat(fields[16]);
		var rotMat13 = Float.parseFloat(fields[17]);
		var rotMat21 = Float.parseFloat(fields[18]);
		var rotMat22 = Float.parseFloat(fields[19]);
		var rotMat23 = Float.parseFloat(fields[20]);
		var rotMat31 = Float.parseFloat(fields[21]);
		var rotMat32 = Float.parseFloat(fields[22]);
		var rotMat33 = Float.parseFloat(fields[23]);

		var temp = Float.parseFloat(fields[24]);

		var time = Long.parseLong(fields[25]);
		var systemState = Integer.parseInt(fields[27]);

		var sysT = new SystemT(accelX, accelY, accelZ,
				gyroX, gyroY, gyroZ,
				gyroSpeedX, gyroSpeedY, gyroSpeedZ,
				gyroGaussX, gyroGaussY, gyroGaussZ,
				magnetX, magnetY, magnetZ,
				rotMat11, rotMat12, rotMat13, rotMat21, rotMat22, rotMat23, rotMat31, rotMat32, rotMat33,
				temp,
				time, systemState);

		vertx.eventBus().publish(config.outAddress(), sysT.json());
	}

	private void parseCalibrationData(String[] fields) {
		if (fields.length != CalibrationData.class.getRecordComponents().length) {
			logger.info("Broken CalibrationData msg - invalid size");
		}

		var accelOffX = Float.parseFloat(fields[0]);
		var accelOffY = Float.parseFloat(fields[1]);
		var accelOffZ = Float.parseFloat(fields[2]);

		var gyroX = Float.parseFloat(fields[3]);
		var gyroY = Float.parseFloat(fields[4]);
		var gyroZ = Float.parseFloat(fields[5]);

		var magnetOffMinX = Float.parseFloat(fields[6]);
		var magnetOffMinY = Float.parseFloat(fields[7]);
		var magnetOffMinZ = Float.parseFloat(fields[8]);

		var magnetOffMaxX = Float.parseFloat(fields[9]);
		var magnetOffMaxY = Float.parseFloat(fields[10]);
		var magnetOffMaxZ = Float.parseFloat(fields[11]);

		var time = Long.parseLong(fields[12]);
		var state = Integer.parseInt(fields[13]);

		var calData = new CalibrationData(accelOffX, accelOffY, accelOffZ,
				gyroX, gyroY, gyroZ,
				magnetOffMinX, magnetOffMinY, magnetOffMinZ,
				magnetOffMaxX, magnetOffMaxY, magnetOffMaxZ,
				time, state);

		vertx.eventBus().publish(config.outAddress(), calData.json());
	}

	private void parseReadingError(String[] fields) {
		if (fields.length != ReadingError.class.getRecordComponents().length) {
			logger.info("Broken ReadingError msg - invalid size");
		}

		var time = Long.parseLong(fields[0]);
		var state = Integer.parseInt(fields[1]);

		var readErrData = new ReadingError(time, state);

		vertx.eventBus().publish(config.outAddress(), readErrData.json());
	}

	private void parseAliveSignal(String[] fields) {
		if (fields.length != AliveSignal.class.getRecordComponents().length) {
			logger.info("Broken AliveSignal msg - invalid size");
		}

		var time = Long.parseLong(fields[0]);
		var state = Integer.parseInt(fields[1]);

		var aliveData = new AliveSignal(time, state);

		vertx.eventBus().publish(config.outAddress(), aliveData.json());
	}

	private Configuration config;
	private final static Logger logger = LoggerFactory.getLogger(Parser.class);
}

package com.github.cb0s.gs9dof;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cb0s.gs9dof.messages.internal.RawPacket;
import de.wuespace.telestion.api.config.Config;
import de.wuespace.telestion.api.message.JsonMessage;
import de.wuespace.telestion.services.connection.rework.ConnectionData;
import de.wuespace.telestion.services.connection.rework.serial.SerialDetails;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple buffer class which delimits the raw read data from the serial con at the occurrence of '\n'.
 *
 * @author Cedric Boes
 * @version 29-05-2021
 */
public class DataBuffer extends AbstractVerticle {
	@Override
	public void start(Promise<Void> startPromise) {
		config = Config.get(config, config(), Configuration.class);
		var parser = RecordParser.newDelimited("\n", handler -> {
			var msg = handler.getBytes();
			vertx.eventBus().publish(config.outAddress(), new RawPacket(msg).json());
		});

		vertx.eventBus().consumer(config.inAddress(), raw -> JsonMessage.on(ConnectionData.class, raw, conData -> {
			if (!(conData.conDetails() instanceof SerialDetails)) {
				logger.debug("Buffer received data which from a connection other than a serial connection ({})",
						conData.conDetails().json());
				return;
			}

			var data = conData.rawData();
			parser.handle(Buffer.buffer(data));
		}));

		startPromise.complete();
	}

	@Override
	public void stop(Promise<Void> stopPromise) {
		stopPromise.complete();
	}

	public record Configuration(@JsonProperty
								String inAddress,
								@JsonProperty
								String outAddress) implements JsonMessage {

	}

	public DataBuffer(Configuration config) {
		this.config = config;
	}

	private Configuration config;
	private final static Logger logger = LoggerFactory.getLogger(DataBuffer.class);
}

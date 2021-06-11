package com.github.cb0s.gs9dof;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cb0s.gs9dof.messages.internal.Header;
import com.github.cb0s.gs9dof.messages.internal.ParsingPacket;
import com.github.cb0s.gs9dof.messages.internal.RawPacket;
import de.wuespace.telestion.api.config.Config;
import de.wuespace.telestion.api.message.JsonMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * A small data preprocessor separating the header from the payload.
 *
 * @author Cedric Boes
 * @version 11-06-2021
 */
public class DataPreprocessor extends AbstractVerticle {
	@Override
	public void start(Promise<Void> startPromise) {
		this.config = Config.get(this.config, config(), Configuration.class);

		vertx.eventBus().consumer(config.inAddress(), raw -> JsonMessage.on(RawPacket.class, raw, msg -> {
			var rawBytes = msg.raw();

			// Check if length is big enough to be a correct message
			if (rawBytes.length < 5) {
				logger.warn("Too short message received (header)");
				return;
			}

			// Checking for the 2 magic bytes
			if (rawBytes[0] != 0x42 || rawBytes[rawBytes.length - 1] != 0x43) {
				logger.warn("Bad message received");
				return;
			}

			var length = rawBytes[1];		// Length from embedded side is not set correctly
			var id = rawBytes[2];
			var msgId = rawBytes[3];

			var header = new Header(length, id, msgId);
			var payload = Arrays.copyOfRange(rawBytes, 4, rawBytes.length-1);
			var parsingPacket = new ParsingPacket(header, payload);

			vertx.eventBus().publish(config.outAddress(), parsingPacket.json());
			logger.debug("Correct msg received and sent to parser");
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
			String outAddress
	) implements JsonMessage {

		private Configuration() {
			this(null, null);
		}
	}

	public DataPreprocessor() {
		this(null);
	}

	public DataPreprocessor(Configuration config) {
		this.config = config;
	}

	private Configuration config;
	private static final Logger logger = LoggerFactory.getLogger(DataPreprocessor.class);
}

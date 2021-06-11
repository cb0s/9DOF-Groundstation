package com.github.cb0s.gs9dof;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cb0s.gs9dof.messages.UartMsgInfo;
import com.github.cb0s.gs9dof.messages.telecommand.TelecommandMsg;
import de.wuespace.telestion.api.config.Config;
import de.wuespace.telestion.api.message.JsonMessage;
import de.wuespace.telestion.services.connection.rework.ConnectionData;
import de.wuespace.telestion.services.connection.rework.serial.SerialDetails;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class PacketCreator extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) {
		this.config = Config.get(this.config, config(), Configuration.class);

		vertx.eventBus().consumer(config.inAddress(), rawMsg -> JsonMessage.on(TelecommandMsg.class, rawMsg, msg -> {
			// Care for magic bytes
			var count = counter.getAndUpdate(i -> i == 256 ? 0 : i == 0x42 ? 0x44 : i);

			var rawBytes = msg.toString().getBytes(StandardCharsets.ISO_8859_1);

			var header = (String.valueOf(rawBytes.length) + count +
					rawMsg.getClass().getAnnotation(UartMsgInfo.class).msgId()).getBytes(StandardCharsets.ISO_8859_1);

			var raws = new byte[rawBytes.length + header.length + 2];

			int i = 1;
			for (var bs : new byte[][] {header, rawBytes}) {
				for (var b : bs) {
					count++;
					raws[i] = b;
				}
			}

			raws[0] = 0x42;
			raws[raws.length - 1] = 0x43;

			var conData = new ConnectionData(raws, new SerialDetails(config.serialPort()));

			vertx.eventBus().publish(config.outAddress(), conData.json());
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
			String outAddress,
			@JsonProperty
			String serialPort
	) implements JsonMessage {
		private Configuration() {
			this(null, null, null);
		}
	}

	public PacketCreator() {
		this(null);
	}

	public PacketCreator(Configuration config) {
		this.config = config;
		this.counter = new AtomicInteger(0);
	}

	private Configuration config;
	private AtomicInteger counter;
	private static final Logger logger = LoggerFactory.getLogger(PacketCreator.class);
}

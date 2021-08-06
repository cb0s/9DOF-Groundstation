package com.github.cb0s.gs9dof;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cb0s.gs9dof.messages.frontend.UpdateCom;
import com.github.cb0s.gs9dof.messages.frontend.UpdateStateCmd;
import com.github.cb0s.gs9dof.messages.telecommand.ChangeState;
import de.wuespace.telestion.api.config.Config;
import de.wuespace.telestion.api.message.JsonMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelcomHandler extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) {
		this.config = Config.get(this.config, config(), Configuration.class);

		vertx.eventBus().consumer(this.config.inAddress(), raw -> {
			if (!JsonMessage.on(UpdateCom.class,
					raw,
					msg -> logger.info("Changing the interface type is not implemented, yet!"))) {
				JsonMessage.on(UpdateStateCmd.class, raw, msg -> {
					var updateState = new ChangeState(msg.state());
					vertx.eventBus().publish(config.outAddress(), updateState.json());
				});
			}
		});

		startPromise.complete();
	}

	@Override
	public void stop(Promise<Void> stopPromise) {
		stopPromise.complete();
	}

	public record Configuration(@JsonProperty String inAddress,
								@JsonProperty String outAddress) implements JsonMessage {
		public Configuration() {
			this(null, null);
		}
	}

	public TelcomHandler() {
		this(null);
	}

	public TelcomHandler(Configuration config) {
		this.config = config;
	}

	private Configuration config;
	private final static Logger logger = LoggerFactory.getLogger(TelcomHandler.class);
}

package com.github.cb0s.gs9dof;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class Parser extends AbstractVerticle {
	@Override
	public void start(Promise<Void> startPromise) {
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

	}
}

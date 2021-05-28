package com.github.cb0s.gs9dof;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class DataBuffer extends AbstractVerticle {
	@Override
	public void start(Promise<Void> startPromise) {
		startPromise.complete();
	}

	@Override
	public void stop(Promise<Void> stopPromise) {
		stopPromise.complete();
	}
}

package com.inqwise.opinion.catalog.servicepackage;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.inqwise.opinion.common.RestApiServerOptions;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * ServicePackageVerticle.
 */
public class ServicePackageVerticle extends VerticleBase {

	/**
	 * getLogger.
	 */
	private static final Logger logger = LogManager.getLogger(ServicePackageVerticle.class);

	@Inject
	private RestApiServerOptions config;

	private HttpServer server;
	private ConfigRetriever retriever;

	/**
	 * Constructs ServicePackageVerticle.
	 */
	public ServicePackageVerticle() {
	}

	/**
	 * start.
	 */
	@Override
	public Future<?> start() throws Exception {
		logger.info("ServicePackageVerticle - start");

		retriever = ConfigRetriever.create(vertx);

		return retriever.getConfig().compose(configJson -> {
			logger.debug("config retrieved: '{}'", configJson);
			Guice.createInjector(List.of(new Module(vertx, configJson))).injectMembers(this);

			var router = Router.router(vertx);
			router.get("/status").handler(context -> context.json(new JsonObject()));

			server = vertx.createHttpServer(new HttpServerOptions()
					.setPort(Objects.requireNonNullElse(config.getHttpPort(), 8080))
					.setHost(Objects.requireNonNullElse(config.getHttpHost(), "127.0.0.1")));
			return server.requestHandler(router).listen();
		});
	}

	/**
	 * stop.
	 */
	@Override
	public Future<?> stop() throws Exception {
		Future<Void> lastFuture = Future.succeededFuture();

		if (null != retriever) {
			lastFuture = lastFuture.compose(nil -> retriever.close());
		}

		if (null != server) {
			lastFuture = lastFuture.compose(nil -> server.close());
		}

		return lastFuture.recover(ex -> {
			logger.error("Error on stop", ex);
			return Future.succeededFuture();
		});
	}
}

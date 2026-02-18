package com.inqwise.opinion.catalog.product;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.inqwise.opinion.common.RestApiServerOptions;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;

/**
 * ProductVerticle.
 */
public class ProductVerticle extends VerticleBase {
	/**
	 * getLogger.
	 */
	private static final Logger logger = LogManager.getLogger(ProductVerticle.class);

	@Inject
	private RestApiServerOptions config;
	@Inject
	private ProductOpenApiRouterBuilder routerBuilder;

	private HttpServer server;
	private ConfigRetriever retriever;
	private static final String STORE_KEY = "store";

	/**
	 * Constructs ProductVerticle.
	 */
	public ProductVerticle() {
	}

	/**
	 * start.
	 */
	@Override
	public Future<?> start() throws Exception {
		logger.info("ProductVerticle - start");
		logger.debug("config: '{}'", config());

		var storeJson = config().getJsonObject(STORE_KEY);
		var options = new ConfigRetrieverOptions().setIncludeDefaultStores(true);

		if (null == storeJson || storeJson.isEmpty()) {
			options.addStore(new ConfigStoreOptions().setType("json").setConfig(config()));
		} else {
			options.addStore(new ConfigStoreOptions(storeJson));
		}

		retriever = ConfigRetriever.create(vertx, options);

		return retriever.getConfig().compose(configJson -> {
			logger.debug("config retrieved: '{}'", configJson);
			Guice.createInjector(List.of(new Module(vertx, configJson))).injectMembers(this);

			return routerBuilder.createRouter().compose(router -> {
				server = vertx.createHttpServer(new HttpServerOptions()
						.setPort(Objects.requireNonNullElse(config.getHttpPort(), 8080))
						.setHost(Objects.requireNonNullElse(config.getHttpHost(), "0.0.0.0")));
				return server.requestHandler(router).listen();
			});
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

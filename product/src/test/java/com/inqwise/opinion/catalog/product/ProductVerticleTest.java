package com.inqwise.opinion.catalog.product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.inqwise.opinion.common.RestApiServerOptions;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpResponseExpectation;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@ExtendWith(VertxExtension.class)
class ProductVerticleTest {
	private static final Logger logger = LogManager.getLogger(ProductVerticleTest.class);
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 8091;
	private WebClient client;

	@BeforeEach
	@DisplayName("Deploy a verticle")
	void setUp(Vertx vertx, VertxTestContext testContext) throws Exception {
		logger.debug("deploysVerticle");

		var config = RestApiServerOptions.builder().withHttpPort(PORT).withHttpHost(HOST).build().toJson();
		vertx
				.deployVerticle(ProductVerticle.class, new DeploymentOptions().setConfig(config))
				.onComplete(testContext.succeedingThenComplete());
	}

	@Test
	@DisplayName("A http server response test")
	void http_server_response_check(Vertx vertx, VertxTestContext testContext) {
		client = WebClient.create(vertx);
		client.get(PORT, HOST, "/status")
				.send()
				.expecting(HttpResponseExpectation.SC_OK)
				.expecting(HttpResponseExpectation.JSON)
				.onComplete(testContext.succeeding(resp -> testContext.verify(() -> {
					var json = resp.bodyAsJsonObject();
					Assertions.assertTrue(json.isEmpty());
					testContext.completeNow();
				})));
	}
}

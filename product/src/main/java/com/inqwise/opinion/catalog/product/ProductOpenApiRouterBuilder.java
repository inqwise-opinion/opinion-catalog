package com.inqwise.opinion.catalog.product;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.inqwise.opinion.common.ExceptionLoggerHandler;
import com.inqwise.opinion.common.ExceptionNormalizerHandler;
import com.inqwise.opinion.common.HttpErrorResponseHandler;
import com.inqwise.opinion.common.RestApiServerOptions;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.router.RouterBuilder;
import io.vertx.openapi.contract.OpenAPIContract;

/**
 * ProductOpenApiRouterBuilder.
 */
public final class ProductOpenApiRouterBuilder {
	private static final String PRODUCT_CONTRACT_YML = "contracts/product-contract.yml";

	private static final Logger logger = LogManager.getLogger(ProductOpenApiRouterBuilder.class);

	@Inject
	private Vertx vertx;
	@Inject
	private DefaultService service;
	@Inject
	private RestApiServerOptions options;

	/**
	 * Constructs ProductOpenApiRouterBuilder.
	 */
	@Inject
	private ProductOpenApiRouterBuilder() {
	}

	/**
	 * createRouter.
	 */
	public Future<Router> createRouter() {
		return getContract()
				.map(contract -> RouterBuilder.create(vertx, contract))
				.map(this::registerHandlers)
				.map(RouterBuilder::createRouter)
				.map(this::customizeRouter);
	}

	/**
	 * getContract.
	 */
	private Future<OpenAPIContract> getContract() {
		return OpenAPIContract.from(vertx, PRODUCT_CONTRACT_YML);
	}

	/**
	 * registerHandlers.
	 */
	private RouterBuilder registerHandlers(RouterBuilder routerBuilder) {
		routerBuilder.getRoute("status").addHandler(this::status);
		routerBuilder.getRoute("get").addHandler(this::get);
		routerBuilder.getRoute("findAll").addHandler(this::findAll);
		return routerBuilder;
	}

	/**
	 * status.
	 */
	private void status(RoutingContext context) {
		logger.debug("status");
		service.status()
				.compose(context::json)
				.onFailure(context::fail);
	}

	/**
	 * get.
	 */
	private void get(RoutingContext context) {
		logger.debug("get");
		var productId = Integer.parseInt(context.request().getParam("product_id"));
		service.get(productId)
				.map(product -> product.toJson())
				.compose(context::json)
				.onFailure(context::fail);
	}

	/**
	 * findAll.
	 */
	private void findAll(RoutingContext context) {
		logger.debug("findAll");
		service.findAll()
				.compose(products -> context.json(products.stream()
						.map(product -> product.toJson())
						.collect(JsonArray::new, JsonArray::add, JsonArray::addAll)))
				.onFailure(context::fail);
	}

	/**
	 * customizeRouter.
	 */
	private Router customizeRouter(Router router) {
		router.route()
				.failureHandler(new ExceptionNormalizerHandler())
				.failureHandler(new ExceptionLoggerHandler(Objects.requireNonNullElse(options.getLogErrorTickets(), false)))
				.failureHandler(new HttpErrorResponseHandler(Objects.requireNonNullElse(options.getPrintStacktrace(), false),
						Objects.requireNonNullElse(options.getSanitizeUnexpectedErrors(), false)));

		return router;
	}
}

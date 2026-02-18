package com.inqwise.opinion.catalog.servicepackage;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.inqwise.opinion.catalog.common.ServicePackageIdentity;
import com.inqwise.opinion.catalog.common.ServicePackageModifyRequest;
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
import io.vertx.openapi.validation.ValidatedRequest;

/**
 * ServicePackageOpenApiRouterBuilder.
 */
public final class ServicePackageOpenApiRouterBuilder {
	private static final String SERVICE_PACKAGE_CONTRACT_YML = "contracts/service-package-contract.yml";

	private static final Logger logger = LogManager.getLogger(ServicePackageOpenApiRouterBuilder.class);

	@Inject
	private Vertx vertx;
	@Inject
	private DefaultServicePackageService service;
	@Inject
	private RestApiServerOptions options;

	/**
	 * Constructs ServicePackageOpenApiRouterBuilder.
	 */
	@Inject
	private ServicePackageOpenApiRouterBuilder() {
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
		return OpenAPIContract.from(vertx, SERVICE_PACKAGE_CONTRACT_YML);
	}

	/**
	 * registerHandlers.
	 */
	private RouterBuilder registerHandlers(RouterBuilder routerBuilder) {
		routerBuilder.getRoute("status").addHandler(this::status);
		routerBuilder.getRoute("sonySystem").addHandler(this::sonySystem);
		routerBuilder.getRoute("get").addHandler(this::get);
		routerBuilder.getRoute("modify").addHandler(this::modify);
		routerBuilder.getRoute("findByProduct").addHandler(this::findByProduct);
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
	 * sonySystem.
	 */
	private void sonySystem(RoutingContext context) {
		logger.debug("sonySystem");
		service.status()
				.compose(context::json)
				.onFailure(context::fail);
	}

	/**
	 * get.
	 */
	private void get(RoutingContext context) {
		logger.debug("get");
		var identity = ServicePackageIdentity.builder()
				.withId(parseInteger(context.request().getParam("service_package_id")))
				.withProductId(parseInteger(context.request().getParam("product_id")))
				.build();

		service.get(identity)
				.map(servicePackage -> servicePackage.toJson())
				.compose(context::json)
				.onFailure(context::fail);
	}

	/**
	 * findByProduct.
	 */
	private void findByProduct(RoutingContext context) {
		logger.debug("findByProduct");
		var productId = parseInteger(context.request().getParam("product_id"));
		var includeNonActive = parseInteger(context.request().getParam("include_non_active"));

		service.findByProduct(productId, includeNonActive)
				.compose(servicePackages -> context.json(servicePackages.stream().map(servicePackage -> servicePackage.toJson())
						.collect(JsonArray::new, JsonArray::add, JsonArray::addAll)))
				.onFailure(context::fail);
	}

	/**
	 * modify.
	 */
	private void modify(RoutingContext context) {
		logger.debug("modify");
		ValidatedRequest validatedRequest =
				context.get(RouterBuilder.KEY_META_DATA_VALIDATED_REQUEST);
		var body = validatedRequest.getBody();
		var request = new ServicePackageModifyRequest(Objects.requireNonNull(body, "body is mandatory").getJsonObject());

		service.modify(request)
				.compose(nil -> {
					context.response().setStatusCode(204);
					return context.response().end();
				})
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

	private static Integer parseInteger(String value) {
		return null == value ? null : Integer.parseInt(value);
	}
}

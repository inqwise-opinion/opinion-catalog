package com.inqwise.opinion.catalog.servicepackage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inqwise.errors.ErrorCodes;
import com.inqwise.errors.ErrorTicket;
import com.inqwise.errors.NotFoundException;
import com.inqwise.opinion.catalog.common.ServicePackage;
import com.inqwise.opinion.catalog.common.ServicePackageIdentity;
import com.inqwise.opinion.catalog.common.ServicePackageModifyRequest;
import com.inqwise.opinion.catalog.common.ServicePackageService;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.templates.SqlTemplate;


class DefaultServicePackageService implements ServicePackageService {
	private static final Logger logger = LogManager.getLogger(DefaultServicePackageService.class);
	
	@Inject
	private Provider<Pool> pooledClientProvider;
	
	
	@Inject
	DefaultServicePackageService() {
	}
	
	/**
	 * close.
	 */
	public Future<Void> close() {
		return Future.succeededFuture();
	}

	/**
	 * status.
	 */
	public Future<JsonObject> status() {
		return Future.succeededFuture(new JsonObject());
	}

	/**
	 * get.
	 */
	@Override
	public Future<ServicePackage> get(ServicePackageIdentity identity) {
		logger.info("get");
		return getInternal(identity);
	}

	/**
	 * modify.
	 */
	@Override
	public Future<Void> modify(ServicePackageModifyRequest request) {
		logger.info("modify");

		var identity = ServicePackageIdentity.builder()
				.withId(request.getId())
				.withProductId(null == request.getProductId() ? null : request.getProductId().intValue())
				.build();

		return getInternal(identity)
				.compose(item -> {
					var hasAnyChanges = Stream.of(
							request.getName(),
							request.getProductId(),
							request.getAmount(),
							request.getDescription(),
							request.getDefaultUsagePeriod())
							.filter(Objects::nonNull)
							.count() > 0L;

					if (!hasAnyChanges) {
						throw ErrorTicket.builder().withError(ErrorCodes.ArgumentWrong)
								.withDetails("no service-package changes provided")
								.build();
					}

					return SqlTemplate.forQuery(pooledClientProvider.get(), DaoMappers.SET_TEMPLATE)
							.mapFrom(DaoMappers.MODIFY_PARAMS)
							.execute(request)
							.mapEmpty();
				});
	}

	/**
	 * findByProduct.
	 */
	@Override
	public Future<List<ServicePackage>> findByProduct(Integer productId, Integer includeNonActive) {
		logger.info("findByProduct");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("p_product_id", productId);
		parameters.put("p_include_non_active", includeNonActive);

		return SqlTemplate.forQuery(pooledClientProvider.get(), DaoMappers.FIND_BY_PRODUCT_TEMPLATE)
				.mapTo(DaoMappers.SERVICE_PACKAGE_ROW)
				.execute(parameters)
				.map(rs -> rs.stream().toList());
	}

	
	private Future<ServicePackage> getInternal(ServicePackageIdentity identity) {
		
		return
		SqlTemplate.forQuery(pooledClientProvider.get(), DaoMappers.LOOKUP_TEMPLATE)
		.mapFrom(DaoMappers.IDENTITY_PARAMS)
		.mapTo(DaoMappers.SERVICE_PACKAGE_ROW)
		.execute(identity)
		.map(rs -> {
			var result = rs.stream()
					.findAny()
					.orElseThrow(() -> new NotFoundException("service-package"));
			
			return result;
		});
	}
}

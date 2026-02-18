package com.inqwise.opinion.catalog.product;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inqwise.errors.NotFoundException;
import com.inqwise.opinion.catalog.common.Product;
import com.inqwise.opinion.catalog.common.ProductService;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.templates.SqlTemplate;

/**
 * DefaultService.
 */
class DefaultService implements ProductService {
	private static final Logger logger = LogManager.getLogger(DefaultService.class);

	@Inject
	private Provider<Pool> pooledClientProvider;

	@Inject
	DefaultService() {
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
	@Override
	public Future<JsonObject> status() {
		return Future.succeededFuture(new JsonObject());
	}

	/**
	 * get.
	 */
	@Override
	public Future<Product> get(Integer productId) {
		logger.info("get");

		return SqlTemplate.forQuery(pooledClientProvider.get(), DaoMappers.LOOKUP_TEMPLATE)
				.mapFrom(DaoMappers.PRODUCT_ID_PARAMS)
				.mapTo(DaoMappers.PRODUCT_ROW)
				.execute(productId)
				.map(rs -> rs.stream()
						.findAny()
						.orElseThrow(() -> new NotFoundException("product")));
	}

	/**
	 * findAll.
	 */
	@Override
	public Future<List<Product>> findAll() {
		logger.info("findAll");

		return SqlTemplate.forQuery(pooledClientProvider.get(), DaoMappers.FIND_ALL_TEMPLATE)
				.mapTo(DaoMappers.PRODUCT_ROW)
				.execute(Map.of())
				.map(rs -> rs.stream().toList());
	}
}

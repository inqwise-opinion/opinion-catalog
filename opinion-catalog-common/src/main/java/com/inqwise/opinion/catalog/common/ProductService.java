package com.inqwise.opinion.catalog.common;

import java.util.List;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

/**
 * Service interface for retrieving product catalog data.
 */
@ProxyGen
public interface ProductService {
	/**
	 * Event-bus service name.
	 */
	String SERVICE_NAME = "product-eb-service";

	/**
	 * Event-bus service address.
	 */
	String SERVICE_ADDRESS = "service.product";

	/**
	 * Gets a product by id.
	 *
	 * @param productId product id.
	 * @return product.
	 */
	Future<Product> get(Integer productId);

	/**
	 * Finds all products.
	 *
	 * @return product list.
	 */
	Future<List<Product>> findAll();

	/**
	 * status.
	 */
	Future<JsonObject> status();
}

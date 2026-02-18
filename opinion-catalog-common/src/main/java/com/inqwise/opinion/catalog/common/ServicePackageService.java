package com.inqwise.opinion.catalog.common;


import java.util.List;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.Future;

/**
 * Service interface for retrieving shared service package catalog data.
 */
@ProxyGen
public interface ServicePackageService {
	/**
	 * Event-bus service name.
	 */
	String SERVICE_NAME = "servicepackage-eb-service";

	/**
	 * Event-bus service address.
	 */
	String SERVICE_ADDRESS = "service.servicepackage";

	/**
	 * Gets a service package by identity.
	 *
	 * @param identity service package identity.
	 * @return service package.
	 */
	Future<ServicePackage> get(ServicePackageIdentity identity);

	/**
	 * Modifies a service package.
	 *
	 * @param request modify request.
	 * @return completion future.
	 */
	Future<Void> modify(ServicePackageModifyRequest request);

	/**
	 * Finds service packages by product.
	 *
	 * @param productId product id.
	 * @param includeNonActive include non-active rows flag (0/1).
	 * @return matching service packages.
	 */
	Future<List<ServicePackage>> findByProduct(Integer productId, Integer includeNonActive);
	
}

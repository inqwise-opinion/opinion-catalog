package com.inqwise.opinion.catalog.common;

/**
 * Minimal identity contract for service package projections.
 */
public interface ServicePackageIdentifiable {
	/**
	 * Gets the service package identifier.
	 *
	 * @return service package id.
	 */
	Integer getId();

	/**
	 * Gets the product identifier associated with the package.
	 *
	 * @return product id.
	 */
	Integer getProductId();
}

package com.inqwise.opinion.catalog.common;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Minimal identity data for selecting a service package.
 */
@DataObject
public class ServicePackageIdentity implements ServicePackageIdentifiable {

	private Integer id;
	private Integer productId;

	private ServicePackageIdentity(Builder builder) {
		this.id = builder.id;
		this.productId = builder.productId;
	}

	/**
	 * JSON field names.
	 */
	public static final class Keys {
		public static final String ID = "id";
		public static final String PRODUCT_ID = "product_id";

		private Keys() {
		}
	}

	/**
	 * Creates an empty identity.
	 */
	public ServicePackageIdentity() {
	}

	/**
	 * Creates an identity from JSON.
	 *
	 * @param json source JSON object.
	 */
	public ServicePackageIdentity(JsonObject json) {
		id = json.getInteger(Keys.ID);
		productId = json.getInteger(Keys.PRODUCT_ID);
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Integer getProductId() {
		return productId;
	}

	/**
	 * Serializes identity to JSON.
	 *
	 * @return JSON representation.
	 */
	public JsonObject toJson() {
		var json = new JsonObject();
		if (null != id) {
			json.put(Keys.ID, id);
		}
		if (null != productId) {
			json.put(Keys.PRODUCT_ID, productId);
		}
		return json;
	}

	/**
	 * Creates a builder.
	 *
	 * @return builder instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder for {@link ServicePackageIdentity}.
	 */
	public static final class Builder {
		private Integer id;
		private Integer productId;

		private Builder() {
		}

		/**
		 * Sets service package id.
		 *
		 * @param id service package id.
		 * @return current builder.
		 */
		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		/**
		 * Sets product id.
		 *
		 * @param productId product id.
		 * @return current builder.
		 */
		public Builder withProductId(Integer productId) {
			this.productId = productId;
			return this;
		}

		/**
		 * Builds identity instance.
		 *
		 * @return identity.
		 */
		public ServicePackageIdentity build() {
			return new ServicePackageIdentity(this);
		}
	}
}

package com.inqwise.opinion.catalog.common;

import java.math.BigDecimal;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Request payload for creating or updating a service package.
 */
@DataObject
public class ServicePackageModifyRequest {

	private Integer id;
	private String name;
	private Long productId;
	private BigDecimal amount;
	private String description;
	private Integer defaultUsagePeriod;

	private ServicePackageModifyRequest(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.productId = builder.productId;
		this.amount = builder.amount;
		this.description = builder.description;
		this.defaultUsagePeriod = builder.defaultUsagePeriod;
	}

	/**
	 * JSON field names.
	 */
	public static final class Keys {
		public static final String SERVICE_PACKAGE_ID = "service_package_id";
		public static final String NAME = "name";
		public static final String PRODUCT_ID = "product_id";
		public static final String AMOUNT = "amount";
		public static final String DESCRIPTION = "description";
		public static final String DEFAULT_USAGE_PERIOD = "default_usage_period";

		private Keys() {
		}
	}

	/**
	 * Creates an empty request.
	 */
	public ServicePackageModifyRequest() {
	}

	/**
	 * Creates a request from JSON.
	 *
	 * @param json source JSON object.
	 */
	public ServicePackageModifyRequest(JsonObject json) {
		id = json.getInteger(Keys.SERVICE_PACKAGE_ID);
		name = json.getString(Keys.NAME);
		productId = json.getLong(Keys.PRODUCT_ID);
		amount = parseAmount(json.getValue(Keys.AMOUNT));
		description = json.getString(Keys.DESCRIPTION);
		defaultUsagePeriod = json.getInteger(Keys.DEFAULT_USAGE_PERIOD);
	}

	/**
	 * Gets service package id.
	 *
	 * @return service package id.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Gets service package name.
	 *
	 * @return service package name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets product id.
	 *
	 * @return product id.
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * Gets amount.
	 *
	 * @return amount.
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Gets description.
	 *
	 * @return description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets default usage period in days.
	 *
	 * @return default usage period.
	 */
	public Integer getDefaultUsagePeriod() {
		return defaultUsagePeriod;
	}

	/**
	 * Serializes request to JSON.
	 *
	 * @return JSON representation.
	 */
	public JsonObject toJson() {
		var json = new JsonObject();
		if (null != id) {
			json.put(Keys.SERVICE_PACKAGE_ID, id);
		}
		if (null != name) {
			json.put(Keys.NAME, name);
		}
		if (null != productId) {
			json.put(Keys.PRODUCT_ID, productId);
		}
		if (null != amount) {
			json.put(Keys.AMOUNT, amount);
		}
		if (null != description) {
			json.put(Keys.DESCRIPTION, description);
		}
		if (null != defaultUsagePeriod) {
			json.put(Keys.DEFAULT_USAGE_PERIOD, defaultUsagePeriod);
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
	 * Builder for {@link ServicePackageModifyRequest}.
	 */
	public static final class Builder {
		private Integer id;
		private String name;
		private Long productId;
		private BigDecimal amount;
		private String description;
		private Integer defaultUsagePeriod;

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
		 * Sets service package name.
		 *
		 * @param name service package name.
		 * @return current builder.
		 */
		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		/**
		 * Sets product id.
		 *
		 * @param productId product id.
		 * @return current builder.
		 */
		public Builder withProductId(Long productId) {
			this.productId = productId;
			return this;
		}

		/**
		 * Sets amount.
		 *
		 * @param amount amount.
		 * @return current builder.
		 */
		public Builder withAmount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		/**
		 * Sets description.
		 *
		 * @param description description.
		 * @return current builder.
		 */
		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		/**
		 * Sets default usage period in days.
		 *
		 * @param defaultUsagePeriod usage period.
		 * @return current builder.
		 */
		public Builder withDefaultUsagePeriod(Integer defaultUsagePeriod) {
			this.defaultUsagePeriod = defaultUsagePeriod;
			return this;
		}

		/**
		 * Builds request instance.
		 *
		 * @return request.
		 */
		public ServicePackageModifyRequest build() {
			return new ServicePackageModifyRequest(this);
		}
	}

	private static BigDecimal parseAmount(Object value) {
		if (null == value) {
			return null;
		}
		if (value instanceof BigDecimal decimalValue) {
			return decimalValue;
		}
		if (value instanceof Number numberValue) {
			return new BigDecimal(numberValue.toString());
		}
		return new BigDecimal(value.toString());
	}
}

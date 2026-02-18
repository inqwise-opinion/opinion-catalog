package com.inqwise.opinion.catalog.common;

import java.time.LocalDateTime;
import java.util.Optional;

import com.inqwise.opinion.common.Formatters;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import com.google.common.base.MoreObjects;

/**
 * Data object describing a catalog service package.
 */
@DataObject
public class ServicePackage implements ServicePackageIdentifiable {

	private Integer id;
	private String name;
	private Integer productId;
	private LocalDateTime createdAt;
	private String description;
	private Boolean isActive;
	private Boolean isDefault;
	private Integer defaultUsagePeriod;
	private Integer amount;
	private Integer maxAccountUsers;

	private ServicePackage(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.productId = builder.productId;
		this.createdAt = builder.createdAt;
		this.description = builder.description;
		this.isActive = builder.isActive;
		this.isDefault = builder.isDefault;
		this.defaultUsagePeriod = builder.defaultUsagePeriod;
		this.amount = builder.amount;
		this.maxAccountUsers = builder.maxAccountUsers;
	}

	/**
	 * JSON field names used for serialization.
	 */
	public static class Keys {
		/** Identifier field. */
		public static final String ID = "id";
		/** Name field. */
		public static final String NAME = "name";
		/** Product id field. */
		public static final String PRODUCT_ID = "product_id";
		/** Creation timestamp field. */
		public static final String CREATED_AT = "created_at";
		/** Description field. */
		public static final String DESCRIPTION = "description";
		/** Active flag field. */
		public static final String IS_ACTIVE = "is_active";
		/** Default flag field. */
		public static final String IS_DEFAULT = "is_default";
		/** Default usage period field. */
		public static final String DEFAULT_USAGE_PERIOD = "default_usage_period";
		/** Amount field. */
		public static final String AMOUNT = "amount";
		/** Max account users field. */
		public static final String MAX_ACCOUNT_USERS = "max_account_users";

		private Keys() {
		}
	}

	/**
	 * Creates an empty service package instance.
	 */
	public ServicePackage() {
	}

	/**
	 * Creates a service package from its JSON representation.
	 *
	 * @param json source JSON object.
	 */
	public ServicePackage(JsonObject json) {
		id = json.getInteger(Keys.ID);
		name = json.getString(Keys.NAME);
		productId = json.getInteger(Keys.PRODUCT_ID);
		createdAt = Optional.ofNullable(json.getString(Keys.CREATED_AT))
			.map(Formatters::parseDateTime)
			.orElse(null);
		description = json.getString(Keys.DESCRIPTION);
		isActive = json.getBoolean(Keys.IS_ACTIVE);
		isDefault = json.getBoolean(Keys.IS_DEFAULT);
		defaultUsagePeriod = json.getInteger(Keys.DEFAULT_USAGE_PERIOD);
		amount = json.getInteger(Keys.AMOUNT);
		maxAccountUsers = json.getInteger(Keys.MAX_ACCOUNT_USERS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * Gets the package name.
	 *
	 * @return package name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getProductId() {
		return productId;
	}

	/**
	 * Gets the creation timestamp.
	 *
	 * @return creation timestamp.
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * Gets the package description.
	 *
	 * @return description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Indicates whether this package is active.
	 *
	 * @return {@code true} when this package is active.
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * Indicates whether this package is the default selection.
	 *
	 * @return {@code true} when this package is default.
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}

	/**
	 * Gets the default usage period in days.
	 *
	 * @return default usage period.
	 */
	public Integer getDefaultUsagePeriod() {
		return defaultUsagePeriod;
	}

	/**
	 * Gets the package amount.
	 *
	 * @return amount.
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * Gets the maximum number of account users supported by the package.
	 *
	 * @return maximum account users.
	 */
	public Integer getMaxAccountUsers() {
		return maxAccountUsers;
	}

	/**
	 * Serializes the package into JSON.
	 *
	 * @return JSON representation.
	 */
	public JsonObject toJson() {
		var json = new JsonObject();
		if (null != id) {
			json.put(Keys.ID, id);
		}
		if (null != name) {
			json.put(Keys.NAME, name);
		}
		if (null != productId) {
			json.put(Keys.PRODUCT_ID, productId);
		}
		if (null != createdAt) {
			json.put(Keys.CREATED_AT, createdAt);
		}
		if (null != description) {
			json.put(Keys.DESCRIPTION, description);
		}
		if (null != isActive) {
			json.put(Keys.IS_ACTIVE, isActive);
		}
		if (null != isDefault) {
			json.put(Keys.IS_DEFAULT, isDefault);
		}
		if (null != defaultUsagePeriod) {
			json.put(Keys.DEFAULT_USAGE_PERIOD, defaultUsagePeriod);
		}
		if (null != amount) {
			json.put(Keys.AMOUNT, amount);
		}
		if (null != maxAccountUsers) {
			json.put(Keys.MAX_ACCOUNT_USERS, maxAccountUsers);
		}
		return json;
	}

	/**
	 * Creates an empty builder.
	 *
	 * @return builder instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Creates a builder pre-populated from an existing package.
	 *
	 * @param servicePackage source package.
	 * @return builder instance.
	 */
	public static Builder builderFrom(ServicePackage servicePackage) {
		return new Builder(servicePackage);
	}

	/**
	 * Builder for {@link ServicePackage}.
	 */
	public static final class Builder {
		private Integer id;
		private String name;
		private Integer productId;
		private LocalDateTime createdAt;
		private String description;
		private Boolean isActive;
		private Boolean isDefault;
		private Integer defaultUsagePeriod;
		private Integer amount;
		private Integer maxAccountUsers;

		private Builder() {
		}

		private Builder(ServicePackage servicePackage) {
			this.id = servicePackage.id;
			this.name = servicePackage.name;
			this.productId = servicePackage.productId;
			this.createdAt = servicePackage.createdAt;
			this.description = servicePackage.description;
			this.isActive = servicePackage.isActive;
			this.isDefault = servicePackage.isDefault;
			this.defaultUsagePeriod = servicePackage.defaultUsagePeriod;
			this.amount = servicePackage.amount;
			this.maxAccountUsers = servicePackage.maxAccountUsers;
		}

		/**
		 * Sets the package id.
		 *
		 * @param id package id.
		 * @return current builder.
		 */
		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		/**
		 * Sets the package name.
		 *
		 * @param name package name.
		 * @return current builder.
		 */
		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		/**
		 * Sets the product id.
		 *
		 * @param productId product id.
		 * @return current builder.
		 */
		public Builder withProductId(Integer productId) {
			this.productId = productId;
			return this;
		}

		/**
		 * Sets the creation timestamp.
		 *
		 * @param createdAt creation timestamp.
		 * @return current builder.
		 */
		public Builder withCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		/**
		 * Sets the description.
		 *
		 * @param description package description.
		 * @return current builder.
		 */
		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		/**
		 * Sets active package flag.
		 *
		 * @param isActive active flag.
		 * @return current builder.
		 */
		public Builder withIsActive(Boolean isActive) {
			this.isActive = isActive;
			return this;
		}

		/**
		 * Sets default package flag.
		 *
		 * @param isDefault default flag.
		 * @return current builder.
		 */
		public Builder withIsDefault(Boolean isDefault) {
			this.isDefault = isDefault;
			return this;
		}

		/**
		 * Sets default usage period.
		 *
		 * @param defaultUsagePeriod usage period in days.
		 * @return current builder.
		 */
		public Builder withDefaultUsagePeriod(Integer defaultUsagePeriod) {
			this.defaultUsagePeriod = defaultUsagePeriod;
			return this;
		}

		/**
		 * Sets the amount.
		 *
		 * @param amount package amount.
		 * @return current builder.
		 */
		public Builder withAmount(Integer amount) {
			this.amount = amount;
			return this;
		}

		/**
		 * Sets the maximum account users.
		 *
		 * @param maxAccountUsers max account users.
		 * @return current builder.
		 */
		public Builder withMaxAccountUsers(Integer maxAccountUsers) {
			this.maxAccountUsers = maxAccountUsers;
			return this;
		}

		/**
		 * Builds a {@link ServicePackage} instance.
		 *
		 * @return built package.
		 */
		public ServicePackage build() {
			return new ServicePackage(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("name", name).add("productId", productId)
				.add("createdAt", createdAt).add("description", description).add("isActive", isActive)
				.add("isDefault", isDefault)
				.add("defaultUsagePeriod", defaultUsagePeriod).add("amount", amount)
				.add("maxAccountUsers", maxAccountUsers).toString();
	}
	
}

package com.inqwise.opinion.catalog.common;

import java.time.LocalDateTime;
import java.util.Optional;

import com.inqwise.opinion.common.Formatters;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import com.google.common.base.MoreObjects;

@DataObject
public class ServicePackage implements ServicePackageIdentifiable {

	private Integer id;
	private String name;
	private Integer productId;
	private LocalDateTime createdAt;
	private String description;
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
		this.isDefault = builder.isDefault;
		this.defaultUsagePeriod = builder.defaultUsagePeriod;
		this.amount = builder.amount;
		this.maxAccountUsers = builder.maxAccountUsers;
	}

	public static class Keys {
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String PRODUCT_ID = "product_id";
		public static final String CREATED_AT = "created_at";
		public static final String DESCRIPTION = "description";
		public static final String IS_DEFAULT = "is_default";
		public static final String DEFAULT_USAGE_PERIOD = "default_usage_period";
		public static final String AMOUNT = "amount";
		public static final String MAX_ACCOUNT_USERS = "max_account_users";
	}

	public ServicePackage() {
	}

	public ServicePackage(JsonObject json) {
		id = json.getInteger(Keys.ID);
		name = json.getString(Keys.NAME);
		productId = json.getInteger(Keys.PRODUCT_ID);
		createdAt = Optional.ofNullable(json.getString(Keys.CREATED_AT))
			.map(Formatters::parseDateTime)
			.orElse(null);
		description = json.getString(Keys.DESCRIPTION);
		isDefault = json.getBoolean(Keys.IS_DEFAULT);
		defaultUsagePeriod = json.getInteger(Keys.DEFAULT_USAGE_PERIOD);
		amount = json.getInteger(Keys.AMOUNT);
		maxAccountUsers = json.getInteger(Keys.MAX_ACCOUNT_USERS);
	}

	@Override
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public Integer getProductId() {
		return productId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public Integer getDefaultUsagePeriod() {
		return defaultUsagePeriod;
	}

	public Integer getAmount() {
		return amount;
	}

	public Integer getMaxAccountUsers() {
		return maxAccountUsers;
	}

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

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(ServicePackage servicePackage) {
		return new Builder(servicePackage);
	}

	public static final class Builder {
		private Integer id;
		private String name;
		private Integer productId;
		private LocalDateTime createdAt;
		private String description;
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
			this.isDefault = servicePackage.isDefault;
			this.defaultUsagePeriod = servicePackage.defaultUsagePeriod;
			this.amount = servicePackage.amount;
			this.maxAccountUsers = servicePackage.maxAccountUsers;
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withProductId(Integer productId) {
			this.productId = productId;
			return this;
		}

		public Builder withCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder withIsDefault(Boolean isDefault) {
			this.isDefault = isDefault;
			return this;
		}

		public Builder withDefaultUsagePeriod(Integer defaultUsagePeriod) {
			this.defaultUsagePeriod = defaultUsagePeriod;
			return this;
		}

		public Builder withAmount(Integer amount) {
			this.amount = amount;
			return this;
		}

		public Builder withMaxAccountUsers(Integer maxAccountUsers) {
			this.maxAccountUsers = maxAccountUsers;
			return this;
		}

		public ServicePackage build() {
			return new ServicePackage(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("name", name).add("productId", productId)
				.add("createdAt", createdAt).add("description", description).add("isDefault", isDefault)
				.add("defaultUsagePeriod", defaultUsagePeriod).add("amount", amount)
				.add("maxAccountUsers", maxAccountUsers).toString();
	}
	
}

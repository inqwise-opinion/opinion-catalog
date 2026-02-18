package com.inqwise.opinion.catalog.common;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Product catalog model.
 */
@DataObject
public class Product {

	private Integer productId;
	private String productName;
	private String description;
	private String feedbackCaption;
	private String feedbackShortCaption;
	private String supportEmail;
	private String noReplyEmail;
	private String adminEmail;
	private String salesEmail;
	private String contactUsEmail;

	private Product(Builder builder) {
		this.productId = builder.productId;
		this.productName = builder.productName;
		this.description = builder.description;
		this.feedbackCaption = builder.feedbackCaption;
		this.feedbackShortCaption = builder.feedbackShortCaption;
		this.supportEmail = builder.supportEmail;
		this.noReplyEmail = builder.noReplyEmail;
		this.adminEmail = builder.adminEmail;
		this.salesEmail = builder.salesEmail;
		this.contactUsEmail = builder.contactUsEmail;
	}

	/**
	 * JSON field names.
	 */
	public static final class Keys {
		public static final String PRODUCT_ID = "product_id";
		public static final String PRODUCT_NAME = "product_name";
		public static final String DESCRIPTION = "description";
		public static final String FEEDBACK_CAPTION = "feedback_caption";
		public static final String FEEDBACK_SHORT_CAPTION = "feedback_short_caption";
		public static final String SUPPORT_EMAIL = "support_email";
		public static final String NO_REPLY_EMAIL = "no_reply_email";
		public static final String ADMIN_EMAIL = "admin_email";
		public static final String SALES_EMAIL = "sales_email";
		public static final String CONTACT_US_EMAIL = "contact_us_email";

		private Keys() {
		}
	}

	/**
	 * Creates an empty product model.
	 */
	public Product() {
	}

	/**
	 * Creates a product model from JSON.
	 *
	 * @param json source JSON object.
	 */
	public Product(JsonObject json) {
		productId = json.getInteger(Keys.PRODUCT_ID);
		productName = json.getString(Keys.PRODUCT_NAME);
		description = json.getString(Keys.DESCRIPTION);
		feedbackCaption = json.getString(Keys.FEEDBACK_CAPTION);
		feedbackShortCaption = json.getString(Keys.FEEDBACK_SHORT_CAPTION);
		supportEmail = json.getString(Keys.SUPPORT_EMAIL);
		noReplyEmail = json.getString(Keys.NO_REPLY_EMAIL);
		adminEmail = json.getString(Keys.ADMIN_EMAIL);
		salesEmail = json.getString(Keys.SALES_EMAIL);
		contactUsEmail = json.getString(Keys.CONTACT_US_EMAIL);
	}

	public Integer getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public String getDescription() {
		return description;
	}

	public String getFeedbackCaption() {
		return feedbackCaption;
	}

	public String getFeedbackShortCaption() {
		return feedbackShortCaption;
	}

	public String getSupportEmail() {
		return supportEmail;
	}

	public String getNoReplyEmail() {
		return noReplyEmail;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public String getSalesEmail() {
		return salesEmail;
	}

	public String getContactUsEmail() {
		return contactUsEmail;
	}

	/**
	 * Serializes to JSON.
	 *
	 * @return JSON representation.
	 */
	public JsonObject toJson() {
		var json = new JsonObject();
		if (null != productId) {
			json.put(Keys.PRODUCT_ID, productId);
		}
		if (null != productName) {
			json.put(Keys.PRODUCT_NAME, productName);
		}
		if (null != description) {
			json.put(Keys.DESCRIPTION, description);
		}
		if (null != feedbackCaption) {
			json.put(Keys.FEEDBACK_CAPTION, feedbackCaption);
		}
		if (null != feedbackShortCaption) {
			json.put(Keys.FEEDBACK_SHORT_CAPTION, feedbackShortCaption);
		}
		if (null != supportEmail) {
			json.put(Keys.SUPPORT_EMAIL, supportEmail);
		}
		if (null != noReplyEmail) {
			json.put(Keys.NO_REPLY_EMAIL, noReplyEmail);
		}
		if (null != adminEmail) {
			json.put(Keys.ADMIN_EMAIL, adminEmail);
		}
		if (null != salesEmail) {
			json.put(Keys.SALES_EMAIL, salesEmail);
		}
		if (null != contactUsEmail) {
			json.put(Keys.CONTACT_US_EMAIL, contactUsEmail);
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
	 * Builder for {@link Product}.
	 */
	public static final class Builder {
		private Integer productId;
		private String productName;
		private String description;
		private String feedbackCaption;
		private String feedbackShortCaption;
		private String supportEmail;
		private String noReplyEmail;
		private String adminEmail;
		private String salesEmail;
		private String contactUsEmail;

		private Builder() {
		}

		public Builder withProductId(Integer productId) {
			this.productId = productId;
			return this;
		}

		public Builder withProductName(String productName) {
			this.productName = productName;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder withFeedbackCaption(String feedbackCaption) {
			this.feedbackCaption = feedbackCaption;
			return this;
		}

		public Builder withFeedbackShortCaption(String feedbackShortCaption) {
			this.feedbackShortCaption = feedbackShortCaption;
			return this;
		}

		public Builder withSupportEmail(String supportEmail) {
			this.supportEmail = supportEmail;
			return this;
		}

		public Builder withNoReplyEmail(String noReplyEmail) {
			this.noReplyEmail = noReplyEmail;
			return this;
		}

		public Builder withAdminEmail(String adminEmail) {
			this.adminEmail = adminEmail;
			return this;
		}

		public Builder withSalesEmail(String salesEmail) {
			this.salesEmail = salesEmail;
			return this;
		}

		public Builder withContactUsEmail(String contactUsEmail) {
			this.contactUsEmail = contactUsEmail;
			return this;
		}

		public Product build() {
			return new Product(this);
		}
	}
}

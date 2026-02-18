package com.inqwise.opinion.catalog.product;

import java.util.HashMap;
import java.util.Map;

import com.inqwise.opinion.catalog.common.Product;

import io.vertx.sqlclient.templates.RowMapper;
import io.vertx.sqlclient.templates.TupleMapper;

/**
 * DaoMappers.
 */
class DaoMappers {
	public static final TupleMapper<Integer> PRODUCT_ID_PARAMS = TupleMapper.mapper(productId -> {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("p_product_id", productId);
		return parameters;
	});

	public static final RowMapper<Product> PRODUCT_ROW = row -> {
		var builder = Product.builder();

		builder.withProductId(row.getInteger("product_id"));
		builder.withProductName(row.getString("product_name"));
		builder.withDescription(row.getString("description"));
		builder.withFeedbackCaption(row.getString("feedback_caption"));
		builder.withFeedbackShortCaption(row.getString("feedback_short_caption"));
		builder.withSupportEmail(row.getString("support_email"));
		builder.withNoReplyEmail(row.getString("no_reply_email"));
		builder.withAdminEmail(row.getString("admin_email"));
		builder.withSalesEmail(row.getString("sales_email"));
		builder.withContactUsEmail(row.getString("contact_us_email"));

		return builder.build();
	};

	public static final String LOOKUP_TEMPLATE = "CALL getProduct("
			+ "#{p_product_id}"
			+ ");";

	public static final String FIND_ALL_TEMPLATE = "CALL getProducts();";
}

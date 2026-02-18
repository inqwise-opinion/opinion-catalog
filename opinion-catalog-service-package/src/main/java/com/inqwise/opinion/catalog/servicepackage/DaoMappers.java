package com.inqwise.opinion.catalog.servicepackage;

import java.util.HashMap;
import java.util.Map;

import com.inqwise.opinion.catalog.common.ServicePackage;
import com.inqwise.opinion.catalog.common.ServicePackageIdentity;
import com.inqwise.opinion.catalog.common.ServicePackageModifyRequest;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.templates.RowMapper;
import io.vertx.sqlclient.templates.TupleMapper;

/**
 * DaoMappers.
 */
class DaoMappers {
	public static final TupleMapper<ServicePackageIdentity> IDENTITY_PARAMS = TupleMapper.mapper(identity -> {
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.put("p_service_package_id", identity.getId());
		parameters.put("p_product_id", identity.getProductId());
		return parameters;
	});

	public static final TupleMapper<ServicePackageModifyRequest> MODIFY_PARAMS = TupleMapper.mapper(request -> {
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("p_service_package_id", request.getId());
		parameters.put("p_sp_name", request.getName());
		parameters.put("p_product_id", request.getProductId());
		parameters.put("p_amount", request.getAmount());
		parameters.put("p_description", request.getDescription());
		parameters.put("p_default_usage_period", request.getDefaultUsagePeriod());
		return parameters;
	});
			
	public static final RowMapper<ServicePackage> SERVICE_PACKAGE_ROW = row -> {
		var builder = ServicePackage.builder();

		if (hasColumn(row, "sp_id")) {
			builder.withId(row.getInteger("sp_id"));
		}
		if (hasColumn(row, "sp_name")) {
			builder.withName(row.getString("sp_name"));
		}
		if (hasColumn(row, "product_id")) {
			builder.withProductId(row.getInteger("product_id"));
		}
		if (hasColumn(row, "insert_date")) {
			builder.withCreatedAt(row.getLocalDateTime("insert_date"));
		}
		if (hasColumn(row, "description")) {
			builder.withDescription(row.getString("description"));
		}
		if (hasColumn(row, "is_active")) {
			builder.withIsActive(readBoolean(row, "is_active"));
		}
		if (hasColumn(row, "is_default")) {
			builder.withIsDefault(readBoolean(row, "is_default"));
		}
		if (hasColumn(row, "default_usage_period")) {
			builder.withDefaultUsagePeriod(row.getInteger("default_usage_period"));
		}
		if (hasColumn(row, "amount")) {
			builder.withAmount(row.getInteger("amount"));
		}
		if (hasColumn(row, "max_account_users")) {
			builder.withMaxAccountUsers(row.getInteger("max_account_users"));
		}

		return builder.build();
	};
	
	public static final String LOOKUP_TEMPLATE =  "CALL getServicePackage("
			+ "#{p_service_package_id}, "
			+ "#{p_product_id}"
			+ ");";

	public static final String FIND_BY_PRODUCT_TEMPLATE = "CALL getServicePackages("
			+ "#{p_product_id}, "
			+ "#{p_include_non_active}"
			+ ");";

	public static final String SET_TEMPLATE = "CALL setServicePackage("
			+ "#{p_service_package_id}, "
			+ "#{p_sp_name}, "
			+ "#{p_product_id}, "
			+ "#{p_amount}, "
			+ "#{p_description}, "
			+ "#{p_default_usage_period}"
			+ ");";

	private static boolean hasColumn(Row row, String columnName) {
		return row.getColumnIndex(columnName) > -1;
	}

	private static Boolean readBoolean(Row row, String columnName) {
		Object value = row.getValue(columnName);
		if (null == value) {
			return null;
		}
		if (value instanceof Boolean booleanValue) {
			return booleanValue;
		}
		if (value instanceof Number numberValue) {
			return numberValue.intValue() != 0;
		}
		return Boolean.parseBoolean(value.toString());
	}
}

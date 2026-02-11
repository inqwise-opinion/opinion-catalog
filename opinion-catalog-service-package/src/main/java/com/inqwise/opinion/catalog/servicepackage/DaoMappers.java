package com.inqwise.opinion.catalog.servicepackage;

import java.util.HashMap;
import java.util.Map;

import com.inqwise.opinion.catalog.common.ServicePackage;
import com.inqwise.opinion.catalog.common.ServicePackageIdentifiable;
import io.vertx.sqlclient.templates.RowMapper;
import io.vertx.sqlclient.templates.TupleMapper;

/**
 * DaoMappers.
 */
class DaoMappers {
	public static final TupleMapper<ServicePackageIdentifiable> IDENTITY_PARAMS = TupleMapper.mapper(identity -> {
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.put("p_service_package_id", identity.getId());
		parameters.put("p_product_id", identity.getProductId());
		return parameters;
	});
			
	public static final RowMapper<ServicePackage> SERVICE_PACKAGE_ROW = row -> {
		var builder = ServicePackage.builder();
		builder.withId(row.getInteger("sp_id"))
			.withName(row.getString("sp_name"))
			.withProductId(row.getInteger("product_id"))
			.withCreatedAt(row.getLocalDateTime("insert_date"))
			.withDescription(row.getString("description"))
			.withIsDefault(row.getBoolean("is_default"))
			.withDefaultUsagePeriod(row.getInteger("default_usage_period"))
			.withAmount(row.getInteger("amount"))
			.withMaxAccountUsers(row.getInteger("max_account_users"));

		return builder.build();
	};
	
	public static final String LOOKUP_TEMPLATE =  "CALL getServicePackage("
			+ "#{p_service_package_id}, "
			+ "#{p_product_id}"
			+ ");";
}

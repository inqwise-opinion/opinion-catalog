package com.inqwise.opinion.catalog.servicepackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inqwise.errors.NotFoundException;
import com.inqwise.opinion.catalog.common.ServicePackage;
import com.inqwise.opinion.catalog.common.ServicePackageIdentifiable;
import com.inqwise.opinion.catalog.common.ServicePackageService;
import com.inqwise.opinion.common.OpinionEntityStatus;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.templates.SqlTemplate;


class DefaultServicePackageService implements ServicePackageService {
	private static final Logger logger = LogManager.getLogger(DefaultServicePackageService.class);
	
	@Inject
	private Provider<Pool> pooledClientProvider;
	
	
	@Inject
	DefaultServicePackageService() {
	}
	
	/**
	 * close.
	 */
	public Future<Void> close() {
		return Future.succeededFuture();
	}

	/**
	 * status.
	 */
	public Future<JsonObject> status() {
		return Future.succeededFuture(new JsonObject());
	}

	
	private Future<ServicePackage> getInternal(ServicePackageIdentifiable identity) {
		
		return
		SqlTemplate.forQuery(pooledClientProvider.get(), DaoMappers.LOOKUP_TEMPLATE)
		.mapFrom(DaoMappers.IDENTITY_PARAMS)
		.mapTo(DaoMappers.SERVICE_PACKAGE_ROW)
		.execute(identity)
		.map(rs -> {
			var result = rs.stream()
					.findAny()
					.orElseThrow(() -> new NotFoundException("service-package"));
			
			return result;
		});
	}
}

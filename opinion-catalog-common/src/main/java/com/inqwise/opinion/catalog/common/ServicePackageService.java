package com.inqwise.opinion.catalog.common;


import io.vertx.codegen.annotations.ProxyGen;

/**
 * Service interface for retrieving shared service package catalog data.
 */
@ProxyGen
public interface ServicePackageService {
	String SERVICE_NAME = "servicepackage-eb-service";
	String SERVICE_ADDRESS = "service.servicepackage";
	
}

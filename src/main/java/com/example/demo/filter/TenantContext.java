package com.example.demo.filter;

public class TenantContext {

	private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

	private TenantContext() {

	}

	public static String getCurrentTenant() {
		return currentTenant.get();
	}

	public static void setCurrentTenant(String tenant) {
		currentTenant.set(tenant);
	}

	public static void clear() {
		currentTenant.remove();
	}

}
package com.example.demo.domain;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.example.demo.filter.TenantContext;

public class TenantListener {

	@PreUpdate
	@PreRemove
	@PrePersist
	public void setTenant(TenantAware entity) {
		final String tenantId = TenantContext.getCurrentTenant();
		entity.setTenantId(tenantId);
	}

}

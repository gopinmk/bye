package com.nmk.api.auditlog.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.nmk.ibank.service.entity.IbankAuditLog;
public interface AuditlogRepository extends JpaRepository<IbankAuditLog, Integer>{

}

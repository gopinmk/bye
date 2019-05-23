package com.nmk.api.auditlog.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.nmk.api.transactiontype.model.TransactiontypeList;
import com.nmk.api.auditlog.repository.AuditlogRepository;
import com.nmk.ibank.api.exception.HTTP404Exception;
import com.nmk.ibank.api.util.AuditLogServiceUtil;
import com.nmk.ibank.model.AuditLog;
import com.nmk.ibank.model.list.AuditLogList;
import com.nmk.ibank.service.entity.IbankAuditLog;

@RestController
@RequestMapping("/api/v1/auditlog")
public class AuditlogController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AuditlogRepository auditlogRepository;

	@RequestMapping("/")
	String hello() {
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warn message");
		logger.error("Error message");
		return "Done";
	}

	@GetMapping(value = "", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public AuditLogList getAllBrands() {
		List<IbankAuditLog> ibankAuditLogList = auditlogRepository.findAll();
		List<AuditLog> list = AuditLogServiceUtil.convertibankAuditLogListToAuditLog(ibankAuditLogList);
		AuditLogList auditLogList = new AuditLogList();
		auditLogList.setData(list);
		return auditLogList;
	}

	@GetMapping(value = "/{idx}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public AuditLog getAuditLogByIdx(@PathVariable(value = "idx") Integer auditLogIdx) {
		IbankAuditLog ibankAuditLog = auditlogRepository.findById(auditLogIdx)
				.orElseThrow(() -> new HTTP404Exception("idx-" + auditLogIdx + " is not exist"));
		return AuditLogServiceUtil.convertibankAuditLogObjectToAuditLog(ibankAuditLog);
	}

	@PostMapping(value = "", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public AuditLog createAuditLog(@Valid @RequestBody AuditLog auditLog) {
		IbankAuditLog ibankAuditLog = AuditLogServiceUtil.convertAuditLogToibankAuditLog(auditLog);
		IbankAuditLog ibankAuditLog1 = auditlogRepository.save(ibankAuditLog);
		return AuditLogServiceUtil.convertibankAuditLogObjectToAuditLog(ibankAuditLog1);
	}

	@DeleteMapping(value = "/{idx}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> deleteAuditLog(@PathVariable(value = "idx") Integer auditLogIdx) {
		IbankAuditLog ibankAuditLog = auditlogRepository.findById(auditLogIdx)
				.orElseThrow(() -> new HTTP404Exception("idx-" + auditLogIdx + " is not exist"));
		auditlogRepository.delete(ibankAuditLog);
		return ResponseEntity.ok().build();
	}

	@PutMapping(value = "/{idx}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public AuditLog updateBrand(@PathVariable(value = "idx") Integer auditLogIdx,
			@Valid @RequestBody AuditLog auditLog) {
		IbankAuditLog ibankAuditLog = auditlogRepository.findById(auditLogIdx)
				.orElseThrow(() -> new HTTP404Exception("idx-" + auditLogIdx + " is not exist"));
		ibankAuditLog.setXmlMsg(auditLog.getXmlMsg());
		ibankAuditLog.setTransTypeIdx(auditLog.getTransTypeIdx());
		ibankAuditLog.setDescription(auditLog.getDescription());
		ibankAuditLog.setModifiedBy(auditLog.getModifiedBy());
		ibankAuditLog.setModifiedDate(new java.util.Date());
		IbankAuditLog updatedBrand = auditlogRepository.save(ibankAuditLog);
		return AuditLogServiceUtil.convertibankAuditLogObjectToAuditLog(ibankAuditLog);
	}
	
	
	 
}

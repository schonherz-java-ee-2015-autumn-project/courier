package hu.schonherz.java.training.courier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.java.training.courier.dao.SupplierDao;
import hu.schonherz.java.training.courier.service.SupplierService;
import hu.schonherz.java.training.courier.service.converter.SupplierConverter;
import hu.schonherz.java.training.courier.service.vo.SupplierVO;

@Service("supplierService")
@Transactional(propagation = Propagation.REQUIRED)
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	SupplierDao supplierDao;

	@Override
	public List<SupplierVO> findAll() throws Exception {

		return SupplierConverter.toVo(supplierDao.findAll());
	}
}
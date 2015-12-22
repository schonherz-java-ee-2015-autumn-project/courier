package hu.schonherz.java.training.courier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.java.training.courier.dao.AddressDao;
import hu.schonherz.java.training.courier.service.AddressService;
import hu.schonherz.java.training.courier.service.converter.AddressConverter;
import hu.schonherz.java.training.courier.service.vo.AddressVO;

@Service("addressService")
@Transactional(propagation = Propagation.REQUIRED)
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressDao addressDao;

	@Override
	public AddressVO findAddressById(Long addressId) throws Exception {
		return AddressConverter.toVo(addressDao.findAddressById(addressId));
	}

	@Override
	public AddressVO save(AddressVO address) throws Exception {
		return AddressConverter.toVo(addressDao.save(AddressConverter.toEntity(address)));
	}

}
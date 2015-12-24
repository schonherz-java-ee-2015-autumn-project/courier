package hu.schonherz.java.training.courier.service.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.java.training.courier.dao.AddressDao;
import hu.schonherz.java.training.courier.service.AddressServiceLocal;
import hu.schonherz.java.training.courier.service.AddressServiceRemote;
import hu.schonherz.java.training.courier.service.converter.AddressConverter;
import hu.schonherz.java.training.courier.service.vo.AddressVO;

@Stateless(mappedName = "AddressService")
@Local(AddressServiceLocal.class)
@Remote(AddressServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ SpringBeanAutowiringInterceptor.class })
public class AddressServiceImpl implements AddressServiceLocal, AddressServiceRemote {

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
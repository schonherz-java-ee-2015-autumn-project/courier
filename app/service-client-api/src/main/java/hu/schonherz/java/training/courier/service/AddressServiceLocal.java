package hu.schonherz.java.training.courier.service;

import hu.schonherz.java.training.courier.service.vo.AddressVO;

public interface AddressServiceLocal {

	public AddressVO findAddressById(Long addressId) throws Exception;

	public AddressVO save(AddressVO address) throws Exception;

}
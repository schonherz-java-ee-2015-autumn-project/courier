package hu.schonherz.java.training.courier.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.java.training.courier.entities.Address;
import hu.schonherz.java.training.courier.service.vo.AddressVO;

public class AddressConverter {

	static Mapper mapper = new DozerBeanMapper();

	public static AddressVO toVo(Address address) {
		if (address == null) {
			return null;
		}
		return mapper.map(address, AddressVO.class);
	}

	public static Address toEntity(AddressVO addressVO) {
		if (addressVO == null) {
			return null;
		}
		return mapper.map(addressVO, Address.class);
	}

	public static List<AddressVO> toVo(List<Address> address) {
		List<AddressVO> rv = new ArrayList<>();
		for (Address addresss : address) {
			rv.add(toVo(addresss));
		}
		return rv;
	}

	public static List<Address> toEntity(List<AddressVO> address) {
		List<Address> rv = new ArrayList<>();
		for (AddressVO addresses : address) {
			rv.add(toEntity(addresses));
		}
		return rv;
	}
}

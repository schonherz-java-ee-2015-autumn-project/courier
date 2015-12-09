package hu.schonherz.java.training.courier.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.java.training.courier.entities.Supplier;
import hu.schonherz.java.training.courier.service.vo.SupplierVO;

public class SupplierConverter {

	static Mapper mapper = new DozerBeanMapper();

	public static SupplierVO toVo(Supplier supplier) {
		if (supplier == null) {
			return null;
		}
		return mapper.map(supplier, SupplierVO.class);
	}

	public static Supplier toEntity(SupplierVO supplierVO) {
		if (supplierVO == null) {
			return null;
		}
		return mapper.map(supplierVO, Supplier.class);
	}

	public static List<SupplierVO> toVo(List<Supplier> supplier) {
		List<SupplierVO> rv = new ArrayList<>();
		for (Supplier suppliers : supplier) {
			rv.add(toVo(suppliers));
		}
		return rv;
	}

	public static List<Supplier> toEntity(List<SupplierVO> supplier) {
		List<Supplier> rv = new ArrayList<>();
		for (SupplierVO suppliers : supplier) {
			rv.add(toEntity(suppliers));
		}
		return rv;
	}
}

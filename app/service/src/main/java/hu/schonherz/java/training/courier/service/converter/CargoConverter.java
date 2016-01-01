package hu.schonherz.java.training.courier.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.java.training.courier.entities.Cargo;
import hu.schonherz.java.training.courier.entities.CargoStatus;
import hu.schonherz.java.training.courier.service.vo.CargoVO;

public class CargoConverter {

	static Mapper mapper = new DozerBeanMapper();

	public static CargoVO toVo(Cargo cargo) {
		if (cargo == null) {
			return null;
		}
		return mapper.map(cargo, CargoVO.class);
	}

	public static Cargo toEntity(CargoVO cargoVO) {
		if (cargoVO == null) {
			return null;
		}
		return mapper.map(cargoVO, Cargo.class);
	}

	public static List<CargoVO> toVo(List<Cargo> cargo) {
		List<CargoVO> rv = new ArrayList<>();
		for (Cargo cargoes : cargo) {
			rv.add(toVo(cargoes));
		}
		return rv;
	}

	public static List<Cargo> toEntity(List<CargoVO> cargo) {
		List<Cargo> rv = new ArrayList<>();
		for (CargoVO cargoes : cargo) {
			rv.add(toEntity(cargoes));
		}
		return rv;
	}


}

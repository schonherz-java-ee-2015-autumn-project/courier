package hu.schonherz.java.training.courier.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.java.training.courier.entities.Role;
import hu.schonherz.java.training.courier.service.vo.RoleVO;

public class RoleConverter {

	static Mapper mapper = new DozerBeanMapper();

	public static RoleVO toVo(Role Role) {
		if (Role == null) {
			return null;
		}
		return mapper.map(Role, RoleVO.class);
	}

	public static Role toEntity(RoleVO RoleVO) {
		if (RoleVO == null) {
			return null;
		}
		return mapper.map(RoleVO, Role.class);
	}

	public static List<RoleVO> toVo(List<Role> Role) {
		List<RoleVO> rv = new ArrayList<>();
		for (Role Roles : Role) {
			rv.add(toVo(Roles));
		}
		return rv;
	}

	public static List<Role> toEntity(List<RoleVO> Role) {
		List<Role> rv = new ArrayList<>();
		for (RoleVO Roles : Role) {
			rv.add(toEntity(Roles));
		}
		return rv;
	}
	
	public static List<RoleVO> toVofromWS(List<hu.schonherz.java.training.courier.webservice.RoleVO> roles)  {
		List<RoleVO> localRoles = new ArrayList<>();
		for (hu.schonherz.java.training.courier.webservice.RoleVO roleVO : roles) {
			localRoles.add(toVo(roleVO));
		}
		return localRoles;
		
	}
	public static RoleVO toVo(hu.schonherz.java.training.courier.webservice.RoleVO roleVO) {
		RoleVO localRoleVO = new RoleVO();
		localRoleVO.setId(roleVO.getId());
		localRoleVO.setName(roleVO.getName());
		
		return null;
		
	}
}

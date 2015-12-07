package hu.schonherz.java.training.courier.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.java.training.courier.entities.Log;
import hu.schonherz.java.training.courier.service.vo.LogVO;

public class LogConverter {

	static Mapper mapper = new DozerBeanMapper();

	public static LogVO toVo(Log log) {
		if (log == null) {
			return null;
		}
		return mapper.map(log, LogVO.class);
	}

	public static Log toEntity(LogVO logVO) {
		if (logVO == null) {
			return null;
		}
		return mapper.map(logVO, Log.class);
	}

	public static List<LogVO> toVo(List<Log> log) {
		List<LogVO> rv = new ArrayList<>();
		for (Log logs : log) {
			rv.add(toVo(logs));
		}
		return rv;
	}

	public static List<Log> toEntity(List<LogVO> log) {
		List<Log> rv = new ArrayList<>();
		for (LogVO logs : log) {
			rv.add(toEntity(logs));
		}
		return rv;
	}
}

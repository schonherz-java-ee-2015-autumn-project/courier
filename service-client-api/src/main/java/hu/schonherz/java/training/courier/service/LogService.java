package hu.schonherz.java.training.courier.service;

import hu.schonherz.java.training.courier.service.vo.LogVO;

public interface LogService {
	void save(LogVO logVO) throws Exception;

	LogVO findBySessionId(String name) throws Exception;
}
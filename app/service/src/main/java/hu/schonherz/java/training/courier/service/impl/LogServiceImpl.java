package hu.schonherz.java.training.courier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.java.training.courier.dao.LogDao;
import hu.schonherz.java.training.courier.entities.Log;
import hu.schonherz.java.training.courier.service.LogService;
import hu.schonherz.java.training.courier.service.converter.LogConverter;
import hu.schonherz.java.training.courier.service.vo.LogVO;

@Service("logService")
@Transactional(propagation = Propagation.REQUIRED)
public class LogServiceImpl implements LogService {

	@Autowired
	LogDao logDao;

	@Override
	public void save(LogVO logVO) throws Exception {
		logDao.save(LogConverter.toEntity(logVO));
	}

	@Override
	public LogVO findBySessionId(String name) throws Exception {
		Log user = logDao.findBySessionId(name);
		return LogConverter.toVo(user);
	}
}

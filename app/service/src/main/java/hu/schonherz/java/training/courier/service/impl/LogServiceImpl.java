package hu.schonherz.java.training.courier.service.impl;

import java.util.List;

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
	public LogVO save(LogVO logVO) throws Exception {
		Log log = logDao.save(LogConverter.toEntity(logVO));
		return LogConverter.toVo(log);
	}

	@Override
	public List<LogVO> findByUserId(Long id) throws Exception {
		return LogConverter.toVo(logDao.findByUserId(id));
	}
}

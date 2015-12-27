package hu.schonherz.java.training.courier.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.java.training.courier.dao.LogDao;
import hu.schonherz.java.training.courier.entities.Log;
import hu.schonherz.java.training.courier.service.LogServiceLocal;
import hu.schonherz.java.training.courier.service.LogServiceRemote;
import hu.schonherz.java.training.courier.service.converter.LogConverter;
import hu.schonherz.java.training.courier.service.vo.LogVO;

@Stateless
@Local(LogServiceLocal.class)
@Remote(LogServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ SpringBeanAutowiringInterceptor.class })
public class LogServiceImpl implements LogServiceLocal, LogServiceRemote {

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

	@Override
	public List<LogVO> getLogsFrom(Date date) throws Exception {
		return LogConverter.toVo(logDao.getLogsFrom(date));
	}
}

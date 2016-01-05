package hu.schonherz.java.training.courier.service.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.java.training.courier.dao.ItemDao;
import hu.schonherz.java.training.courier.service.ItemServiceLocal;
import hu.schonherz.java.training.courier.service.ItemServiceRemote;
import hu.schonherz.java.training.courier.service.converter.ItemConverter;
import hu.schonherz.java.training.courier.service.vo.ItemVO;

@Stateless(mappedName = "ItemService")
@Local(ItemServiceLocal.class)
@Remote(ItemServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ SpringBeanAutowiringInterceptor.class })
public class ItemServiceImpl implements ItemServiceLocal, ItemServiceRemote {
	
	@Autowired
	ItemDao itemDao;

	@Override
	public ItemVO findItemByGlobalid(Long globalid) throws Exception {
		return ItemConverter.toVo(itemDao.findItemByGlobalid(globalid));
	}

	@Override
	public ItemVO save(ItemVO item) throws Exception {
		return ItemConverter.toVo(itemDao.save(ItemConverter.toEntity(item)));
	}
}
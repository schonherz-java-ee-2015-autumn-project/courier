package hu.schonherz.java.training.courier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.java.training.courier.dao.ItemDao;
import hu.schonherz.java.training.courier.service.ItemService;
import hu.schonherz.java.training.courier.service.converter.ItemConverter;
import hu.schonherz.java.training.courier.service.vo.ItemVO;

@Service("itemService")
@Transactional(propagation = Propagation.REQUIRED)
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemDao itemDao;

	@Override
	public List<ItemVO> findAll() throws Exception {

		return ItemConverter.toVo(itemDao.findAll());
	}
}
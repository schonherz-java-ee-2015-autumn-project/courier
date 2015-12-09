package hu.schonherz.java.training.courier.service;

import java.util.List;

import hu.schonherz.java.training.courier.service.vo.ItemVO;

public interface ItemService {
	public List<ItemVO> findAll() throws Exception;
}
package hu.schonherz.java.training.courier.service;

import hu.schonherz.java.training.courier.service.vo.ItemVO;

public interface ItemServiceLocal {

	public ItemVO findItemByGlobalid(Long globalid) throws Exception;

	public ItemVO save(ItemVO item) throws Exception;

}
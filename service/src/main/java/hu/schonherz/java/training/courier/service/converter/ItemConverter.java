package hu.schonherz.java.training.courier.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.java.training.courier.entities.Item;
import hu.schonherz.java.training.courier.service.vo.ItemVO;

public class ItemConverter {

	static Mapper mapper = new DozerBeanMapper();

	public static ItemVO toVo(Item item) {
		if (item == null) {
			return null;
		}
		return mapper.map(item, ItemVO.class);
	}

	public static Item toEntity(ItemVO itemVO) {
		if (itemVO == null) {
			return null;
		}
		return mapper.map(itemVO, Item.class);
	}

	public static List<ItemVO> toVo(List<Item> item) {
		List<ItemVO> rv = new ArrayList<>();
		for (Item items : item) {
			rv.add(toVo(items));
		}
		return rv;
	}

	public static List<Item> toEntity(List<ItemVO> item) {
		List<Item> rv = new ArrayList<>();
		for (ItemVO items : item) {
			rv.add(toEntity(items));
		}
		return rv;
	}
}

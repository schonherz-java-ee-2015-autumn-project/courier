package hu.schonherz.java.training.courier.webservice.converters;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.administrator.RemoteItemDTO;
import hu.schonherz.java.training.courier.service.vo.ItemVO;

public class RemoteItemConverter {

	public static ItemVO toLocalVo(RemoteItemDTO remoteItemDTO) {
		ItemVO localVo = new ItemVO();
		localVo.setGlobalid(remoteItemDTO.getId());
		localVo.setName(remoteItemDTO.getName());
		localVo.setPrice((double) remoteItemDTO.getPrice());
		return localVo;
	}

	public static List<ItemVO> toLocalVo(List<RemoteItemDTO> remoteItems) {
		List<ItemVO> localItems = new ArrayList<>();
		for (RemoteItemDTO remoteItem : remoteItems) {
			localItems.add(toLocalVo(remoteItem));

		}
		return localItems;
	}
}

package hu.schonherz.java.training.courier.webservice.converters;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.administrator.RemoteItemQuantityDTO;
import hu.schonherz.java.training.courier.service.vo.AddressDetailsVO;

public class RemoteItemQuantityConverter {

	public static AddressDetailsVO toLocalVo(RemoteItemQuantityDTO remoteQuantityItemDTO) {
		AddressDetailsVO localVo = new AddressDetailsVO();
		localVo.setGlobalid(remoteQuantityItemDTO.getId());
		localVo.setQuantity(remoteQuantityItemDTO.getQuantity().longValue());
		localVo.setItem(RemoteItemConverter.toLocalVo(remoteQuantityItemDTO.getItemDTO()));
		return localVo;
	}

	public static List<AddressDetailsVO> toLocalVo(List<RemoteItemQuantityDTO> remoteItemsQuantity) {
		List<AddressDetailsVO> localItemsQuantity = new ArrayList<>();
		for (RemoteItemQuantityDTO remoteItemQuantity : remoteItemsQuantity) {
			localItemsQuantity.add(toLocalVo(remoteItemQuantity));
		}
		return localItemsQuantity;
	}
}

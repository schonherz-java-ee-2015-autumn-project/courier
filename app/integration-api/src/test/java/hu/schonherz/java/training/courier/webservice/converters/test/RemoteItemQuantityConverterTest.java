package hu.schonherz.java.training.courier.webservice.converters.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.schonherz.administrator.RemoteItemDTO;
import hu.schonherz.administrator.RemoteItemQuantityDTO;
import hu.schonherz.java.training.courier.service.vo.AddressDetailsVO;
import hu.schonherz.java.training.courier.webservice.converters.RemoteItemQuantityConverter;

public class RemoteItemQuantityConverterTest {

	RemoteItemQuantityDTO itemQuantityVO;
	RemoteItemDTO itemVO;

	@Before
	public void setUp() throws Exception {
		itemQuantityVO = new RemoteItemQuantityDTO();
		itemQuantityVO.setId((long) 1);
		itemQuantityVO.setQuantity(10);

		itemVO = new RemoteItemDTO();
		itemVO.setName("Csirke");
		itemVO.setId((long) 1);
		itemVO.setPrice(1500);

		itemQuantityVO.setItemDTO(itemVO);

	}

	@Test
	public void testRemoteItemConveterToLocalVo() {
		AddressDetailsVO addressDetails = new AddressDetailsVO();
		addressDetails = RemoteItemQuantityConverter.toLocalVo(itemQuantityVO);

		Assert.assertEquals(itemQuantityVO.getId(), addressDetails.getGlobalid());
		Assert.assertEquals(itemQuantityVO.getItemDTO().getId(), addressDetails.getItem().getGlobalid());
		Assert.assertEquals(itemQuantityVO.getItemDTO().getPrice(), addressDetails.getItem().getPrice(), 0);
		Assert.assertEquals(itemQuantityVO.getItemDTO().getName(), addressDetails.getItem().getName());
		Assert.assertEquals(itemQuantityVO.getQuantity().intValue(), addressDetails.getQuantity().intValue());

	}

}

package hu.schonherz.java.training.courier.webservice.converters.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.schonherz.administrator.RemoteItemDTO;
import hu.schonherz.java.training.courier.service.vo.ItemVO;
import hu.schonherz.java.training.courier.webservice.converters.RemoteItemConverter;

public class RemoteItemConverterTest {

	RemoteItemDTO itemVO;

	@Before
	public void setUp() throws Exception {
		itemVO = new RemoteItemDTO();
		itemVO.setId((long) 1);
		itemVO.setName("Csirke");
		itemVO.setPrice(1500);
	}

	@Test
	public void testRemoteItemConveterToLocalVo() {
		ItemVO localItemVO = new ItemVO();
		localItemVO = RemoteItemConverter.toLocalVo(itemVO);
		
		Assert.assertEquals(itemVO.getId(), localItemVO.getGlobalid());
		Assert.assertEquals(itemVO.getName(), localItemVO.getName());
		Assert.assertEquals(itemVO.getPrice(), itemVO.getPrice());
		
	}

}

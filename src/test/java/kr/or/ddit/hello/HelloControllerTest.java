package kr.or.ddit.hello;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.test.config.WebTestConfig;
import kr.or.ddit.user.model.UserVo;

public class HelloControllerTest extends WebTestConfig {

	//@Resource(name="helloController")

	// ���������߿� ���� ������ Ÿ���� ������ ���� �����Ѵ�
	// ���� ������ Ÿ���� ������ ���� ������ ���� ��� @Qulifier ������̼��� ����
	// Ư�� ������ ���� �̸��� ��Ī�� �� �ִ�
	//  ==> @Resource ������̼� �ϳ��� ��� ���� ���� ���� �ϴ�

	@Test
	public void viewTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/hello/view"))
								.andExpect(status().isOk())
								.andExpect(view().name("hello"))
								.andExpect(model().attributeExists("userVo"))
								.andDo(print())
								.andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		
		assertEquals("hello", mav.getViewName());
		
		UserVo userVo = (UserVo)mav.getModel().get("userVo");
		assertEquals("����", userVo.getUsernm());
	}
	
	@Test
	public void pathVariableTest() throws Exception {
		mockMvc.perform(get("/hello/path/cony").header("User-Agent", "chrome-"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("subpath"))
				.andDo(print())
				.andReturn();
	}
}















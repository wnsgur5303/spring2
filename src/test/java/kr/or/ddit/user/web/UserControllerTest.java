package kr.or.ddit.user.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import kr.or.ddit.test.config.WebTestConfig;
import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.service.UserService;

public class UserControllerTest extends WebTestConfig{

	@Resource(name="userService")
	private UserService userService;
	
	@Before
	public void setup() {
		super.setup();
		// �׽�Ʈ���� ����� �ű� ����� �߰�
		UserVo userVo = new UserVo("testUser", "�׽�Ʈ�����", "testUserPass", new Date(), "���", "���� �߱� �߾ӷ� 76", "4��",
				"34940", "brown.png", "uuid-generated-filename.png");

		userService.registUser(userVo);

		// �ű� �Է� �׽�Ʈ�� ���� �׽�Ʈ �������� �Էµ� �����͸� ����
		userService.deleteUser("ddit_n");
	}

	@After
	public void tearDodwn() {
		userService.deleteUser("testUser");
	}
	
	@Test
	public void allUserTest() throws Exception {
		mockMvc.perform(get("/user/allUser"))
				.andExpect(view().name("user/allUser"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("userList"))
				.andDo(print());
	}
	
	@Test
	public void pagingUserTest() throws Exception {
		mockMvc.perform(get("/user/pagingUser"))
			.andExpect(view().name("user/pagingUser"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("userList"))
			.andExpect(model().attributeExists("pageVo"))
			.andExpect(model().attributeExists("pagination"))
			.andDo(print());
	}
	
	@Test
	public void pagingUser2PageTest() throws Exception {
		mockMvc.perform(get("/user/pagingUser").param("page", "2"))
			.andExpect(view().name("user/pagingUser"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("userList"))
			.andExpect(model().attributeExists("pageVo"))
			.andExpect(model().attributeExists("pagination"))
			.andDo(print());
	}
	
	@Test
	public void userTest() throws Exception {
		mockMvc.perform(get("/user/user").param("userid", "sally"))
			.andExpect(view().name("user/user"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("user"))
			.andDo(print());
	}
	
	@Test
	public void userModifyGetTest() throws Exception {
		mockMvc.perform(get("/user/modify").param("userid", "sally"))
			.andExpect(view().name("user/userModify"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("user"))
			.andDo(print());
	}
	
	@Test
	public void userModifyPostTest() throws Exception {
		
		ClassPathResource resource = new ClassPathResource("kr/or/ddit/upload/sally.png");
		
		MockMultipartFile file = 
				new MockMultipartFile("profile", "sally.png", "image/png",
									   resource.getInputStream());
		
		mockMvc.perform(fileUpload("/user/modify")
						.file(file)
						.param("userid", "ddit")
						.param("usernm", "�������")
						.param("alias", "���κ���")
						.param("pass", "ddit_newpass")
						.param("alais", "���κ���")
						.param("reg_dt", "2021-01-13")
						.param("addr1", "���� �߱� �߾ӷ� 76")
						.param("addr2", "4��")
						.param("zipcode", "34940") )
			.andExpect(view().name("redirect:/user/user"))
			.andExpect(status().is3xxRedirection())
			.andExpect(model().attributeExists("userid"))
			.andDo(print());
	}
	
	@Test
	public void userRegistGetTest() throws Exception {
		mockMvc.perform(get("/user/regist"))
			.andExpect(view().name("user/userRegist"))
			.andExpect(status().isOk())
			.andDo(print());
	}
	
	@Test
	public void userRegistPostTest() throws Exception {
		
		ClassPathResource resource = new ClassPathResource("kr/or/ddit/upload/sally.png");
		
		MockMultipartFile file = 
				new MockMultipartFile("profile", "sally.png", "image/png",
									   resource.getInputStream());
		
		mockMvc.perform(fileUpload("/user/regist")
						.file(file)
						.param("userid", "ddit_n")
						.param("usernm", "�������")
						.param("alias", "���κ���")
						.param("pass", "ddit_newpass")
						.param("alais", "���κ���")
						.param("reg_dt", "2021-01-13")
						.param("addr1", "���� �߱� �߾ӷ� 76")
						.param("addr2", "4��")
						.param("zipcode", "34940") )
			.andExpect(view().name("redirect:/user/pagingUser"))
			.andExpect(status().is3xxRedirection())
			.andDo(print());
	}
	
	@Test
	public void userDeleteTest() throws Exception {
		mockMvc.perform(post("/user/delete")
						.param("userid", "testUser"))
				.andExpect(view().name("redirect:/user/pagingUser"))
				.andExpect(status().is3xxRedirection())
				.andDo(print());
	}
}










package kr.or.ddit.user.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.test.config.ModelTestConfig;
import kr.or.ddit.user.model.UserVo;

public class UserDaoTest extends ModelTestConfig {

	@Resource(name = "userDao")
	private UserDao userDao;

	@Before
	public void setup() {

		UserVo userVo = new UserVo("testUser", "�׽�Ʈ�����", "testUserPass", new Date(), "���", "���� �߱� �߾ӷ� 76", "4��",
				"34940", "brown.png", "uuid-generated-filename.png");

		userDao.registUser(userVo);

		userDao.deleteUser("ddit_n");

	}

	@After
	public void tearDodwn() {
		userDao.deleteUser("testUser");
	}

	// ��ü ����� ��ȸ �׽�Ʈ
	@Test
	public void selectAllUserTest() {
		/*** Given ***/

		/*** When ***/
		List<UserVo> userList = userDao.selectAllUser();

		/*** Then ***/
		assertEquals(17, userList.size());
	}

	// ����� ���̵� �̿��Ͽ� Ư�� ����� ���� ��ȸ
	@Test
	public void selectUserTest() {
		/*** Given ***/
		String userid = "brown";

		/*** When ***/
		UserVo user = userDao.selectUser(userid);

		/*** Then ***/
		assertNotNull(user);
		assertEquals("����", user.getUsernm());
	}

	// ����� ����¡ ��ȸ �׽�Ʈ
	@Test
	public void selectPagingUserTest() {
		/*** Given ***/
		PageVo pageVo = new PageVo(2, 5);

		/*** When ***/
		// List<UserVo> userList = userDao.selectPagingUser(page, pagesize);
		List<UserVo> userList = userDao.selectPagingUser(pageVo);

		/*** Then ***/
		assertEquals(5, userList.size());
	}

	@Test
	public void selectAllUserCntTest() {
		/*** Given ***/

		/*** When ***/
		int userCnt = userDao.selectAllUserCnt();

		/*** Then ***/
		assertEquals(17, userCnt);
	}

	@Test
	public void modifyUserTest() {
		/*** Given ***/

		// userid, usernm, pass, reg_dt, alias, addr1, addr2, zipcode
		UserVo userVo = new UserVo("ddit", "�������", "dditpass", new Date(), "���߿�_m", "������ �߱� �߾ӷ� 76", "4�� ������簳�߿�",
				"34940", "brown.png", "uuid-generated-filename.png");

		/*** When ***/
		int updateCnt = userDao.modifyUser(userVo);

		/*** Then ***/
		assertEquals(1, updateCnt);
	}

	@Test
	public void registUserTest() {
		/*** Given ***/

		// userid, usernm, pass, reg_dt, alias, addr1, addr2, zipcode
		UserVo userVo = new UserVo("ddit_n", "�������", "dditpass", new Date(), "���߿�_m", "������ �߱� �߾ӷ� 76", "4�� ������簳�߿�",
				"34940", "brown.png", "uuid-generated-filename.png");

		/*** When ***/
		int insertCnt = userDao.registUser(userVo);

		/*** Then ***/
		assertEquals(1, insertCnt);
	}

	// ���� �׽�Ʈ
	@Test
	public void deleteUserTest() {
		/*** Given ***/
		// �ش� �׽�Ʈ�� ����� ���� testUser��� ����ڰ� before �޼ҵ忡 ���� ����� �Ȼ���
		String userid = "testUser";

		/*** When ***/
		int deleteCnt = userDao.deleteUser(userid);

		/*** Then ***/
		assertEquals(1, deleteCnt);
	}

}

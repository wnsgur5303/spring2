package kr.or.ddit.user.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.user.model.UserVo;

public interface UserService {

	// ��ü ����� ���� ��ȸ
	List<UserVo> selectAllUser();

	// userid�� �ش��ϴ� ����� �Ѹ��� ���� ��ȸ
	UserVo selectUser(String userid);

	// ����� ����¡ ��ȸ
	Map<String, Object> selectPagingUser(PageVo pageVo);

	// ����� ���� ����
	int modifyUser(UserVo userVo);

	// ����� ���� ���
	int registUser(UserVo userVo);

	// ����� ����
	int deleteUser(String userid);
}

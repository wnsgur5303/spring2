package kr.or.ddit.user.repository;

import java.util.List;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.user.model.UserVo;

public interface UserDao {
	
	//����� ���̵�� ����� ��ȸ
	UserVo selectUser(String userid);
	
	List<UserVo> selectAllUser();

	List<UserVo> selectPagingUser(PageVo pageVo);

	int selectAllUserCnt();

	int modifyUser(UserVo userVo);
	
	int registUser(UserVo userVo);
		
	int deleteUser(String userid);
}

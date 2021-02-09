package kr.or.ddit.user.repository;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.user.model.UserVo;

@Repository("userDao")
public class UserDaoImpl implements UserDao{
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate template;
	
	@Override
	public UserVo selectUser(String userid) {
		return template.selectOne("users.selectUser", userid);
	}

	@Override
	public List<UserVo> selectAllUser() {
		return template.selectList("users.selectAllUser");
	}

	@Override
	public List<UserVo> selectPagingUser(PageVo pageVo) {
		return template.selectList("users.selectPagingUser", pageVo);
	}

	@Override
	public int selectAllUserCnt() {
		return template.selectOne("users.selectAllUserCnt");
	}

	@Override
	public int modifyUser(UserVo userVo) {
		return template.update("users.modifyUser", userVo);
	}

	@Override
	public int registUser(UserVo userVo) {
		return template.insert("users.registUser", userVo);
	}

	@Override
	public int deleteUser(String userid) {
		return template.delete("users.deleteUser", userid);
	}

}

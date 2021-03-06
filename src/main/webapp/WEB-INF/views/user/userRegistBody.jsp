<%@page import="kr.or.ddit.common.model.PageVo"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.user.model.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	$(function() {
		//주소검색 버튼이 클릭 되었을 때 다음주소 api 팝업을 연다
		$("#addrBtn").on("click", function() {
			new daum.Postcode({
				oncomplete : function(data) {

					$("#addr1").val(data.roadAddress); //도로주소
					$("#zipcode").val(data.zonecode); //우편번호

					//사용자 편의성을 위해 상세주소 입력 input 태그로 focus 설정
					$("#addr2").focus();
				}
			}).open();
		});

		//initData();
	});

	function initData() {
		$("#userid").val("ddit_upload");
		$("#usernm").val("대덕신규");
		$("#alias").val("영민빌딩");
		$("#pass").val("ddit_newpass");
		$("#reg_dt").val("2021-01-13");
		$("#addr1").val("대전 중구 중앙로 76");
		$("#addr2").val("4층");
		$("#zipcode").val("34940");
	}
</script>

<form class="form-horizontal" role="form" action="${cp }/user/regist"
	method="post" enctype="multipart/form-data">

	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message code="USERID"/> </label>
		<div class="col-sm-10">
			<input type="text" class="form-control" id="userid" name="userid"
				placeholder="사용자 아이디" value="${param.userid}" /> <span
				style="color: red"><form:errors path="userVo.userid" /></span> <input
				type="file" class="form-control" name="profile" />
		</div>
	</div>

	<div class="form-group">

		<label for="usernm" class="col-sm-2 control-label"><spring:message code="USERNM"/></label>
		<div class="col-sm-10">
			<input type="text" class="form-control" id="usernm" name="usernm"
				placeholder="사용자 이름" value="${param.usernm }" />
		</div>
	</div>

	<div class="form-group">
		<label for="alias" class="col-sm-2 control-label"><spring:message code="ALIAS"/></label>
		<div class="col-sm-10">
			<input type="text" class="form-control" id="alias" name="alias"
				placeholder="별명" value="${param.alias }" />
		</div>
	</div>

	<div class="form-group">
		<label for="pass" class="col-sm-2 control-label"><spring:message code="PASS"/></label>
		<div class="col-sm-10">
			<input type="password" class="form-control" id="pass" name="pass"
				placeholder="비밀번호" value="${param.pass }" />
		</div>
	</div>

	<div class="form-group">
		<label for="reg_dt" class="col-sm-2 control-label"><spring:message code="REG_DT"/></label>
		<div class="col-sm-10">
			<input type="text" class="form-control" id="reg_dt" name="reg_dt"
				placeholder="" value="${param.reg_dt }" />
		</div>
	</div>

	<div class="form-group">
		<label for="addr1" class="col-sm-2 control-label"><spring:message code="ADDR1"/></label>
		<div class="col-sm-8">
			<input type="text" class="form-control" id="addr1" name="addr1"
				placeholder="도로주소" readonly value="${param.addr1 }" />
		</div>
		<div class="col-sm-2">
			<button type="button" id="addrBtn" class="btn btn-default">주소검색</button>
		</div>
	</div>

	<div class="form-group">
		<label for="addr2" class="col-sm-2 control-label"><spring:message code="ADDR2"/></label>
		<div class="col-sm-10">
			<input type="text" class="form-control" id="addr2" name="addr2"
				placeholder="상세주소" value="${param.addr2 }" />
		</div>
	</div>

	<div class="form-group">
		<label for="zipcode" class="col-sm-2 control-label"><spring:message code="ZIPCODE"/></label>
		<div class="col-sm-10">
			<input type="text" class="form-control" id="zipcode" name="zipcode"
				placeholder="우편번호" readonly value="${param.zipcode }" />
		</div>
	</div>

	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<button type="submit" class="btn btn-default">사용자 등록</button>
		</div>
	</div>
</form>


<select name="lang">
	<option value="">언어설정</option>
	<option value="ko">한국어</option>
	<option value="en">영어</option>
</select>

<script>
$(function(){
	$("select[name=lang]").val("${param.lang }");
	$("select[name=lang]").on("change", function(){
		document.location="/user/regist?lang=" + $(this).val(); 
	});
})
</script>















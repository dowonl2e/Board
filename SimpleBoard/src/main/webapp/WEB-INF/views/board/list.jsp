<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/design.css">
	<title>게시판</title>
</head>
<body>
	<div class="contents">
		<form:form commandName="searchForm" action="./boardlist" method="POST">
			
			<div class="table_style1">
				<table>
					<colgroup>
						<col width="10%"/>
						<col />
						<col width="10%"/>
						<col width="10%"/>
					</colgroup>
					<thead>
						<tr>
							<th>No.</th>
							<th>제목</th>
							<th>등록일</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty boardlist}">
								<tr><td>등록된 게시글이 없습니다.</td></tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="list" items="${boardlist}" varStatus="idx">
									<tr>
										<td class="tac">${idx.count}</td>
										<td class="tal">${list.title}</td>
										<td class="tal">${fn:substring(list.insert_time,0,10)}</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</form:form>
	</div>
</body>
</html>
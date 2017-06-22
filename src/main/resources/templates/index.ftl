<!DOCTYPE html>
<html>
<head>
    <title>OAuth2.0网页授权</title>
    <meta name="viewport" content="width=device-width,user-scalable=0">
    <style type="text/css">
        *{margin:0; padding:0}
        table{border:1px dashed #B9B9DD;font-size:12pt}
        td{border:1px dashed #B9B9DD;word-break:break-all; word-wrap:break-word;}
    </style>
</head>
<body>
    <table width="100%" cellspacing="0" cellpadding="0">
        <tr><td width="20%">属性</td><td width="80%">值</td></tr>
        <tr><td>OpenID</td><td>${snsUserInfo.openId!}</td></tr>
        <tr><td>昵称</td><td>${snsUserInfo.nickname!}</td></tr>
        <tr><td>性别</td><td>${snsUserInfo.sex!}</td></tr>
        <tr><td>国家</td><td>${snsUserInfo.country!}</td></tr>
        <tr><td>省份</td><td>${snsUserInfo.province!}</td></tr>
        <tr><td>城市</td><td>${snsUserInfo.city!}</td></tr>
        <tr><td>头像</td><td><img src="${snsUserInfo.headImgUrl!}"/></td></tr>
		<tr><td>特权</td><td>
			<#list privilegeList! as privilege> 
    			${privilege!}
			</#list>
		</td></tr>
        <tr><td>state:</td><td>${state!}</td></tr>
    </table>
</body>
</html>

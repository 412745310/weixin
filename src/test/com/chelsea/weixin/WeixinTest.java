package com.chelsea.weixin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chelsea.weixin.domain.Token;
import com.chelsea.weixin.util.TokenUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class WeixinTest {

	@Autowired
	private TokenUtil tokenUtil;

	@Test
	public void testGetToken() {
		Token token = tokenUtil.getToken();
		System.out.println(token.getAccessToken());
		System.out.println(token.getExpiresIn());
	}

}

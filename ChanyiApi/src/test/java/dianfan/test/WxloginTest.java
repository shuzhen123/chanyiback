package dianfan.test;

import org.junit.Test;

import dianfan.util.AESUtil;

public class WxloginTest {
	
	@Test
	public void WxloginTest() {
		// TODO Auto-generated constructor stub
		String string = AESUtil.getUserInfoFromEncryptedData("MUtXCurWPp57ofwxnBQxxvRBFpT32t3+44s8FnxGXhmFZIb0YqXUSG8gjmi72pw/ljZTsdx1go9NGF7rTuLIpHSoIbWCuQo1mzvNEPXv1F9Xex7Jnds5fWXZwTQ1uMGiwhpcaMUq30bmTfX/K9mfNqbmjapx+qOuYPpfGkx6VzzZYzYdKOtBLYsnxE/bsyjzEq4BcEJrw2/p5tabmnBrYEyFa8JQBV0q2qEfdJdkhBCcfPNmZ104AciDhPaUiwDEocuYmWQ+Dv7UECh2OLK0hmQt1sl19+DpEA3GYusa9XjZQohjfMiTECasXMaazFgEF5c+BSq48mGOyoMFVJShNKYFwDbDfV0OvbdUAH55mQFzU4V3WNFPk7yss1q/BgjwLMYMsjf+/hjZzptrbOXqCgRhQflINaOqdNt2T+z/iJKXo0dJp8/gzEGJg2YV8dfj/QwaXx5DByTQg6YZcHchsqROZ3VDZLTVUUbi/vqIhENctte/0+ug80Duz+qLdHEAu/UIpEuLkOFZLRNNC3WrpQ==", 
				"E0RFybEOPrr9bX9kPs325A==", "WQnx5VF+Oet8GWD2Jm8tlg==");
		System.out.println(string);
	}

}

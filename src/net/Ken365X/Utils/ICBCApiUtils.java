package net.Ken365X.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.IcbcApiException;
import com.icbc.api.request.MybankEnterpriseAccountQaccbalRequestV1;
import com.icbc.api.request.MybankEnterpriseAccountQaccbalRequestV1.MybankEnterpriseAccountQaccbalRequestBizV1;
import com.icbc.api.request.MybankEnterprisePayPayentRequestV1;
import com.icbc.api.request.MybankEnterprisePayPayentRequestV1.MybankEnterprisePayPayentRequestBizV1;
import com.icbc.api.request.MybankEnterprisePayPayentRequestV1.MybankEnterprisePayPayentRequestRdV1;
import com.icbc.api.request.MybankEnterpriseTradeQhisdRequestV1;
import com.icbc.api.request.SettlementAccountBindingQueryRequestV1;
import com.icbc.api.request.MybankEnterpriseTradeQhisdRequestV1.MybankEnterpriseTradeQhisdRequestBizV1;
import com.icbc.api.request.SettlementAccountBindingQueryRequestV1.SettlementAccountBindingQueryRequestV1Biz;
import com.icbc.api.response.MybankEnterpriseAccountQaccbalResponseV1;
import com.icbc.api.response.MybankEnterpriseAccountQaccbalResponseV1.MybankEnterpriseAccountQaccbalResponseV1Rd;
import com.icbc.api.response.MybankEnterprisePayPayentResponseV1;
import com.icbc.api.response.MybankEnterpriseTradeQhisdResponseV1;
import com.icbc.api.response.SettlementAccountBindingQueryResponseV1;
import com.icbc.api.response.MybankEnterpriseTradeQhisdResponseV1.MybankEnterpriseTradeQhisdResponseRdV1;

public class ICBCApiUtils {
	private static final String SANDBOX_APP_ID = "IICAMP0000000002";
	private final static String SANDBOX_PRI_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCqYfK6aohZN2zz+l38LUU+3ogQtIWgA92QUA5kzOTBseEnuIhcKrc43d5IxpFoA8rFlA3FWr7ZDsFXQXdknyALVHCuH0ZSa1AUDUmqaPycClDt1GUc/8iGbZXZY6KBLV5F2+d4746OG6lYWOuo+NenMuJlf0GUTeVmdLoKU7uxsmaQKWiGiL/O6GKoOkF8BrbknKXcw3XZMaGreq6ZxpNf2vfSpYYjwSX337TZ/O/g3Ml6aRUs5UPDMbQLLOfp6EJRMkn6OYQn1Hgo+oRoSs4CsTRJjRQIBZ8+zQOKhk4AMo2bg3EuNcEszcWNCNxWTVSim/qbMjD8Ch6SLnY6OBnAgMBAAECggEAOmjw5Fu6iz0dAtHx64U2Xg/hU2SrqBVNVC7tOA++4X4JWRbxvcMxDpe9A/EWQIPB5CAQcd9oOMNgwxAHnDps35vizepNOi2+fl6ctBDn0mwjdjsQBA4qwqK96anjo31hZqV8Zxe1JS+u/s1fk2V+MgBj2LitKNLLx1vBdblpqMGZLeuqmTtUms1ktovp9NqqA7X6cmnWqgIRxgo5p/Dm6AnjOrU6ZdK79qD7N+1APlcQR07wRCwNlQcl7AIJxES/lFeEOStgi9VxvkauBk82uJK4+IFF6fLDujIQjkLjT03rWInMiHkTAb342BlG1E3iKMrPUFJ2agDvYL+pJIFgiQKBgQDv9kPDjCM2McyZDYzbm6ouTuWFuG5CPlYmu7W9QRBKRaKZQ2un6QzeTC69Cl3WRNYlaMzA52OqseL4goH8moKSTu/HQAkV7IBXUF6SzC94UgFSLGdObHw4lFVg+rE46jWrnAe2d+k6lhn5cfK0gRXNghZaTb1cgDAMQkM6CK+49QKBgQCLZSdNVE+9je8aWYszYB0yaEXMAPa3sZ9LRK6xYgDmp3c49jOF4yCm2ylnuzY0wtVfBPkZ0hg2V0jwrX058vTOnZvt/FMtBnSLv90TR71765uhpM4C4VqBLjIyCfGbABef58zxcAvh25kI4WClfn7s5feH2k7ptqLIJwIQ7ykKawKBgAPwkyQ+xMU7BBTvOATTBXRFL1eGs6i6xUdVhaJZ0x6lDgpeJQJYvTFZJttLtEa0Up/CnLn+EpDW4tcZrXCsSMCGM0GeN4f4nhLQ6vyWBaGAFy12NSwAc/mp1c0F0KsSjVcH9Mh2J4oitMJ3sHhuNiFtpVhONnGyWReabughBGGRAoGABUDsuEdjMfu1dULGskr/DrVLXCL1KXsyS73s75aH/il1ntn4myUyR9eeueAebOKi9A/V65k1fGbRwvBm9dYoT9k01UVbRSknH1U92cosZA0/MlppwUYJCTFahvRE/NiGC2rLGJZmfD6wh9smr1YTJo6MQ7yAmOJfRDfkJgs9er0CgYEAzUag0LnIaLMXf1/Pclol089N8RE5gyLS0GPUsdSbChC5veNV1SO/ceCPZWIhqf669srI5s8qAizE4BknK04l/TQg/BGBblH+B+z+jIZbX14LjehHSET568GD4WpyZQ+Im4RoP8GPYBNFD9auMWgo89QtoY5LhrhjHmjZfbRd1yM=";
	private final static String SANDBOX_APIGW_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwFgHD4kzEVPdOj03ctKM7KV+16"
			+ "bWZ5BMNgvEeuEQwfQYkRVwI9HFOGkwNTMn5hiJXHnlXYCX+zp5r6R52MY0O7BsTCL"
			+ "T7aHaxsANsvI9ABGx3OaTVlPB59M6GPbJh0uXvio0m1r/lTW3Z60RU6Q3oid/rNhP" + "3CiNgg0W6O3AGqwIDAQAB";
	// 沙盒环境网关
	private static String SANDBOX_BASE_URL = "https://apisandbox.dccnet.com.cn/api/";
	// 专用环境网关
	private static String BASE_URL = "https://gw-api-iicamp.dccnet.com.cn/api/";
	
	
	/**
	 * 正式转账
	 * @return
	 * true 成功
	 * false 失败
	 */
	public static boolean beginTransfer(){
		if(getBalance().compareTo(new BigDecimal("100")) >= 0){
			if(transferAccount()){
				System.out.println("转账成功");
				return true;
			} else {
				System.out.println("转账失败");
				return false;
			}
		} else {
			System.out.println("余额不足");
			return false;
		}
		
	}
	
	/**
	 * 绑定卡
	 * @param mediumId 卡号
	 * @return 
	 * true 成功
	 * false 失败
	 */
	public static boolean bindingCard(String mediumId){
		DefaultIcbcClient client = new DefaultIcbcClient(SANDBOX_APP_ID, SANDBOX_PRI_KEY, SANDBOX_APIGW_PUBLIC_KEY);
        SettlementAccountBindingQueryRequestV1 request = new SettlementAccountBindingQueryRequestV1();
        SettlementAccountBindingQueryRequestV1Biz bizContent = new SettlementAccountBindingQueryRequestV1Biz();
        request.setServiceUrl(SANDBOX_BASE_URL + "settlement/account/binding/V1/query");
        bizContent.setCorpNo("corpInst1234"); //合作方机构编号
        bizContent.setTrxAccDate("20170315"); //合作方交易日期
        bizContent.setTrxAccTime("133001"); //合作方交易时间
        bizContent.setCorpDate("20170315"); //合作方工作日期
        bizContent.setCorpSerno("ABC123456789"); //合作方交易单号
        bizContent.setOutServiceCode("querybinding"); //外部服务代码
        bizContent.setMediumId("6214760200000022233"); //工行联名卡号
        bizContent.setSecretKey("ASDQWEQDZCSDFAWWQDA"); //sm4对称密钥
        request.setBizContent(bizContent);
        SettlementAccountBindingQueryResponseV1 response;
		try {
			response = client.execute(request, "msgId");
			if (response.isSuccess() ) {
				//成功
				System.out.println("success");
				System.out.println(response.getReturnMsg());
				System.out.println(response.getReturnCode());
				//response.getAccountBalance();
				return true;
			} else {
				//失败
				System.out.println(response.getReturnMsg());
				System.out.println(response.getReturnCode());
			}
		} catch (IcbcApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 查询账户余额
	 * 
	 * @return
	 */
	public static BigDecimal getBalance() {
		String URI = "mybank/enterprise/account/qaccbal/V1";
		DefaultIcbcClient client = new DefaultIcbcClient(SANDBOX_APP_ID, SANDBOX_PRI_KEY, SANDBOX_APIGW_PUBLIC_KEY);
		try {
			MybankEnterpriseAccountQaccbalRequestBizV1 bizContent = new MybankEnterpriseAccountQaccbalRequestBizV1();
			MybankEnterpriseAccountQaccbalRequestV1 request = new MybankEnterpriseAccountQaccbalRequestV1();
			// 请对照接口文档用bizContent.setxxx()方法对业务上送数据进行赋值
			bizContent.setfSeqno("123");
			request.setServiceUrl(SANDBOX_BASE_URL + URI);
			request.setBizContent(bizContent);
			// 输出余额查询响应对象，需根据实际调用服务更换（MybankEnterpriseAccountQaccbalResponseV1）
			MybankEnterpriseAccountQaccbalResponseV1 response = client.execute(request);
			if (response.isSuccess()) {
				// 业务成功处理
				System.out.println("success");//
				System.out.println(response.getReturnMsg());
				List<MybankEnterpriseAccountQaccbalResponseV1Rd> list = response.getRd();
				BigDecimal balance = new BigDecimal(0);
				for (MybankEnterpriseAccountQaccbalResponseV1Rd mybankEnterpriseAccountQaccbalResponseV1Rd : list) {
					balance = balance.add(mybankEnterpriseAccountQaccbalResponseV1Rd.getBalance());
					System.out.println(mybankEnterpriseAccountQaccbalResponseV1Rd.getBalance());
				}
				System.out.println(balance);
				return balance;
			} else {
				// 失败
				System.out.println("error");
				System.out.println(response.getReturnCode());
				System.out.println(response.getReturnMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 账户明细查询
	 */
	public static void getAccountDetail() {
		String URI = "mybank/enterprise/trade/qhisd/V1";
		DefaultIcbcClient client = new DefaultIcbcClient(SANDBOX_APP_ID, SANDBOX_PRI_KEY, SANDBOX_APIGW_PUBLIC_KEY);
		try {
			MybankEnterpriseTradeQhisdRequestV1 request = new MybankEnterpriseTradeQhisdRequestV1();
			MybankEnterpriseTradeQhisdRequestBizV1 bizContent = new MybankEnterpriseTradeQhisdRequestBizV1();
			// 请对照接口文档用bizContent.setxxx()方法对业务上送数据进行赋值
			bizContent.setfSeqno("123");
			request.setServiceUrl(SANDBOX_BASE_URL + URI);
			request.setBizContent(bizContent);
			// 根据实际调用服务更换（MybankEnterpriseTradeQhisdResponseV1）
			MybankEnterpriseTradeQhisdResponseV1 response = client.execute(request);
			if (response.isSuccess()) {
				// 业务成功处理
				System.out.println("success");//
				ArrayList<MybankEnterpriseTradeQhisdResponseRdV1> list = response.getRd();
				if (null != list) {
					System.out.println(list.size());
					for (MybankEnterpriseTradeQhisdResponseRdV1 mybankEnterpriseTradeQhisdResponseRdV1 : list) {
						System.out.println(mybankEnterpriseTradeQhisdResponseRdV1.getBalance());
					}

				}
			} else {
				// 失败
				System.out.println(response.getReturnCode());
				System.out.println(response.getReturnMsg());
				System.out.println("error");
			}
		} catch (IcbcApiException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 转账
	 * @return 
	 * 成功 true
	 * 失败 false
	 */
	public static boolean transferAccount(){
		String URI = "mybank/enterprise/pay/payent/V1"; // api地址
        DefaultIcbcClient client = new DefaultIcbcClient(SANDBOX_APP_ID, SANDBOX_PRI_KEY, SANDBOX_APIGW_PUBLIC_KEY);
        try {
            MybankEnterprisePayPayentRequestBizV1 bizContent =
                    new MybankEnterprisePayPayentRequestBizV1();
            MybankEnterprisePayPayentRequestV1 request = new MybankEnterprisePayPayentRequestV1();
            // 请对照接口文档用bizContent.setxxx()方法对业务上送数据进行赋值
            bizContent.setfSeqno("123");
            request.setServiceUrl(SANDBOX_BASE_URL + URI);
            ArrayList<MybankEnterprisePayPayentRequestRdV1> rd = new ArrayList<MybankEnterprisePayPayentRequestRdV1>();
            MybankEnterprisePayPayentRequestRdV1 req = new MybankEnterprisePayPayentRequestRdV1();
            req.setiSeqno("00123456789");
            req.setPayType("1");
            req.setPayerCnname("测试");
            req.setPayerEnname("testAccount");
            req.setManagerCardName("ManagerCardName");
            req.setManagerCardNo("00123456789");
            req.setPayeeAccount("0200099838006000187");
            req.setPayeeEnname("1870000");
            req.setPayeeEnname("testAccount");
            req.setIoFlag("1");
            req.setSamecityFlag("1");
            req.setPayeeType("1");
            req.setPayeeIcbcAreaCode("0200");
            req.setPayeeCity("北京");
            req.setPayeeBankNo("00123456789");
            req.setPayeeBankName("1");
            req.setCurrency("c");
            req.setAmount(new BigDecimal("1000000"));
            req.setPurposeCode("1");
            req.setPurpose("shoping");
            rd.add(req);
            bizContent.setRd(rd);
            request.setBizContent(bizContent);
            // 输出支付指令提交响应对象，需根据实际调用服务更换（MybankEnterprisePayPayentResponseV1）
            MybankEnterprisePayPayentResponseV1 response = client.execute(request);
            if (response.isSuccess()) {
                // 业务成功处理
                System.out.println("success");//
                return true;
            } else {
                // 失败
                System.out.println("error");
                System.out.println(response.getReturnMsg());
                System.out.println(response.getReturnCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
	
	
	
	public static void main(String[] args) {
//		ICBCApiUtils.getBalance();
//		ICBCApiUtils.getAccountDetail();
//		ICBCApiUtils.transferAccount();
//		ICBCApiUtils.bindingCard("123");
		ICBCApiUtils.beginTransfer();
	}
}

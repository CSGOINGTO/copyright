package net.Ken365X.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import com.jcraft.jsch.JSchException;


public class BlockChainUtils {
	static final String FILENAME = "blockChain.txt";

	/**
	 * 创作过程确权
	 * @param masterpriseId
	 * @param createDate
	 * @param content
	 * @param contentRight
	 * @return
	 * @throws IOException
	 */
	public static Object contentRight(String masterpriseId, String createDate, String content, String contentRight) throws IOException{
		String contentHash = new SimHash(content, 64).getStrSimHash();
		contentRight = string2Byte(contentRight);
		JschUtils ju = new JschUtils();
		try {
			ju.exec("docker exec cli /bin/bash ./get.sh '{\"Args\":[\"contentRight\",\"" + masterpriseId + "\",\"" + createDate
					+ "\",\"" + contentHash + "\",\"" + contentRight + "\"]}'");
		} catch (JSchException e) {
			e.printStackTrace();
		}
		String str = getResString();
		String res[] = str.split("txid:");
		String re = res[1].split("\"")[0];
		System.out.println(re);
		if (str.contains("the masterprise onchain success!")) {
			System.out.println("the masterprise onchain success!!!");
			return re;
		} else
			return null;
	}
	
	/**
	 * 更改作品版权的所属�??
	 * @param masterpriseId
	 * @param contentRight
	 * @return
	 */
	public static boolean changeRightById(String masterpriseId, String contentRight){
		JschUtils ju = new JschUtils();
		try {
			ju.exec("docker exec cli /bin/bash ./get.sh '{\"Args\":[\"changeRightById\",\"" + masterpriseId + "\",\"" + contentRight
					+ "\"]}'");
		} catch (JSchException e) {
			e.printStackTrace();
		}
		
		String str = getResString();
		System.out.println(str);
		if (str.contains("the masterprise right change success!!!")) {
			System.out.println("the masterprise right change success!!!");
			return true;
		}
		return false;
	}
	
	/**
	 * 通过masterpriseId获取masterprise
	 * @param masterpriseId
	 * @return
	 */
	public static String[] masterpriseById(String masterpriseId){
		JschUtils ju = new JschUtils();
		try {
			ju.exec("docker exec cli /bin/bash ./get.sh '{\"Args\":[\"masterpriseById\",\"" + masterpriseId + "\"]}'");
		} catch (JSchException e) {
			e.printStackTrace();
		}
		String str = getResString();
		System.out.println(str);
		if(str.contains("\\\"masterpriseId\\\":\\\"\\\"")) {
			return null;
		}
		String str1 = str.substring(str.indexOf("{") + 1, str.indexOf("}"));
		String[] strRes = str1.split("\\\\\"");
		
		String masId = strRes[3];
		String creDate = strRes[7];
		String creHash = strRes[11];
		String conRight = strRes[15];
		System.out.println(masId + " " + creDate + " " + creHash + " " + conRight);
		return new String[]{masId, creDate, creHash, conRight};
	}
	
	/**
	 * 获取指定id范围内的hash
	 * @param start
	 * @param end
	 * @return
	 */
	public static LinkedList<String[]> getContentHash(String start, String end){
		JschUtils ju = new JschUtils();
		LinkedList<String[]> lists = new LinkedList<String[]>();
		try {
			ju.exec("docker exec cli /bin/bash ./get.sh '{\"Args\":[\"getContentHash\",\"" + start + "\",\"" + end + "\"]}'");
		} catch (JSchException e) {
			e.printStackTrace();
		}
		String str = getResString();
		System.out.println(str);
		String str1 = str.substring(str.indexOf("[{") + 1, str.indexOf("}]") + 1);
		String recodes[] = str1.split(":");
		for(int i = 2; i < recodes.length; i += 2) {
			String res = recodes[i].split("}")[0];
			System.out.println(res);
			String[] ress = res.split(" ");
			lists.add(new String[] {byte2String(ress[0]), ress[1]});
		}
		// 0是版权所有者  1是simhash
		for (String[] string : lists) {
			System.out.println(string[0] + " " + string[1]);
		}
		System.out.println(str1);
		return lists;
	}
	
	private static String getResString() {
		String str = "";
		File file = new File(FILENAME);
		FileInputStream fin = null;
		int size = 0;
		byte[] bs = null;
		try {
			fin = new FileInputStream(file);
			size = fin.available();
			bs = new byte[size];
			fin.read(bs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fin != null)
				try {
					fin.close();
					file.delete();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		str = new String(bs);
		return str;
	}
	
	private static String string2Byte(String string) {
		byte[] bs = string.getBytes();
		String byteString = new String();
		for (int i = 0; i < bs.length; i++) {
			byteString = byteString + new Byte(bs[i]).toString() + "+";
		}
		return byteString;
	}

	private static String byte2String(String string) {

		String[] strings = string.split("\\+");
		byte[] bs = new byte[strings.length];
		for (int i = 0; i < strings.length; i++) {
			int a = Integer.parseInt(strings[i].trim());
			bs[i] = (byte) a;
		}
		return new String(bs);
	}
	
	public static void main(String[] args) throws IOException {
		//BlockChainUtils.contentRight("1", "1234time", "lala123", "liuxue222");
		//BlockChainUtils.getContentHash("id", "id1");
	}
}

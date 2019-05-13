package net.Ken365X.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * @author ����
 * @version ����ʱ�䣺2018��3��19������9:34:34 ��˵��
 */
public class JschUtils {
	private static final String USER = "root";
	private static final String PASSWORD = "aSd123456";
	private static final String HOST = "39.107.228.162";
	private static final int DEFAULT_SSH_PORT = 22;
	private static JSch jsch = new JSch();
	private static Session session = null;
	private static ChannelExec channel = null;

	public synchronized String exec(String command) throws JSchException {
		session = jsch.getSession(USER, HOST, DEFAULT_SSH_PORT);
		session.setPassword(PASSWORD);
		session.setConfig("userauth.gssapi-with-mic", "no");
		session.setConfig("StrictHostKeyChecking", "no");
		// username and password will be given via UserInfo interface.
		session.setUserInfo(new MyUserInfo());
		session.connect(3000);
		channel = (ChannelExec) session.openChannel("exec");
		StringBuffer sb = new StringBuffer();
		try {

			((ChannelExec) channel).setCommand(command);

			// X Forwarding
			// channel.setXForwarding(true);

			// channel.setInputStream(System.in);
			channel.setInputStream(null);
			// channel.setOutputStream(System.out);

			FileOutputStream fos = new FileOutputStream("blockChain.txt");
			((ChannelExec) channel).setErrStream(fos);
			// ((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			// System.out.println(reader.readLine());
			String buf = null;
			while ((buf = reader.readLine()) != null) {
				sb.append(buf + "\n");
			}

			// channel.disconnect();
			// session.disconnect();

		} catch (Exception e) {
			sb.append(e.getMessage() + "\n");
		} finally {
			channel.disconnect();
			session.disconnect();
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	private static class MyUserInfo implements UserInfo {

		@Override
		public String getPassphrase() {
			System.out.println("getPassphrase");
			return null;
		}

		@Override
		public String getPassword() {
			System.out.println("getPassword");
			return null;
		}

		@Override
		public boolean promptPassword(String s) {
			System.out.println("promptPassword:" + s);
			return false;
		}

		@Override
		public boolean promptPassphrase(String s) {
			System.out.println("promptPassphrase:" + s);
			return false;
		}

		@Override
		public boolean promptYesNo(String s) {
			System.out.println("promptYesNo:" + s);
			return true;// notice here!
		}

		@Override
		public void showMessage(String s) {
			System.out.println("showMessage:" + s);
		}
	}


	public static void main(String[] args) throws JSchException {
		JschUtils ju = new JschUtils();
		ju.exec("ls");
	}
}

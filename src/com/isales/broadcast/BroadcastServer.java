package com.isales.broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BroadcastServer implements Runnable {

	private static Logger LOG = LoggerFactory.getLogger(BroadcastServer.class.getName());
	
	private static int BROADCAST_PORT = 9898;
	private static String BROADCAST_IP = "224.0.0.1";
	InetAddress inetAddress = null;
	Thread t = null;
	/* 发送广播端的socket */
	MulticastSocket multicastSocket = null;
	/* 发送广播的按钮 */
	private volatile boolean isRuning = true;

	public BroadcastServer() {
		try {
			inetAddress = InetAddress.getByName(BROADCAST_IP);
			multicastSocket = new MulticastSocket(BROADCAST_PORT);
			multicastSocket.setTimeToLive(1);
			multicastSocket.joinGroup(inetAddress);

		} catch (Exception e) {
			e.printStackTrace();

		}
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		{
			DatagramPacket dataPacket = null;
			// 将本机的IP（这里可以写动态获取的IP）地址放到数据包里，其实server端接收到数据包后也能获取到发包方的IP的
			byte[] buf = new byte[1024];
			dataPacket = new DatagramPacket(buf, buf.length, inetAddress,
					BROADCAST_PORT);
			while (true) {
				if (isRuning) {
					try {
						multicastSocket.receive(dataPacket);
						int length = dataPacket.getLength();
						System.out.println("receive client content-length:" + length);
						String content = new String(buf, "UTF-8");
						System.out.println("receive client content:{}" + content);
					} catch (Exception e) {
						System.out.println("receive client content error.{}" + e.getMessage());
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		new BroadcastServer();
	}

}

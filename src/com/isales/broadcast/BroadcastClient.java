package com.isales.broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BroadcastClient implements Runnable {

	private static Logger LOG = LoggerFactory.getLogger(BroadcastClient.class.getName());
	
	private MulticastSocket multicastSocket = null;
	private static int BROADCAST_PORT = 9898;
	private static String BROADCAST_IP = "224.0.0.1";
	InetAddress inetAddress = null;
	Thread thread = null;
	private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();

	public BroadcastClient() {
		thread = new Thread(this);
		try {
			multicastSocket = new MulticastSocket(BROADCAST_PORT);
			inetAddress = InetAddress.getByName(BROADCAST_IP);
			multicastSocket.joinGroup(inetAddress);
			thread.start();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				String content = queue.take();
				LOG.info("send content = ", content);
				DatagramPacket dp = null;
				byte[] bytes = content.getBytes("UTF-8");
				dp = new DatagramPacket(bytes, bytes.length, inetAddress, BROADCAST_PORT);
				multicastSocket.send(dp);
			} catch (Exception e) {
				LOG.error("send content error.{}", e.getMessage());
			}
		}
	}
	public static void send(String content) {
		queue.add(content);
	}
	public static void main(String[] args) {
		new BroadcastClient();
		int i = 1;
		while(true) {
			try {
				send("content-" + i);
				i ++;
				Thread.sleep(2000);
			} catch(Exception e) {
				
			}
		}
	}
}

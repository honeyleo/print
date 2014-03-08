package com.isales.broadcast;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemMonClient extends IoHandlerAdapter {

	private final static Logger LOGGER = LoggerFactory.getLogger(MemMonClient.class);  
	  
    private IoSession session;  
  
    private IoConnector connector;  
    /** 
     * Default constructor. 
     */  
    public MemMonClient() {  
        connector = new NioDatagramConnector();  
        connector.setHandler(this);  
        ConnectFuture connFuture = connector.connect(new InetSocketAddress(  
                "localhost", MemoryMonitor.PORT));  // 这样不太好吧  
        connFuture.awaitUninterruptibly();  
        // 给conn添加一个监听器  
        connFuture.addListener(new IoFutureListener<ConnectFuture>() {  
            public void operationComplete(ConnectFuture future) {  
                if (future.isConnected()) {  
                    session = future.getSession();  
                    try {  
                        sendData();  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                } else {  
                    try {  
                        throw new Exception(" 连接错误。 ");  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        });  
    }  
  
    private void sendData() throws InterruptedException {  
        for (int i = 0; i < 10; i++) {  
            long free = Runtime.getRuntime().freeMemory(); // 得到当前空闲内存大小  
            IoBuffer buffer = IoBuffer.allocate(8);  
            buffer.putLong(free);     // 只把剩余内存大小放入buffer， 扔给server            
            buffer.flip();  
            session.write(buffer);    // 写入  
  
            try {  
                Thread.sleep(1000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
                throw new InterruptedException(e.getMessage());  
            }  
        }  
    }  
  
    @Override  
    public void exceptionCaught(IoSession session, Throwable cause)  
            throws Exception {  
        cause.printStackTrace();  
    }  
  
    @Override  
    public void messageReceived(IoSession session, Object message)  
            throws Exception {  
        Charset c = Charset.forName("UTF-8");  
        CharsetDecoder cd = c.newDecoder();  
        IoBuffer buffer = (IoBuffer)message;  
        System.out.println("客户端收到来自服务器的消息String:" + (buffer.getString(cd)));  
    }  
  
    @Override  
    public void messageSent(IoSession session, Object message) throws Exception {  
        System.out.println("客户端向服务器发送信息：" + ((IoBuffer)message).getLong());  
    }  
  
    @Override  
    public void sessionClosed(IoSession session) throws Exception {  
        System.out.println("客户端关闭了当前会话");  
    }  
  
    @Override  
    public void sessionCreated(IoSession session) throws Exception {  
        System.out.println("客户端成功创建session");  
    }  
  
    @Override  
    public void sessionIdle(IoSession session, IdleStatus status)  
            throws Exception {  
    }  
  
    @Override  
    public void sessionOpened(IoSession session) throws Exception {  
        System.out.println("客户端成功开启一个session id:"+session.getId());  
    }  
  
    public static void main(String[] args) {  
        new MemMonClient();  
    }  
}

package com.isales.broadcast;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MemoryMonitorHandler extends IoHandlerAdapter{

	private MemoryMonitor server;  
	  
    public MemoryMonitorHandler(MemoryMonitor server) {  
        this.server = server;  
    }  
  
    /** 
     * 异常来关闭session 
     */  
    @Override  
    public void exceptionCaught(IoSession session, Throwable cause)  
            throws Exception {  
        cause.printStackTrace();  
        session.close(true);  
    }  
  
    /** 
     * 服务器端收到一个消息 
     */  
    @Override  
    public void messageReceived(IoSession session, Object message)  
            throws Exception {  
  
        if (message instanceof IoBuffer) {  
            IoBuffer buffer = (IoBuffer) message;  
            buffer.setAutoExpand(true);  
            System.out.println("服务器端获得udp信息:" + buffer.getLong());  
            Charset c = Charset.forName("UTF-8");            
            CharsetEncoder ce = c.newEncoder();  
              
            // 给client返回信息 IoBuffer.wrap  
            IoBuffer clientBuffer = IoBuffer.wrap((new Date().toLocaleString() + "服务器已收到。").getBytes(c));  
            clientBuffer.setAutoExpand(true);  
            session.setAttribute("clientbuffer", clientBuffer);  
            session.write(clientBuffer);  
        }  
    }  
  
    @Override  
    public void sessionClosed(IoSession session) throws Exception {  
        System.out.println("服务器端关闭session...");  
    }  
  
    @Override  
    public void sessionCreated(IoSession session) throws Exception {  
        System.out.println("服务器端成功创建一个session...");  
    }  
  
    @Override  
    public void sessionIdle(IoSession session, IdleStatus status)  
            throws Exception {  
       //  System.out.println("Session idle...");  
    }  
  
    @Override  
    public void sessionOpened(IoSession session) throws Exception {  
        System.out.println("服务器端成功开启一个session...");  
    }  
}

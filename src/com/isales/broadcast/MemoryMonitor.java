package com.isales.broadcast;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

public class MemoryMonitor {

    public static final int PORT = 18567;  
  
    public MemoryMonitor() throws IOException {  
        // 创建UDP数据包NIO  
        NioDatagramAcceptor acceptor = new NioDatagramAcceptor();  
        // NIO设置底层IOHandler 把服务器的本身传入  
        acceptor.setHandler(new MemoryMonitorHandler(this));  
  
        // 设置filter  
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();  
        chain.addLast("logger", new LoggingFilter());  
  
        // 设置是否重用地址？ 也就是每个发过来的udp信息都是一个地址？  
        DatagramSessionConfig dcfg = acceptor.getSessionConfig();  
        dcfg.setReuseAddress(true);  
  
        // 绑定端口地址  
        acceptor.bind(new InetSocketAddress(PORT));  
        System.out.println("UDPServer listening on port " + PORT);  
    }  
      
    public static void main(String[] args) {  
        try {  
            new MemoryMonitor();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
}

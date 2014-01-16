package com.isales.httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServer {
	static Logger LOG = LoggerFactory.getLogger(HttpServer.class.getName());
	/** Default HTTP port */
	private static final int DEFAULT_PORT = 8000;
	private NioSocketAcceptor acceptor;
	private boolean isRunning;

	private String encoding;
	private HttpHandler httpHandler;

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
		HttpRequestDecoder.defaultEncoding = encoding;
		HttpResponseEncoder.defaultEncoding = encoding;
	}

	public HttpHandler getHttpHandler() {
		return httpHandler;
	}

	public void setHttpHandler(HttpHandler httpHandler) {
		this.httpHandler = httpHandler;
	}

	/**
	 * 启动HTTP服务端箭筒HTTP请求
	 * 
	 * @param port要监听的端口号
	 * @throws IOException
	 */
	public void run(int port) throws IOException {
		synchronized (this) {
			if (isRunning) {
				LOG.warn("服务器已经启动.");
				return;
			}
			acceptor = new NioSocketAcceptor();
			acceptor.getFilterChain().addLast(
					"protocolFilter",
					new ProtocolCodecFilter(
							new HttpServerProtocolCodecFactory()));
//			 acceptor.getFilterChain().addLast("logger", new LoggingFilter());
			ServerHandler handler = new ServerHandler();
			handler.setHandler(httpHandler);
			acceptor.setHandler(handler);
			acceptor.bind(new InetSocketAddress(port));
			isRunning = true;
			LOG.info("服务器启动成功，监听端口[{}].", port);
		}
	}

	/**
	 * 使用默认端口8080
	 * 
	 * @throws IOException
	 */
	public void run() throws IOException {
		run(DEFAULT_PORT);
	}

	/**
	 * 停止监听HTTP服务
	 */
	public void stop() {
		synchronized (this) {
			if (!isRunning) {
				LOG.warn("服务器已经停止.");
				return;
			}
			isRunning = false;
			try {
				acceptor.unbind();
				acceptor.dispose();
				LOG.info("服务器停止成功.");
			} catch (Exception e) {
				LOG.error("服务器停止出现异常.异常信息:{},{}", e.getMessage(), e);
			}
		}
	}
	
}


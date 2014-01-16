package com.isales;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.isales.httpserver.HttpHandler;
import com.isales.httpserver.HttpRequestMessage;
import com.isales.httpserver.HttpResponseMessage;
import com.isales.httpserver.HttpServer;
import com.isales.print.IsalesPrintUtils;
import com.isales.print.Message;
import com.isales.print.Print;
import com.isales.print.PrintData;

public class HttpServerStarter {
	
	static Logger LOG = LoggerFactory.getLogger(HttpServerStarter.class.getName());
	
	static HttpServer server = new HttpServer();
	
	public static void start(int port) throws IOException,
			InterruptedException {
		server.setEncoding("UTF-8");
		server.setHttpHandler(new HttpHandler() {
			public HttpResponseMessage handle(HttpRequestMessage request) {
				Message.Builder builder = Message.newBuilder();
				HttpResponseMessage response = new HttpResponseMessage();
				response.setContentType("text/plain");
				try {
					String content = request.getContent();
					LOG.info("移动终端请求打印JSON数据对象[{}]", content);
					PrintData printData = JSON.parseObject(content, PrintData.class);
				    String fileName = null;
				    if(printData == null) {
				    	throw new IllegalArgumentException("数据对象错误");
				    }
				    fileName = IsalesPrintUtils.writeXls(printData);
				    if(fileName != null) {
				    	LOG.info("生成Excel表格成功，文件绝对路径[{}].", fileName);
				    	int count = 1;
				    	if(printData.getCount() > 0) {
				    		count = printData.getCount();
				    	}
				    	LOG.info("开始打印生成的Excel表格，打印的份数[{}].", count);
				    	LOG.info("正在处理打印......");
				    	Print.printExcel(fileName, count);
				    	LOG.info("打印完成");
				    }
				} catch(Exception e) {
					LOG.error("处理终端打印请求出现异常：{},{}", e.getMessage(), e);
					response.setResponseCode(HttpResponseMessage.HTTP_STATUS_SUCCESS);
					builder.setRet(1).setMsg("fail");
				}
				response.setResponseCode(HttpResponseMessage.HTTP_STATUS_SUCCESS);
				response.appendBody(JSON.toJSONString(builder.build()));
				return response;
			}
		});
		server.run(port);
	}
	
	public static void start() throws IOException,
		InterruptedException {
		start(8000);
	}
	
	public static void stop() {
		server.stop();
	}
}

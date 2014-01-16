package com.isales.print;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.LibraryLoader;
import com.jacob.com.Variant;

public class Print {
	
	static Logger LOG = LoggerFactory.getLogger(Print.class.getName());
	
    private static final String PRINT_NAME = new String("FX7AF20D"); 
    
    static {
    	String jacob_dll_name = LibraryLoader.getPreferredDLLName() + ".dll";
        String classpath = Thread.currentThread().getContextClassLoader()
        		.getResource("").getPath();
        File dllFile = new File(classpath  + jacob_dll_name);
        if(!dllFile.exists()) {
        	InputStream in = null; 
        	BufferedInputStream reader = null;  
            FileOutputStream writer = null;
            try {
            	in = Thread.currentThread().getContextClassLoader()
        				.getResourceAsStream(jacob_dll_name);
            	
            	reader = new BufferedInputStream(in);  
                writer = new FileOutputStream(dllFile);  
                  
                byte[] buffer = new byte[1024];  
                  
                while (reader.read(buffer) > 0) {  
                    writer.write(buffer);  
                    buffer = new byte[1024];  
                }  
            } catch(Exception e) {
            	LOG.error("load jacob dll error.");
            } finally {
            	if(in != null) {
					try {
						in.close();
					} catch (IOException e) {
						LOG.error("close jacob dll InputStream error.");
					}
            	}
            	if(writer != null) {
            		try {
						writer.close();
					} catch (IOException e) {
						LOG.error("close jacob dll FileOutputStream error.");
					}
            	}
            }
        }
        
        LOG.info("初始化打印组件【 " + dllFile.getPath() + "】");
        System.setProperty("jacob.dll.path", dllFile.getPath());
    }
    
    /**
     * @param path 打印路径地址，形如 \\XX\\YY.xls
     * @param copies 打印份数
     */
    public static void printExcel(String path,int copies){
        if(path.isEmpty()||copies<1){
            return;
        }
        //初始化COM线程
        ComThread.InitSTA();
        //新建Excel对象
        ActiveXComponent xl=new ActiveXComponent("Excel.Application");
        Dispatch excel = null;
        try { 
            System.out.println("Version=" + xl.getProperty("Version"));
            //设置是否显示打开Excel  
            Dispatch.put(xl, "Visible", new Variant(true));
            //打开具体的工作簿
            Dispatch workbooks = xl.getProperty("Workbooks").toDispatch(); 
            excel = Dispatch.call(workbooks,"Open",path).toDispatch(); 
            
            //设置打印属性并打印
            Dispatch.callN(excel,"PrintOut",new Object[]{Variant.VT_MISSING, Variant.VT_MISSING, new Integer(copies),
                    new Boolean(false),PRINT_NAME, new Boolean(true),Variant.VT_MISSING, ""});
            
        } catch (RuntimeException e) {
        	LOG.info("服务器运行时异常：{}", e.getMessage());
		} catch (Exception e) { 
        	LOG.info("处理打印出现异常：{}", e.getMessage());
            LOG.error("处理打印出现异常：{}.{}", e.getMessage(), e);
        } finally{
        	if(excel != null) {
        		//关闭文档
                Dispatch.call(excel, "Close", new Variant(false)); 
        	}
        	if(xl != null) {
        		xl.invoke("Quit",new Variant[0]);
        	}
            //始终释放资源 
            ComThread.Release(); 
        } 
    }
    
    public static void htmlToWord(String html, String docFile) {    
        ActiveXComponent app = new ActiveXComponent("Word.Application"); // 启动word        
        try {    
            app.setProperty("Visible", new Variant(false));    
            Dispatch docs = app.getProperty("Documents").toDispatch();    
            Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[] { html, new Variant(false), new Variant(true) }, new int[1]).toDispatch();    
            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { docFile, new Variant(1) }, new int[1]);    
            Variant f = new Variant(false);    
            Dispatch.call(doc, "Close", f);    
        } catch (Exception e) {    
            e.printStackTrace();    
        } finally {    
            app.invoke("Quit", new Variant[] {});    
            ComThread.Release();    
        }    
    }   
    
}

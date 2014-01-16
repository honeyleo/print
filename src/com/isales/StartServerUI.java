package com.isales;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isales.log.TextAreaAppender;

public class StartServerUI {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());  //  @jve:decl-index=0:
	 	
	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="7,7"
	private JPanel jContentPane = null;
	private JTextArea textArea = null;
	private final JButton log4JButton = new JButton("启动");
	private final JTextField logTextJTextField = new JTextField("8000", 10);

	private JLabel jLabel = null;
	private JScrollPane jScrollPane = null;
	private final JButton LogJdkButton = new JButton("停止");
	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setSize(800, 450);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("东风日产移动终端打印服务器");
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints.gridy = 2;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.fill = GridBagConstraints.BOTH;
			gridBagConstraints31.gridy = 1;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.weighty = 1.0;
			gridBagConstraints31.gridwidth = 3;
			gridBagConstraints31.insets = new Insets(8, 8, 8, 8);
			gridBagConstraints31.gridx = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridwidth = 3;
			gridBagConstraints2.insets = new Insets(8, 8, 0, 0);
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			jLabel = new JLabel();
			jLabel.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel.setText("<html><font color='red'><b>第一步：先设置打印服务器接收端口（确保该端口没有被使用）；第二步：点击启动按钮，启动打印服务器；</b></font></html>");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new Insets(4, 4, 4, 8);
			gridBagConstraints3.gridx = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.insets = new Insets(4, 8, 4, 4);
			gridBagConstraints1.gridy = 2;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getLog4JButton(), gridBagConstraints1);
			jContentPane.add(getLogTextJTextField(), gridBagConstraints3);
			jContentPane.add(jLabel, gridBagConstraints2);
			jContentPane.add(getJScrollPane(), gridBagConstraints31);
			jContentPane.add(getLogJdkButton(), gridBagConstraints);
		}
		return jContentPane;
	}

	/**
	 * This method initializes textArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getLogJTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
			textArea.setText("");
			textArea.setEditable(false);
			textArea.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}
		return textArea;
	}

	/**
	 * This method initializes log4JButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLog4JButton() {
		if (log4JButton != null) {
			log4JButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						HttpServerStarter.start(Integer.parseInt(logTextJTextField.getText()));
						log4JButton.setEnabled(false);
						LogJdkButton.setEnabled(true);
						logTextJTextField.setEditable(false);
					} catch (IOException e1) {
						logger.error("启动服务器出现IO异常：{}", e1.getMessage());
					} catch (InterruptedException e1) {
						logger.error("启动服务器出现中断异常：{}", e1.getMessage());
					} catch (Exception e2) {
						logger.error("启动服务器出现异常：{}", e2.getMessage());
					}
				}
			});
		}
		return log4JButton;
	}

	/**
	 * This method initializes logTextJTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getLogTextJTextField() {
		return logTextJTextField;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getLogJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes LogJdkButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLogJdkButton() {
		if (LogJdkButton != null) {
			LogJdkButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					HttpServerStarter.stop();
					log4JButton.setEnabled(true);
					LogJdkButton.setEnabled(false);
					logTextJTextField.setEditable(true);
				}
			});
		}
		return LogJdkButton;
	}

	/**
	 * Launches this application
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				StartServerUI startServerUI = new StartServerUI();
				startServerUI.getJFrame().setLocation(100,100);
				startServerUI.getJFrame().setVisible(true);
				
				setupLog4JAppender(startServerUI.getLogJTextArea());
				startServerUI.logger.info("打印服务器主界面启动成功！！！！！！");
			}
		});
	}

	protected static void setupLog4JAppender(JTextArea jTextArea) {
		
		TextAreaAppender.setTextArea(jTextArea);
		
		String classpath = Thread.currentThread().getContextClassLoader()
        		.getResource("").getPath();
		
		Properties logProperties = new Properties();
		logProperties.put("log4j.rootLogger", "INFO, CONSOLE, TEXTAREA,print");
		logProperties.put("log4j.appender.CONSOLE", "org.apache.log4j.ConsoleAppender"); // A standard console appender
		logProperties.put("log4j.appender.CONSOLE.layout", "org.apache.log4j.PatternLayout"); //See: http://logging.apache.org/log4j/docs/api/org/apache/log4j/PatternLayout.html
		logProperties.put("log4j.appender.CONSOLE.layout.ConversionPattern", "%d{yyyy-MM-dd HH:mm:ss}:%p %t %c - %m%n");

		logProperties.put("log4j.appender.TEXTAREA", "com.isales.log.TextAreaAppender");  // Our custom appender
		logProperties.put("log4j.appender.TEXTAREA.layout", "org.apache.log4j.PatternLayout"); //See: http://logging.apache.org/log4j/docs/api/org/apache/log4j/PatternLayout.html
		logProperties.put("log4j.appender.TEXTAREA.layout.ConversionPattern", "%d{yyyy-MM-dd HH:mm:ss}:%p %t %c - %m%n");
		
		logProperties.put("log4j.appender.print", "org.apache.log4j.DailyRollingFileAppender");
		logProperties.put("log4j.appender.print.layout", "org.apache.log4j.PatternLayout"); //See: http://logging.apache.org/log4j/docs/api/org/apache/log4j/PatternLayout.html
		logProperties.put("log4j.appender.print.layout.ConversionPattern", "%d{yyyy-MM-dd HH:mm:ss}:%p %t %c - %m%n");
		logProperties.put("log4j.appender.print.File", classpath + "/logs/printInfo.log");
		logProperties.put("log4j.appender.print.DatePattern", "'.'yyyy-MM-dd");
		logProperties.put("log4j.appender.print.Threshold", "INFO");
		
		PropertyConfigurator.configure(logProperties);
	}

}

package com.basicframe.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtil {

	private Properties property;
	 private FileInputStream inputFile;
	 private FileOutputStream outputFile;

	 public PropUtil() {
	  property = new Properties();
	 }

	 public PropUtil(String filePath) {
	  property = new Properties();
	  try {
	   inputFile = new FileInputStream(filePath);
	   property.load(inputFile);
	   inputFile.close();
	  } catch (FileNotFoundException e) {
	   System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	 }

	 /*
	  * 重载函数，得到key的值 @param key 取得其值的键 @return key的值
	  */
	 public String getValue(String key) {
	  if (property.containsKey(key)) {
	   return property.getProperty(key);

	  } else
	   return "";
	 }

	 /*
	  * 重载函数，得到key的值
	  * 
	  * @param fileName propertys文件的路径+文件名 @param key 取得其值的键 @return key的值
	  */
	 public String getValue(String fileName, String key) {
	  try {
	   String value = "";
	   inputFile = new FileInputStream(fileName);
	   property.load(inputFile);
	   inputFile.close();
	   if (property.containsKey(key)) {
	    value = property.getProperty(key);
	    return value;
	   } else
	    return value;
	  } catch (FileNotFoundException e) {
	   e.printStackTrace();
	   return "";
	  } catch (IOException e) {
	   e.printStackTrace();
	   return "";
	  } catch (Exception ex) {
	   ex.printStackTrace();
	   return "";
	  }
	 }

	 /*
	  * 清除properties文件中所有的key和其值
	  */
	 public void clear() {
	  property.clear();
	 }

	 /*
	  * 改变或添加一个key的值，当key存在于properties文件中时该key的值被value所代替， 当key不存在时，该key的值是value
	  * @param key 要存入的键 @param value 要存入的值
	  */
	 public void setValue(String key, String value) {
	  property.setProperty(key, value);
	 }

	 /*
	  * 将更改后的文件数据存入指定的文件中，该文件可以事先不存在。 @param fileName 文件路径+文件名称 @param
	  * description 对该文件的描述
	  */
	 public void saveFile(String fileName, String description) {
	  try {
	   outputFile = new FileOutputStream(fileName);
	   property.store(outputFile, description);
	   outputFile.close();
	  } catch (FileNotFoundException e) {
	   e.printStackTrace();
	  } catch (IOException ioe) {
	   ioe.printStackTrace();
	  }
	 }
	 
	 public Properties getProperties(String configName){
		 Properties dbconfig=new Properties();
	        InputStream in=PropUtil.class.getClassLoader().getResourceAsStream(configName);
	        try {
				dbconfig.load(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return dbconfig;
	 }

	 public static void main(String[] args) {
		 Properties dbconfig=new PropUtil().getProperties("app.properties");
	        System.out.println(dbconfig.getProperty("imagePath", "/weeee/iss"));
	  
	 }

}

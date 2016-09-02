package com.app.util;

import com.Config;
import com.basicframe.common.exception.BusException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/7/8.
 */
@Component
public class FileUpload {
    private Logger logger = Logger.getLogger("log");


    /**
     * 多文件上传(可以单个)
     * @param  src 表示路径 写法如:upload/homeLeftImage 存入数据库路径为:/upload/homeLeftImage/201503/*.jpg
     * @return 返回上传后文件的路径集合
     */
    public  Map<String,String> upload(HttpServletRequest request,String src) throws Exception{
       Map<String,String> map = new HashMap<>();
        //获取当前年份与月份用于作用文件分区
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String tomcat_path = Config.UPLOAD_BASE_PATH;

        System.out.println("tomcat_path--->"+tomcat_path);
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        boolean multipart = multipartResolver.isMultipart(request);


        if (multipart){
            //记录上传过程起始时的时间，用来计算上传时间
            int pre = (int) System.currentTimeMillis();
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                String key = iter.next();

                //取得上传文件
                MultipartFile file = multiRequest.getFile(key);
                if(file != null){
                    //取得当前上传文件的文件名称
                    String originalFilename = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if(originalFilename!=null&&originalFilename.trim() !=""){
                        int index = originalFilename.lastIndexOf(".");
                        String   imageFromat =    originalFilename.substring(index+1);
                        //文件类型验证
                        if("jpg,png,jpeg,gif,bmp".indexOf(imageFromat) == -1){
                            throw new BusException("不支持"+ imageFromat +"类型的相片上传！");
                        }
                        //文件高宽验证
                        long size = file.getSize();
                        if(size < 10 || size > 10 * 1024 * 1024){
                            throw new BusException("相片大小不能小于10K，大于10M！");
                        }
                        //重命名上传后的文件名
                        String fileName = UUID.randomUUID().toString();

                        //定义上传路径
                        String txt_path =tomcat_path+ "/"+ src+"/"+year+""+(month+1)+"/";
                        File file_path = new File(txt_path);
                        if (!file_path.exists()) { //如果文件不存在创建文件夹
                            file_path.mkdirs(); //创建文件夹
                        }
                        String path = txt_path + fileName+".jpg";
                        File localFile = new File(path);
                            file.transferTo(localFile);
                        map.put(key,"/"+ src+"/"+year+""+(month+1)+"/"+ fileName+".jpg");
                    }
                }
            }
            //记录上传该文件后的时间
            int finaltime = (int) System.currentTimeMillis();
            System.out.println("文件上传时间:"+(finaltime - pre));
        }
        return map ;
    }
}

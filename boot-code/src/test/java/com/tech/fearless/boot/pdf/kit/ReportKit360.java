package com.tech.fearless.boot.pdf.kit;

import com.tech.fearless.boot.pdf.kit.component.PDFHeaderFooter;
import com.tech.fearless.boot.pdf.kit.component.PDFKit;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgm on 2017/4/17.
 * 360报告
 *
 */
public class ReportKit360 {
	
	public static Logger log = Logger.getLogger(ReportKit360.class);

    public  String createPDF(Object data, String fileName){
        //pdf保存路径
        try {
            //设置自定义PDF页眉页脚工具类
            PDFHeaderFooter headerFooter=new PDFHeaderFooter();
            PDFKit kit=new PDFKit();
            kit.setHeaderFooterBuilder(headerFooter);
            //设置输出路径
            kit.setSaveFilePath("D:/Users/hello.pdf");
            String saveFilePath=kit.exportToFile(fileName,data);
            return  saveFilePath;
        } catch (Exception e) {
        	System.out.println("竟然失败了，艹！");
        	e.printStackTrace();
//            log.error("PDF生成失败{}", ExceptionUtils.getFullStackTrace(e));
            log.error("PDF生成失败{}");
            return null;
        }
    }

    public static void main(String[] args) {
        ReportKit360 kit = new ReportKit360();
        TemplateBO templateBO = new TemplateBO();
        templateBO.setTemplateName("Hello iText! Hello freemarker! Hello jFreeChart!");
        templateBO.setFreeMarkerUrl("http://www.zheng-hang.com/chm/freemarker2_3_24/ref_directive_if.html");
        templateBO.setITEXTUrl("http://developers.itextpdf.com/examples-itext5");
        templateBO.setJFreeChartUrl("http://www.yiibai.com/jfreechart/jfreechart_referenced_apis.html");
        templateBO.setImageUrl("E:/图片2/004d.jpg");
        List<String> scores=new ArrayList<String>();
        scores.add("94");
        scores.add("95");
        scores.add("98");
        templateBO.setScores(scores);
        String path= kit.createPDF(templateBO,"hello.pdf");
        System.out.println("打印："+path);
    }
}

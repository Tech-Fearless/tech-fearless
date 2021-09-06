package com.tech.fearless.boot.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Snippet {  
    // 利用模板生成pdf  
    public static void fillTemplate() {  
        // 模板路径  
        String templatePath = "D:/Users/战神0.pdf";  
        // 生成的新文件路径  
        String newPDFPath = "D:/Users/战神1.pdf";
        PdfReader reader;  
        FileOutputStream out;  
        ByteArrayOutputStream bos;  
        PdfStamper stamper;  
        try {  
            out = new FileOutputStream(newPDFPath);// 输出流  
            reader = new PdfReader(templatePath);// 读取pdf模板  
            bos = new ByteArrayOutputStream();  
            stamper = new PdfStamper(reader, bos);  
            AcroFields form = stamper.getAcroFields();
            
            String str1 = "在需要打横线的地方打空格，然后选定所打的空格，用工具栏的加下划线就可以了。";
            String str2 = " 昨日入城市,归来泪满巾。 遍身罗绮者,不是养蚕人。";
            String str3 = "Maven 除了以程序构建能力为特色之外，还提供高级项目管理工具。由于 Maven 的缺" +
            		"省构建规则有较高的可重用性，所以常常用两三行 Maven 构建脚本就可以构建简单的项目。由于 Maven " +
            		"的面向项目的方法，许多 Apache Jakarta 项目发文时使用 Maven，而且公司项目采用 Maven 的比例在持续增长。";
            String[] str = {"123456789", "TOP__ONE", "男", "1991-01-01", "130222111133338888", "河北省保定市",str1,str2,str3};  
            int i = 0;  
            java.util.Iterator<String> it = form.getFields().keySet().iterator();  
            while (it.hasNext()) {  
                String name = it.next().toString();  
                System.out.println(name);  
                form.setField(name, str[i++]);  
            }  
            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true  
            stamper.close();  
  
            Document doc = new Document();  
            PdfCopy copy = new PdfCopy(doc, out);  
            doc.open();  
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);  
            copy.addPage(importPage);  
            doc.close();  
  
        } catch (IOException e) {  
            System.out.println(1);  
        } catch (DocumentException e) {  
            System.out.println(2);  
        }  
  
    }  
  
    public static void main(String[] args) {  
        fillTemplate();  
    }  
}
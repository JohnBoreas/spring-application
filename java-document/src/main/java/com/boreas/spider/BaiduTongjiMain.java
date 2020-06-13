package com.boreas.spider;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpWebConnection;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import javax.imageio.ImageReader;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class BaiduTongjiMain {
    public void baidu() {
        //baidu统计登陆代码
        try {
            WebClient client = new WebClient(BrowserVersion.CHROME);
            HttpWebConnection httpwebconnection = new HttpWebConnection(client);
            client.setWebConnection(httpwebconnection);
            HtmlPage page = client.getPage("http://tongji.baidu.com");
            HtmlElement username = (HtmlElement) page.getElementById("UserName");
            HtmlElement password = (HtmlElement) page.getElementById("Password");
            HtmlElement valiCode = (HtmlElement) page.getElementById("Valicode");
            HtmlImage valiCodeImg = (HtmlImage) page.getElementById("cas_code");
            ImageReader imageReader = valiCodeImg.getImageReader();
            BufferedImage bufferedImage = imageReader.read(0);

            JFrame f2 = new JFrame();
            JLabel l = new JLabel();
            l.setIcon(new ImageIcon(bufferedImage));
            f2.getContentPane().add(l);
            f2.setSize(100, 100);
            f2.setTitle("验证码");
            f2.setVisible(true);

            String valicodeStr = JOptionPane.showInputDialog("请输入验证码：");
            f2.setVisible(false);
            HtmlElement submit = (HtmlElement) page.getElementById("Submit");
            HtmlSubmitInput submit2 = (HtmlSubmitInput) submit;
            username.click();
            username.type("gabazi");
            password.click();
            password.type("******");
            valiCode.click();
            valiCode.type(valicodeStr);

            HtmlPage resultPage = submit2.click();
            System.out.println(resultPage.asText());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

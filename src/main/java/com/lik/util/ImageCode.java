package com.lik.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
/**
 * @author Bill 
 * @since 2011
 */
public class ImageCode {

    private static char mapTable[]={
      'a','b','c','d','e','f',
      'g','h','i','j','k','L',
      'm','n','o','p','q','r',
      's','t','u','v','w','x',
      'y','z'};
   
    
    /**
     * 
     * @param width
     * @param height
     * @param os
     * @return
     */
	public static String getImageCode(int width, int height, OutputStream os) {
	  if(width<=0)width=60;
	  if(height<=0)height=20; 
	  BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB); 
	  // ��ȡͼ�������� 
	  Graphics g = image.getGraphics();
	  //���������
	  Random random = new Random();
	  // �趨����ɫ 
	  g.setColor(getRandColor(200,250)); 
	  g.fillRect(0, 0, width, height); 
	  //�趨����
	  g.setFont(new Font("Times New Roman",Font.PLAIN,18));
	  //���߿� 
	  //g.setColor(Color.black); 
	  //g.drawRect(0,0,width-1,height-1); 
	  // �������168�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽
	  g.setColor(getRandColor(160,200));
	  for (int i=0;i<168;i++)
	  {
		  int x = random.nextInt(width);
		  int y = random.nextInt(height);
		  int xl = random.nextInt(12);
		  int yl = random.nextInt(12);
		  g.drawLine(x,y,x+xl,y+yl);
	  }
	  //ȡ�����������
	  String strEnsure = "";
	  //4����4λ��֤��,���Ҫ���ɸ���λ����֤��,��Ӵ���ֵ
	  for(int i=0; i<4; ++i) {
		  strEnsure += mapTable[(int)(mapTable.length*Math.random())];
		  // ����֤����ʾ��ͼ����
		  g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
		  //ֱ������
		  String str = strEnsure.substring(i,i+1);
		  g.drawString(str,13*i+6,16);
	  }  
	  
	  // �ͷ�ͼ��������
	  g.dispose();   
	  try {
	   // ���ͼ�� 
	   ImageIO.write(image, "JPEG", os);
	  } catch (IOException e) {
	   return "";
	  }  
	  return strEnsure;
	}
	
	//������Χ��������ɫ
	static Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
}

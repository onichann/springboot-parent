package com.wt.springboot.freemarker;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.ui.VerticalAlignment;
import sun.misc.BASE64Encoder;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is a JFreeCharDemo provide BarPic by now
 * @author JavaLuSir
 *
 */
public class JFreeChartD {

	/**
	 * empty construct
	 */
	public JFreeChartD(){}

	/**
	 * write a WriteBarPic
	 * return a Filepath
	 * @return filepath a Filepath
	 */
	public static String writeBarPic(){

		try {
		//创建主题样式
	   StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
	   //设置标题字体
	   standardChartTheme.setExtraLargeFont(new Font("宋体",Font.BOLD,20));
	   //设置图例的字体
	   standardChartTheme.setRegularFont(new Font("宋体",Font.BOLD,15));
	   //设置轴向的字体
	   standardChartTheme.setLargeFont(new Font("宋体",Font.BOLD,15));
	   //应用主题样式
	   ChartFactory.setChartTheme(standardChartTheme);
		Font font = new Font("宋体", Font.BOLD, 14);
		TextTitle title = new TextTitle("统计图", font);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(510, "异常数据", "北京");
		dataset.addValue(320, "异常数据", "上海");
		dataset.addValue(300, "异常数据", "广州");
		dataset.addValue(390, "异常数据", "深圳");
		JFreeChart chart = ChartFactory.createBarChart("",//标题
		                  "",
		                  "",		//副标题
		                  dataset,
		                  PlotOrientation.HORIZONTAL, // 生成图片方向
		                  true,		//设置示例
		                  true,		//设置注释
		                  true);	//设置url
		//设置标题
		chart.setTitle(title);
		//获得示例实例
		LegendTitle ltitle = chart.getLegend();
		//设置图例在图片中的位置(左中右)
		ltitle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		//设置图例在图片中的位置(上中下)
		ltitle.setVerticalAlignment(VerticalAlignment.CENTER);
		//设置图例在图片中的位置(上下左右)
		ltitle.setPosition(RectangleEdge.RIGHT);
		//去除示例边框
		ltitle.setFrame(BlockBorder.NONE);

		CategoryPlot plot = chart.getCategoryPlot();
		//设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		//设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.BLUE);
		//设置网格横线颜色
		plot.setRangeGridlinePaint(Color.BLUE);

		//显示每个柱的数值，并修改该数值的字体属性
		BarRenderer renderer = new BarRenderer();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setSeriesPaint(0, Color.BLUE);

		//默认的数字显示在柱子中，通过如下两句可调整数字的显示
		//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.BASELINE_RIGHT));
		renderer.setItemLabelAnchorOffset(30D);

		plot.setRenderer(renderer);
		//设置最大值与顶端距离
		ValueAxis ra = plot.getRangeAxis();
		ra.setUpperMargin(0.1);
			//生成图片
			String imgName = ServletUtilities.saveChartAsPNG(chart, 800, 300, null);
			String path = System.getProperty("java.io.tmpdir") + imgName;
			return path;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 *
	 * @param imgFilePath
	 * @return String Base64Encode String
	 */
	public static String getBase64ImageStr(String imgFilePath) {
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();

			BASE64Encoder encoder = new BASE64Encoder();
			JFreeChartD.deleteImg(imgFilePath);
			return encoder.encode(data);

		} catch (IOException e) {
			e.printStackTrace();

			return "";
		}

	}

	/**
	 * delete tmpimage by path
	 */
	public static void deleteImg(String imagePath){
		File file = new File(imagePath);
		file.delete();
	}

}
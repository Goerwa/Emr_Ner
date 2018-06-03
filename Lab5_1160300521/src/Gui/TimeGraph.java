package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.stream.FileImageInputStream;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.*; 
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.*; 
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.TextTitle;

public class TimeGraph { 
    DefaultCategoryDataset dataset = null;
    public TimeGraph(long[] time) throws Exception { 
      dataset = new DefaultCategoryDataset(); 
      dataset.addValue(time[0], "Stream", "Stream");
      dataset.addValue(time[1], "Reader", "Reader");
      dataset.addValue(time[2], "Buffer", "Buffer");
      dataset.addValue(time[3], "Scanner", "Scanner");;
      dataset.addValue(time[4], "java.nio.file.Files", "java.nio.file.Files");
      init();
    }
    public void init() throws Exception{ 
        CategoryDataset dataset = this.dataset; 
        JFreeChart chart = ChartFactory.createBarChart3D( 
                           "读入文件耗时比较", // 图表标题
                           "方式", // 目录轴的显示标签
                           "时间", // 数值轴的显示标签
                            dataset, // 数据集
                            PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                            true,  // 是否显示图例(对于简单的柱状图必须是 false)
                            false, // 是否生成工具
                            false  // 是否生成 URL 链接
                            ); 

        chart.setBackgroundPaint(Color.WHITE); 
        CategoryPlot plot = chart.getCategoryPlot(); 
        CategoryAxis domainAxis = plot.getDomainAxis(); 
        domainAxis.setAxisLineVisible(false); 
        NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
        numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
        plot.setDomainAxis(domainAxis); 
        BarRenderer3D renderer = new BarRenderer3D(); 
        renderer.setBaseOutlinePaint(Color.BLACK);
        // 显示每个柱的数值，并修改该数值的字体属性 
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator()); 
        // 设置每个地区所包含的平行柱的之间距离 
        renderer.setItemMargin(0.1); 
        // 设置柱的数值可见
        renderer.setBaseItemLabelsVisible(true);
        plot.setRenderer(renderer); 
        // 设置柱的透明度 
        plot.setForegroundAlpha(0.8f); 
       
        FileOutputStream fos_jpg = null; 
        try { 
            fos_jpg = new FileOutputStream("src/Gui/time.jpg"); 
            ChartUtilities.writeChartAsJPEG(fos_jpg, 1, chart, 800, 800); 
        } finally { 
            try { 
                fos_jpg.close(); 
            } catch (Exception e) {} 
        }
        System.out.println("写入成功");
        
        //ImageIO.write(image, "jpeg", new File("E://helloImage.jpeg"));
    } 

}
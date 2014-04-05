package com.yflab.util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import com.yflab.model.Temperature;

public class TemperatureDAO {
	private final static String tableName="temperature";

	public static Temperature GetLatestTemperature() throws SQLException {
		

		DbConnector db = new DbConnector();
		Statement statement=null;
		Connection connection=null;
		Temperature ret=new Temperature();
		
		try {
			connection=db.getConnection();
			statement = connection.createStatement();
			String sql = "select * from "+tableName+" ORDER BY id DESC limit 0,1";
			
			ResultSet rs;
			rs=statement.executeQuery(sql);
			rs.next();
			
			ret.setId(rs.getInt(1));
			ret.setValue(rs.getString(2));
			ret.setDate(rs.getString(3));
			ret.setArg0(rs.getString(4));
			ret.setArg1(rs.getString(5));
			
			
		} catch (SQLException e) {
			ret=null;
			e.printStackTrace();
		}
		
		finally
		{
			if (statement!=null) statement.close();
			if (connection!=null) connection.close();
		}
		return ret;
	}

	public static ArrayList<Temperature> getTemperatureList(int count) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Temperature> tempArr=new ArrayList<Temperature>();
		

		DbConnector db = new DbConnector();
		Statement statement=null;
		Connection connection=null;
		
		try
		{
			connection=db.getConnection();
			statement=connection.createStatement();
			String sql="select * from "+tableName+" ORDER BY id DESC limit 0,"+count;
			ResultSet rs;
			rs=statement.executeQuery(sql);
			
			rs.next();
			
			for (int i=0;i<count;i++)
			{
				Temperature temp=new Temperature();
			    temp.setId(rs.getInt(1));
				temp.setValue(rs.getString(2));
			    temp.setDate(rs.getString(3));
				temp.setArg0(rs.getString(4));
				temp.setArg1(rs.getString(5));
				tempArr.add(temp);
				if (!rs.next())
					break;
			}
		}
		
		catch(Exception e)
		{
			tempArr=null;
			e.printStackTrace();
		}
		
		finally
		{
			if (statement!=null) statement.close();
			if (connection!=null) connection.close();
		}
		
		return tempArr;
	}

	public static String generateTemperatureChart(int count, String path) throws SQLException, IOException {
		// TODO Auto-generated method stub
		ArrayList<Temperature> tempArr=getTemperatureList(count);
		if (tempArr==null)
		  return null;
		else
		{
			
			String nakedFileName=UUID.randomUUID().toString()+".png";
			String chartName=path+"\\"+nakedFileName;
			
			System.out.println(chartName);
		       
		
			
			StandardChartTheme mChartTheme = new StandardChartTheme("CN");  
	        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));  
	        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));  
	        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));  
	        ChartFactory.setChartTheme(mChartTheme);   
	        
			DefaultCategoryDataset mDataset = new DefaultCategoryDataset(); 
			
			double min=10000,max=-10000;
			for (int i=count-1;i>=0;i--)
			{
			 
			   Temperature t=tempArr.get(i);
			   mDataset.addValue(Double.parseDouble(t.getValue()), "Temperature", t.getDate());
			
			   if (Double.parseDouble(t.getValue())>max)
				   max=Double.parseDouble(t.getValue());
			   
			   if (Double.parseDouble(t.getValue())<min)
				   min=Double.parseDouble(t.getValue());
					   
			}
		
		   
		 JFreeChart jfreechart=ChartFactory.createLineChart(
					"Latest "+count+ " Temperature Data",
				    "",
					"Value(C)",
					mDataset,
					PlotOrientation.VERTICAL,
					true,
					false,
					false);
			CategoryPlot plot=(CategoryPlot) jfreechart.getPlot();
			
			 plot.setBackgroundPaint(Color.LIGHT_GRAY);  
		     plot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线  
		     plot.setOutlinePaint(Color.RED);//边界线  
			
		    plot.getRangeAxis().setLowerBound(min-3);
			plot.getRangeAxis().setUpperBound(max+3);
			
			plot.setBackgroundAlpha(0.5f);
			plot.setForegroundAlpha(0.5f);
			
			CategoryAxis axis = plot.getDomainAxis();
			axis.setMaximumCategoryLabelLines(30);
			axis.setMaximumCategoryLabelWidthRatio(2f); 
			axis.setCategoryLabelPositions(CategoryLabelPositions
					.createUpRotationLabelPositions(Math.PI / 6.0));
			axis.setLabelFont(new Font("宋体",Font.PLAIN,8));
			 
			//Generate Chart finished.
			
			//Save chart to file.
			saveAsFile(jfreechart,chartName, 720, 480); 
			//System.out.println("saveOK!");
			
			return nakedFileName;
		}
	}
	
  

private static void saveAsFile(JFreeChart chart, String outputPath, int weight,
		int height) throws IOException {
	  System.out.println(chart.toString()+" "+outputPath);
	  File outFile=new File(outputPath);    
	  if (!outFile.exists())
			outFile.createNewFile();
	
	  
      FileOutputStream out=new FileOutputStream(outFile);
      
      try {
		ChartUtilities.writeChartAsPNG(out, chart, weight, height);
		out.flush();
		out.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}        
      
}    
   


}

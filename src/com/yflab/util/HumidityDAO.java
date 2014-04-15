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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.yflab.model.Humidity;


import com.yflab.util.DbConnector;

public class HumidityDAO {
  private final static String tableName="humidity";
	private  ArrayList<Humidity> humidCache=new ArrayList<Humidity>();
	private  String humidCacheNakedName=null;

	public Humidity GetLatestHumidity() throws SQLException {
		

		DbConnector db = new DbConnector();
		Statement statement=null;
		Connection connection=null;
		Humidity ret=new Humidity();
		
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

	public  ArrayList<Humidity> getHumidityList(int count) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Humidity> humidArr=new ArrayList<Humidity>();
		

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
				Humidity temp=new Humidity();
			    temp.setId(rs.getInt(1));
				temp.setValue(rs.getString(2));
			    temp.setDate(rs.getString(3));
				temp.setArg0(rs.getString(4));
				temp.setArg1(rs.getString(5));
				humidArr.add(temp);
				if (!rs.next())
					break;
			}
		}
		
		catch(Exception e)
		{
			humidArr=null;
			e.printStackTrace();
		}
		
		finally
		{
			if (statement!=null) statement.close();
			if (connection!=null) connection.close();
		}
		
		return humidArr;
	}

	public String generateHumidityChart(int count, String path) throws SQLException, IOException {
		// TODO Auto-generated method stub
		ArrayList<Humidity> humidArr=getHumidityList(count);
		if (humidArr==null)
		  return null;
		else
		{
			
			if (!compareWithCache(humidArr))
			{
			String nakedFileName=UUID.randomUUID().toString()+".png";
			String chartName=path+"\\"+nakedFileName;
			
			
		       
		
			
			StandardChartTheme mChartTheme = new StandardChartTheme("CN");  
	        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));  
	        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));  
	        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));  
	        ChartFactory.setChartTheme(mChartTheme);   
	        
			DefaultCategoryDataset mDataset = new DefaultCategoryDataset(); 
			
			double min=10000,max=-10000;
			for (int i=humidArr.size()-1;i>=0;i--)
			{
			 
			   Humidity t=humidArr.get(i);
			   mDataset.addValue(Double.parseDouble(t.getValue()), "Humidity", t.getDate());
			
			   if (Double.parseDouble(t.getValue())>max)
				   max=Double.parseDouble(t.getValue());
			   
			   if (Double.parseDouble(t.getValue())<min)
				   min=Double.parseDouble(t.getValue());
					   
			}
		
		   
		 JFreeChart jfreechart=ChartFactory.createLineChart(
					"Latest "+count+ " Humidity Data",
				    "",
					"Value(%)",
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
			
			humidCacheNakedName=nakedFileName;
			humidCache.clear();
			for (int i=0;i<humidArr.size();i++)
				humidCache.add(humidArr.get(i));
			
			return nakedFileName;
		}
			else
				return humidCacheNakedName;
	}
	
	}

private  boolean compareWithCache(ArrayList<Humidity> humidArr) {
		// TODO Auto-generated method stub
	if (humidCache.size()!=humidArr.size())
		return false;
	
	boolean ret=true;
	for (int i=0;i<humidCache.size();i++)
		if (!humidCache.get(i).equals(humidArr.get(i)))
		{
			ret=false;
			break;
		}
	return ret;
}
	

private  void saveAsFile(JFreeChart chart, String outputPath, int weight,
		int height) throws IOException {
	  System.out.println("New file saved.");
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

public  void setLatestHumidity(int junction, int humidity) throws SQLException {
	// TODO Auto-generated method stub
	 DbConnector db = new DbConnector();
	 Statement statement=null;
	 Connection connection=null;
	 try
	 {
		 connection=db.getConnection();
		 statement=connection.createStatement();
		 String sql="select count(id) from "+tableName+";";
		 ResultSet rs=null;
		 rs=statement.executeQuery(sql);
		 rs.next();
		 
		 int id=rs.getInt(1)+1;
		 int value=humidity;
		 String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		 int arg0=junction;
		 
		 sql="insert into "+tableName+" VALUES("+id+","+value+",\""+date+"\","+arg0+",\"\");";
		 statement.execute(sql);
		
	 }
	 catch (Exception e)
	 {
		 e.printStackTrace();
		
		 
	 }
	 
	 finally
	 {
		 if (statement!=null) statement.close();
		 if (connection!=null) connection.close();
	 }
}    
}

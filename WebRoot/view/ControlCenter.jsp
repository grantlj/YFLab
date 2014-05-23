<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.yflab.model.Humidity" %>
<%@ page import="com.yflab.model.Temperature" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.yflab.model.Smog" %>
<%@ page import="com.yflab.model.Infrared" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <meta http-equiv="refresh" content=30;url=ControlCenter>
<style type="text/css">

body,table{
 font-size:12px;
}
table{
 table-layout:fixed;
 empty-cells:show; 
 border-collapse: collapse;
 margin:0 auto;
}
td{
 height:20px;
}
h1,h2,h3{
 font-size:12px;
 margin:0;
 padding:0;
}
 
.title { background: #FFF; border: 1px solid #9DB3C5; padding: 1px; width:90%;margin:20px auto; }
 .title h1 { line-height: 31px; text-align:center;  background: #2F589C url(th_bg2.gif); background-repeat: repeat-x; background-position: 0 0; color: #FFF; }
  .title th, .title td { border: 1px solid #CAD9EA; padding: 5px; }
 
//样式一

div#floatparts
{
  background-color:blue;
  positon:fixed;
  border: 1px dotted #000;
}


table.tab_css_1{
 border:1px solid #cad9ea;
 color:#666;
}
table.tab_css_1 th {
 background-image: url(th_bg1.gif);
 background-repeat::repeat-x;
 height:30px;
}
table.tab_css_1 td,table.tab_css_1 th{
 border:1px solid #cad9ea;
 padding:0 1em 0;
}
table.tab_css_1 tr.tr_css{
 background-color:#f5fafe;
}
 
//样式二
<!--
table.tab_css_2{
 border:1px solid #9db3c5;
 color:#666;
}
table.tab_css_2 th {
 background-image: url(th_bg2.gif);
 background-repeat::repeat-x;
 height:30px;
 color:#fff;
}
table.tab_css_2 td{
 border:1px dotted #cad9ea;
 padding:0 2px 0;
}
table.tab_css_2 th{
 border:1px solid #a7d1fd;
 padding:0 2px 0;
}
table.tab_css_2 tr.tr_css{
 background-color:#e8f3fd;
}
 
//样式三
table.tab_css_3{
 border:1px solid #fc58a6;
 color:#720337;
}
table.tab_css_3 th {
 background-image: url(th_bg3.gif);
 background-repeat::repeat-x;
 height:30px;
 color:#35031b;
}
table.tab_css_3 td{
 border:1px dashed #feb8d9;
 padding:0 1.5em 0;
}
table.tab_css_3 th{
 border:1px solid #b9f9dc;
 padding:0 2px 0;
}
table.tab_css_3 tr.tr_css{
 background-color:#fbd8e8;
}
 
.hover{
   background-color: #53AB38;
   color: #fff;
-->
}
 

</style>
<script type="text/javascript">
 function ApplyStyle(s){
  document.getElementById("mytab").className=s.innerText;
 }
 
 $(function(){
       addHover('mytab');
 });
 
    /**
  * 在鼠标悬停时突出显示行--jQuery处理表格  
  *
  * @tab table id
  */
 function addHover(tab){
    $('#'+tab+' tr').hover(
       function(){
          $(this).find('td').addClass('hover');
       },
    function(){
       $(this).find('td').removeClass('hover');
    }
    );
 }
</script>
<title>控制中心</title>
</head>

<%
   ArrayList<Temperature> tempArr=(ArrayList<Temperature>) request.getAttribute("tempArr");
   ArrayList<Humidity> humidArr=(ArrayList<Humidity>) request.getAttribute("humidArr");
   String tempChartStr=(String) request.getAttribute("tempChartStr");
   String humidChartStr=(String) request.getAttribute("humidChartStr");
   String smogState=(String) request.getAttribute("smogState");
   String infraredState=(String) request.getAttribute("infraredState");
   
   int lightState=Integer.parseInt((String) request.getAttribute("lightState"));
   
   int light1,light2;
   light1=lightState%2;
   lightState/=2;
   light2=lightState%2;
  // int lightState=(String) request.getAttribute("lightState")
%>

<body>
  <center>
    <h1>控制中心</h1>
    <hr>
    <h2>环境温度</h2>
    <img src="view/images/generated/<%=tempChartStr%>">
    <br>
    <br>
    <table width="90%" id="mytab"  border="1" class="tab_css_1">
      <tr>
        <th>序号</th><th>获取时间</th><th>值</th>
      </tr>
      
      <%
      for (int i=0;i<tempArr.size();i++)
        {
      %>
        <tr class="tr_css">
      
      <%
          out.println("<td>"+tempArr.get(i).getId()+"</td>"+"<td>"+tempArr.get(i).getDate()+"</td>"+"<td>"+tempArr.get(i).getValue()+"</td>");
       %>
     </tr>
     <%} %>
    </table>
    
    <hr>
    <h2>环境湿度</h2>
     <img src="view/images/generated/<%=humidChartStr%>">
     <br>
     <br>
     <table width="90%" id="mytab"  border="1" class="tab_css_1">
      <tr>
        <th>序号</th><th>获取时间</th><th>值</th>
      </tr>
      
      <%
      for (int i=0;i<humidArr.size();i++)
        {
      %>
        <tr class="tr_css">
      
      <%
          out.println("<td>"+humidArr.get(i).getId()+"</td>"+"<td>"+humidArr.get(i).getDate()+"</td>"+"<td>"+humidArr.get(i).getValue()+"</td>");
       %>
     </tr>
     <%} %>
    </table>
   
   <div id="floatparts">
     <hr>
     <%
       if (smogState.equals("1"))
         out.println("<h2 style=\"color:#FF0000\">烟雾浓度超标</h3>");
       else
         out.println("<h3>烟雾浓度正常</h3>");
       
        if (infraredState.equals("1"))
         out.println("<h2 style=\"color:#FF0000\">红外线检测到入侵</h3>");
       else
         out.println("<h3>红外线正常</h3>");  
      %>
     <hr>
     
     <h2>灯光情况</h2>
	   <form method="post" action="OnLightChange">
	   <table>
	     <tr>
            <td>
               <h3>灯光1</h3>
               <input type="radio" name="switch1" value="open" <%if (light1==1) out.print("checked=\'checked\'"); %>/>开<br />
               <input type="radio" name="switch1" value="close"  <%if (light1==0) out.print("checked=\'checked\'"); %>/>关<br />
			</td>
			<td>
               <h3>灯光2</h3>
	           <input type="radio" name="switch2" value="open" <%if (light1==1) out.print("checked=\'checked\'"); %>/>开<br />
               <input type="radio" name="switch2" value="close"  <%if (light1==0) out.print("checked=\'checked\'"); %>/>关<br />
			</td>
			
		 <tr>
		 <tr>
		     <td><input type="submit" value="设置灯光"/></td>
		 </tr>
	   </table>
	   </form>
	
  </div>
  
   <hr>
    
      <h2>遥控器</h2>
      <form method="post" action="OnKeyDown">
      <table>
        <tr>
          <td><input type="radio" name="keyboard" id="key1" value="1"/>key1</td><td><input type="radio" name="keyboard" id="key2" value="2"/>key2</td><td><input type="radio" name="keyboard" id="key3" value="3"/>key3</td>
        </tr>
        
         <tr>
            <td><input type="radio" name="keyboard" id="key4" value="4"/>key4</td><td><input type="radio" name="keyboard" id="key5" value="5"/>key5</td><td><input type="radio" name="keyboard" id="key6" value="6"/>key6</td>
        </tr>
        
         <tr>
            <td><input type="radio" name="keyboard" id="key7" value="7"/>key7</td><td><input type="radio" name="keyboard" id="key8" value="8"/>key8</td><td><input type="radio" name="keyboard" id="key9" value="9"/>key9</td>
        </tr>
        
         <tr>
            <td><input type="radio" name="keyboard" id="key10" value="10"/>key10</td><td><input type="radio" name="keyboard" id="key11" value="11"/>key11</td><td><input type="radio" name="keyboard" id="key12" value="12"/>key12</td>
        </tr>
      </table> 
         <tr>
            <td><input type="submit" value="调用按键"/></td>
         </tr>
      </form>
  </center>
</body>
</html>
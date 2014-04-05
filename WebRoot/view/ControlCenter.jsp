<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.yflab.model.Humidity" %>
<%@ page import="com.yflab.model.Temperature" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
<!--
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
}
 
-->
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
    
  </center>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link href="mycss.css" rel="stylesheet" type="text/css">
<script src="jquery-3.6.0.min.js"></script>

</head>
<body>
<input type="text" id="1stNo" /> <br><br>
<input type="text" id="2ndNo" /><br><br>
<input type="button" id="submit" value="submit" /> <br><br>
<input type="text" id="answer" />

<script>
$("document").ready(function(){

	$("#submit").on("click", function(){

        //adding two numbers without sending to servlet:
        //var str = parseFloat($("#1stNo").val());
        //var str2 = parseFloat($("#2ndNo").val());
        //var ans = str + str2
		//$("#answer").val(ans);


		//adding two numbers with servlet:
		var no1 = $("#1stNo").val();
	    var no2 = $("#2ndNo").val(); 
		$.ajax({
            url:'ResourceServlet?action=calc',
            data:{number: no1, number2: no2},
            type:'get',
            cache:false,
            success:function(data){
               alert(data);
               $("#answer").val(data); 
            },
            error:function(){
              alert('error');
            }
         
		});
		
	
}); 
});



</script>
</body>
</html>
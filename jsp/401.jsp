<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
   <title>404: Page Not Found</title>
   <meta http-equiv="Content-type" content="text/html; charset=ISO-8859-1" />
   <style type="text/css">
 		.wrapper {
 			width 970px;
 			border: 2px solid #EEE;
 			padding: 10px;
 			-moz-border-radius: 5px;
 			-webkit-border-radius: 5px;
 		}
 		.heading {
 			font: bold 20px "verdana";
 			color: #336;
 			padding: 5px;
 			width: auto;
 		}
 		p {
 			font-size: 12px;
 		}
   </style>
</head>
<body>
	<div class="wrapper">
		<div class="heading"><u>performanceappraisal.com: Page Not Found</u></div>
		<p>
			You don't have sufficient privileges to access this page <code> <%= request.getAttribute("javax.servlet.forward.request_uri") %> </code> you requested.
		</p>
	</div>
</body>
</html>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://org.openuss.web.components.flexlist" prefix="fl" %>
<HTML>
    <HEAD> 
    	<title>Flexlist</title>
		<link href="contents/flexlist.css" media="screen" rel="Stylesheet" type="text/css" />
		<script src="contents/prototype.js" type="text/javascript"></script>
		<script src="contents/effects.js" type="text/javascript"></script>
		<script src="contents/dragdrop.js" type="text/javascript"></script>
		<script src="contents/controls.js" type="text/javascript"></script>
    </HEAD>
    <body bgcolor="white">
	<div id="wrapper" style="width:400px; margin:20px;">
	<f:view>
		<fl:flexlist binding="#{TestBackingBean.flexlist}" >
		</fl:flexlist>
	</f:view>
	</div>
    </body>
</HTML>  

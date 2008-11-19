<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.openuss.org/openformula" prefix="open" %>


<html>
<head>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
</head>
<body>
  Editor
  <f:view>
    <h:form id="form">
    	<open:editor id="editor" value="#{editorBean.text}" width="600" height="400"/>
      	<h:commandButton id="save" action="#{editorBean.save}" value="Submit" />
    </h:form>
  <br/>
  <div>
  	<h:outputLabel for="olddata" value="Currently saved data:"/>
  	<f:verbatim><br/></f:verbatim>
  	<h:outputText id="olddata" value="#{editorBean.text}" escape="false"/>
  </div>
  </f:view> 
</body>
</html>

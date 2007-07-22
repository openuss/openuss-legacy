<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr" %>
 
<f:view>
 <tr:document title="InputText Demo">
        
	<tr:form>
	    <tr:inputText label="Your name" id="input1" value="insert text here" />
	    <tr:commandButton id="button1" text="press me" action="sendEvent" />
	</tr:form>
        
 </tr:document>
</f:view>


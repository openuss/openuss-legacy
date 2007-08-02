<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://org.openuss.web.component.genericflexlist" prefix="gfl" %>
<f:loadBundle basename="demo.bundle.Messages" var="Message"/>

<HTML>
    <head>
    	<title>Input Name Page</title>
    	<script src="contents/prototype.js" type="text/javascript"></script>
		<script src="contents/effects.js" type="text/javascript"></script>
		<script src="contents/dragdrop.js" type="text/javascript"></script>
		<script src="contents/controls.js" type="text/javascript"></script>
    </head>
    <body bgcolor="white">	
		<f:view>
			<gfl:genericflexlist title="Mein Titel" hideButtonTitle="Weniger..." showButtonTitle="Mehr...">
				<f:facet name="visible">
					<h:panelGroup>
						<h:inputTextarea />
						<h:commandButton />
					</h:panelGroup>
						
				</f:facet>
				
				<f:facet name="hidden">
					<h:outputText value="invisible facet output" />	
				</f:facet>
			</gfl:genericflexlist>
			<h:panelGroup>
						<h:outputText value="visible facet output" />
						<h:inputTextarea />
						
						<h:commandButton>Mein Button</h:commandButton>
					</h:panelGroup>
			
		</f:view>
    </body>
</HTML>  

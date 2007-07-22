<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr" %>

<f:view>

 <tr:document title="Autosubmit Demo">
   <tr:form>
       
       <tr:selectOneChoice id="university" 
                label="Universität:" 
                value="#{autoSubmitHandler.university}" 
                autoSubmit="true" >
           <f:selectItems value="#{autoSubmitHandler.universities}"/>
        </tr:selectOneChoice>
        
        <tr:selectOneChoice id="department" 
                label="Fachbereich:" 
                value="#{autoSubmitHandler.department}" 
                partialTriggers="university" 
                autoSubmit="true" >
           <f:selectItems value="#{autoSubmitHandler.departments}"/>
        </tr:selectOneChoice>
        
        <tr:selectOneChoice id="institution" 
                label="Lehrstuhl:" 
                value="#{autoSubmitHandler.institution}" 
                partialTriggers="department" 
                autoSubmit="true" >
           <f:selectItems value="#{autoSubmitHandler.institutions}"/>
        </tr:selectOneChoice>

        <h:outputLink value="../index.jsp">
        	<h:outputText value="Index" />
        </h:outputLink>
        
    </tr:form>
 </tr:document>
</f:view>
package org.openuss.security.acegi.ldap;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsImpl;
import org.openuss.security.Roles;

/**
 * Usage of DIGEST-MD5.
 * 
 */
public class DigestMd5Local {

	private static void authenticate(String password) throws NamingException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//        env.put(Context.PROVIDER_URL, "ldap://wwusv1.uni-muenster.de:389/dc=uni-muenster,dc=de");
        
        env.put(Context.PROVIDER_URL, "ldap://localhost:10389/dc=example,dc=com");
//        env.put(Context.PROVIDER_URL, "ldap://commws02.uni-muenster.de:389/dc=uni-muenster,dc=de");
//        env.put(Context.PROVIDER_URL, "ldap://commws03.uni-muenster.de:389/dc=uni-muenster,dc=de");
//        env.put(Context.PROVIDER_URL, "ldap://commws04.uni-muenster.de:389/dc=uni-muenster,dc=de");
//        env.put(Context.PROVIDER_URL, "ldap://commws05.uni-muenster.de:389/dc=uni-muenster,dc=de");
//        env.put(Context.PROVIDER_URL, "ldap://commws06.uni-muenster.de:389/dc=uni-muenster,dc=de");        
               
//      env.put(Context.PROVIDER_URL, "ldap://wi1.uni-muenster.de:389/dc=uni-muenster,dc=de");
        
        env.put(Context.SECURITY_AUTHENTICATION, "SIMPLE");
//        env.put(Context.SECURITY_AUTHENTICATION, "SIMPLE");
        env.put(Context.SECURITY_PRINCIPAL, "uid=defaultuser,ou=myunit,dc=example,dc=com");


        /*
         * AcceptSecurityContext error, data 0: fehlerhafter Benutzername oder Passwort
         * AcceptSecurityContext error, data 525: fehlerhafter AuthentifikationsTyp (Simple statt DIGEST-MD5)
         */
//        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.SECURITY_CREDENTIALS, "masterkey");
                
        try {
        	
        	Hashtable envClone = (Hashtable) env.clone();
            if (envClone.containsKey(Context.SECURITY_CREDENTIALS)) {
                envClone.put(Context.SECURITY_CREDENTIALS, "******");
            }
            System.out.println("Creating InitialDirContext with environment " + envClone);
                        
        	
        	DirContext ctx = new InitialDirContext(env);
        	System.out.println("NameInNamespace: " + ctx.getNameInNamespace());
        	System.out.println(ctx.getAttributes("uid=defaultuser,ou=myunit").get("cn").toString());
        	System.out.println(ctx.getAttributes("uid=defaultuser,ou=myunit").get("givenName").toString());
        	System.out.println(ctx.getAttributes("uid=defaultuser,ou=myunit").get("sn").toString());
        	System.out.println(ctx.getAttributes("uid=defaultuser,ou=myunit").get("mail").toString());
            System.out.println();
                        
            ctx.close();
            
            
            
            System.out.println("\nSie haben sich korrekt authentifiziert!");

        }
        catch(AuthenticationException ex) {
        	System.out.println("Fehlerhafte Authentifizierung: "+ex);
        }        

    }
	
	public static void testSetUpProviderUrl(String inputUrl, Integer port, String rootDn) {
    	String url = inputUrl;
    	url = url.toLowerCase().replaceAll("\\s+","");
    	url = url + " ";  	
    	url = url.replaceAll("[^a-z]+\\s", "");
    	
    	rootDn = rootDn.toLowerCase();
    	rootDn = rootDn.replaceAll("\\s+","");
    	rootDn = rootDn.replaceAll("/+","");
    	url = url + ":"+port.toString()+"/"+rootDn;
    	
    	System.out.println(url);
	}
	

	private static void testExtendedLdapUserDetailsMapper(String password) throws NamingException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://wwusv1.uni-muenster.de:389/dc=uni-muenster,dc=de");
        env.put(Context.SECURITY_AUTHENTICATION, "DIGEST-MD5");
        env.put(Context.SECURITY_PRINCIPAL, "p_schu07");
        
        env.put(Context.SECURITY_CREDENTIALS, password);
                
        try {
        	
        	Hashtable envClone = (Hashtable) env.clone();
            if (envClone.containsKey(Context.SECURITY_CREDENTIALS)) {
                envClone.put(Context.SECURITY_CREDENTIALS, "******");
            }
            System.out.println("Creating InitialDirContext with environment " + envClone);
            System.out.println();
            
                	
        	DirContext ctx = new InitialDirContext(env);
        	String UserDn = ctx.getAttributes("cn=p_schu07,ou=projekt-benutzer",null).get("distinguishedName").toString();
        	Attributes attributes = ctx.getAttributes("cn=p_schu07,ou=projekt-benutzer",null);
        	
        	ExtendedLdapUserDetailsMapper mapper = new ExtendedLdapUserDetailsMapper();
        	//LdapUserDetailsMapper mapper = new LdapUserDetailsMapper();
        	mapper.setRoleAttributes(new String[]{"memberOf"});
        	mapper.setGroupRoleAttributeKey("ou");
        	mapper.setConvertToUpperCase(true);
        	
        	LdapUserDetailsImpl.Essence essence = (LdapUserDetailsImpl.Essence) mapper.mapAttributes(UserDn, attributes);
        	LdapUserDetails ldapUserDetails = essence.createUserDetails();
        	        	
//        	GrantedAuthority[] grantedAuthorities = ldapUserDetails.getAuthorities();
        	
//        	for (int i = 0; i < grantedAuthorities.length; i++) {
//				GrantedAuthority grantedAuthority = grantedAuthorities[i];
//				System.out.println(grantedAuthority.getAuthority());
//			}

        	
            // Set up some test values
            String rootDn1 = "dc=uni-muenster, DC=DE";
            String rootDn2 = "dc=openuss-university,dc=de";
            
            // Prepare test values for string comparison
    		rootDn1 = rootDn1.replaceAll("\\s+","");
    		rootDn2 = rootDn2.replaceAll("\\s+","");
    		rootDn1 = rootDn1.toLowerCase();
    		rootDn2 = rootDn2.toLowerCase();

        	String dn = ldapUserDetails.getDn();
        	
    		dn = dn.toLowerCase().replaceAll("\\s+","");
    		
    		Integer domainId = new Integer(0);
    		// Retrieve Domain
    		if (dn.contains(rootDn2.toLowerCase()))
    			System.out.println("OpenUSS");
    		else if (dn.contains(rootDn1.toLowerCase())) {
    				System.out.println("Uni Muenster");
    				domainId = 42;
    			 }
    		
    		ldapUserDetails.getAttributes().put("logonDomainId", domainId);
    		
    		essence = new LdapUserDetailsImpl.Essence(ldapUserDetails);    		
    		
    		
    		essence.addAuthority(new GrantedAuthorityImpl("ROLE_LDAPUSER"));
    		
    		ldapUserDetails = essence.createUserDetails();
    		
    		assignAttributes(ldapUserDetails);
    		System.out.println(ldapUserDetails.getAttributes().get("logonDomainId").toString());
    		System.out.println(ldapUserDetails.getAttributes().get("OPENUSS_TEST").toString());
    		
    		ldapUserDetails = assignDefaultRole(ldapUserDetails);
    		GrantedAuthority[] grantedAuthorities = ldapUserDetails.getAuthorities();
    		
        	for (int i = 0; i < grantedAuthorities.length; i++) {
				GrantedAuthority grantedAuthority = grantedAuthorities[i];
				System.out.println(grantedAuthority.getAuthority());
			}

        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
/*        	
            Attributes attrs = ctx.getAttributes("cn=p_schu07,ou=projekt-benutzer",null);

            // Set up some test values
            String rootDn1 = "dc=uni-muenster, DC=DE";
            String rootDn2 = "dc=openuss-university,dc=de";
            
            // Prepare test values for string comparison
    		rootDn1 = rootDn1.replaceAll("\\s+","");
    		rootDn2 = rootDn2.replaceAll("\\s+","");
    		rootDn1 = rootDn1.toLowerCase();
    		rootDn2 = rootDn2.toLowerCase();
    		
    		String groupRoleAttribute = "cn";
    		groupRoleAttribute.trim();
    		groupRoleAttribute = groupRoleAttribute.replaceAll("\\s+","");
    		groupRoleAttribute = groupRoleAttribute.toUpperCase();
    		
    		
    		// Retrieve information from DN
        	Attribute returnedAttribute = attrs.get("memberOf");
        	NamingEnumeration attributeValues = returnedAttribute.getAll();
        	while (attributeValues.hasMore()) {
        		String dn = ((Object) attributeValues.next()).toString();
//        		System.out.println(dn);
        		dn = dn.toLowerCase().replaceAll("\\s+","");
        		
        		// Retrieve Domain
        		if (dn.contains(rootDn2.toLowerCase()))
        			System.out.println("OpenUSS");
        		else if (dn.contains(rootDn1.toLowerCase()))
        			System.out.println("Uni Muenster");
        		
        		// Retrieve Role     		
        		String role = "GroupRoleAttribute not found within DN.";
        		dn = dn.toUpperCase();
        		int startindex = dn.indexOf(groupRoleAttribute);
        		int endindex = 0;
        		if (startindex > -1) {
        			startindex = startindex + groupRoleAttribute.length()+1;
        			endindex = dn.indexOf(",", startindex);
        		    role = "ROLE_"+ dn.substring(startindex,endindex);
        		}
//        		System.out.println(role);        	      		
        	} 	
*/          
            ctx.close();
            
            
            
            System.out.println("\nSie haben sich korrekt authentifiziert!");

        }
        catch(AuthenticationException ex) {
        	System.out.println("Fehlerhafte Authentifizierung: "+ex);
        }        

    }

	protected static LdapUserDetails assignDefaultRole(LdapUserDetails ldapUserDetails) {
		LdapUserDetailsImpl.Essence essence = new LdapUserDetailsImpl.Essence(ldapUserDetails);
		essence.addAuthority(new GrantedAuthorityImpl("ROLE_HERO"));
		return essence.createUserDetails();		
	}
	
	protected static void assignAttributes(LdapUserDetails ldapUserDetails) {
		ldapUserDetails.getAttributes().put("OPENUSS_TEST", 4711);
	}	
	
	public static void main (String[] args) throws Exception {

    	if (args.length < 1) {
            System.err.println(
                "Usage: java DigestMd5 <password>");
            System.exit(1);
        }
    	
    	authenticate(args[0]);
//    	testSetUpProviderUrl(" LDAP:// wwusv1.uni-muenster. DE://.:://///////// ", new Integer(389), "//////dc=uni-muenster, dc=de");
//    	testExtendedLdapUserDetailsMapper(args[0]);
	}	
}


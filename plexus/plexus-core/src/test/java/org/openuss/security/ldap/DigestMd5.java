package org.openuss.security.ldap;

/* Beispiele aus dem Buch "LDAP fuer Java-Entwickler"
 * Software & Support Verlag, 2004
 */
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Verwendung von DIGEST-MD5 -- (Listing 4_2).
 */
public class DigestMd5 {

    public static void main (String[] args) throws Exception {

    	if (args.length < 1) {
            System.err.println(
                "Usage: java DigestMd5 <password>");
            System.exit(1);
        }
    	
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//        env.put(Context.PROVIDER_URL, "ldap://wwusv1.uni-muenster.de:389/dc=uni-muenster,dc=de");
        
        env.put(Context.PROVIDER_URL, "ldap://wwusv1.uni-muenster.de:389/dc=uni-muenster,dc=de");
//        env.put(Context.PROVIDER_URL, "ldap://commws02.uni-muenster.de:389/dc=uni-muenster,dc=de");
//        env.put(Context.PROVIDER_URL, "ldap://commws03.uni-muenster.de:389/dc=uni-muenster,dc=de");
//        env.put(Context.PROVIDER_URL, "ldap://commws04.uni-muenster.de:389/dc=uni-muenster,dc=de");
//        env.put(Context.PROVIDER_URL, "ldap://commws05.uni-muenster.de:389/dc=uni-muenster,dc=de");
//        env.put(Context.PROVIDER_URL, "ldap://commws06.uni-muenster.de:389/dc=uni-muenster,dc=de");        
               
//      env.put(Context.PROVIDER_URL, "ldap://wi1.uni-muenster.de:389/dc=uni-muenster,dc=de");
        
        env.put(Context.SECURITY_AUTHENTICATION, "DIGEST-MD5");
//        env.put(Context.SECURITY_AUTHENTICATION, "SIMPLE");
        env.put(Context.SECURITY_PRINCIPAL, "p_schu07");
        

        /*
         * AcceptSecurityContext error, data 0: fehlerhafter Benutzername oder Passwort
         * AcceptSecurityContext error, data 525: fehlerhafter AuthentifikationsTyp (Simple statt DIGEST-MD5)
         */
        env.put(Context.SECURITY_CREDENTIALS, args[0]);
//        env.put(Context.SECURITY_CREDENTIALS, "");
                
        try {
        	
        	Hashtable envClone = (Hashtable) env.clone();
            if (envClone.containsKey(Context.SECURITY_CREDENTIALS)) {
                envClone.put(Context.SECURITY_CREDENTIALS, "******");
            }
            System.out.println("Creating InitialDirContext with environment " + envClone);
            
            
        	
        	DirContext ctx = new InitialDirContext(env);
            System.out.println(ctx.getAttributes("cn=p_schu07,ou=projekt-benutzer").toString());
            System.out.println();
            System.out.println(ctx.getAttributes("cn=p_schu07,ou=projekt-benutzer", new String[]{"mail"}).get("mail").toString().substring(6));
        	
//        	NamingEnumeration enumList = ctx.list("");
//        	int i=0;
//            while (i < 10 && enumList.hasMore()) {
//                System.out.println(enumList.next());
//                ++i;
//            }            
//            ctx.close();
            
            
            
            System.out.println("\nSie haben sich korrekt authentifiziert!");

        }
        catch(AuthenticationException ex) {
        	System.out.println("Fehlerhafte Authentifizierung: "+ex);
        }
        

            }
}
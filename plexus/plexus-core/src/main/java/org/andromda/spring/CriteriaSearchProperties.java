package org.andromda.spring;

/**
 * Stores the embedded values and asssociations of all entities in the system by type.  
 * Is used to determine the appropriate parameter name when an embedded value's property 
 * is referenced as the attribute to search by (as opposed to an association).
 * 
 * @author Chad Brandon
 */
 @SuppressWarnings({"unchecked"})
public class CriteriaSearchProperties
{
    private static final java.util.Map embeddedValuesByType = new java.util.HashMap();
    private static final java.util.Map navigableAssociationEndsByType = new java.util.HashMap();
    
    static
    {
        initialize1();
        initialize2();
        initialize3();
        initialize4();
        initialize5();
        initialize6();
        initialize7();
        initialize8();
        initialize9();
        initialize10();
        initialize11();
        initialize12();
        initialize13();
        initialize14();
        initialize15();
        initialize16();
        initialize17();
        initialize18();
        initialize19();
        initialize20();
        initialize21();
        initialize22();
        initialize23();
        initialize24();
        initialize25();
        initialize26();
        initialize27();
        initialize28();
        initialize29();
        initialize30();
        initialize31();
        initialize32();
        initialize33();
        initialize34();
        initialize35();
        initialize36();
        initialize37();
        initialize38();
        initialize39();
        initialize40();
        initialize41();
        initialize42();
        initialize43();
        initialize44();
        initialize45();
        initialize46();
        initialize47();
        initialize48();
        initialize49();
        initialize50();
        initialize51();
        initialize52();
        initialize53();
        initialize54();
        initialize55();
        initialize56();
        initialize57();
        initialize58();
        initialize59();
        initialize60();
        initialize61();
    }
    
    private static final void initialize1()
    {
        embeddedValuesByType.put(
            org.openuss.news.NewsItemImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.news.NewsItemImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize2()
    {
        embeddedValuesByType.put(
            org.openuss.documents.FolderImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.documents.FolderImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("entries", org.openuss.documents.FolderEntryImpl.class)
                }
            )
        );
    }
    
    private static final void initialize3()
    {
        embeddedValuesByType.put(
            org.openuss.documents.FolderEntryImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.documents.FolderEntryImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("parent", org.openuss.documents.FolderImpl.class)
                }
            )
        );
    }
    
    private static final void initialize4()
    {
        embeddedValuesByType.put(
            org.openuss.documents.FileEntryImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.documents.FileEntryImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("parent", org.openuss.documents.FolderImpl.class)
                }
            )
        );
    }
    
    private static final void initialize5()
    {
        embeddedValuesByType.put(
            org.openuss.viewtracking.DomainViewStateImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.viewtracking.DomainViewStateImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize6()
    {
        embeddedValuesByType.put(
            org.openuss.security.GroupImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.security.GroupImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("members", org.openuss.security.AuthorityImpl.class)
                }
            )
        );
    }
    
    private static final void initialize7()
    {
        embeddedValuesByType.put(
            org.openuss.security.UserImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.security.UserImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("groups", org.openuss.security.GroupImpl.class)
                }
            )
        );
    }
    
    private static final void initialize8()
    {
        embeddedValuesByType.put(
            org.openuss.security.acl.ObjectIdentityImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.security.acl.ObjectIdentityImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("parent", org.openuss.security.acl.ObjectIdentityImpl.class), 
                    new AssociationType("child", org.openuss.security.acl.ObjectIdentityImpl.class), 
                    new AssociationType("permissions", org.openuss.security.acl.PermissionImpl.class)
                }
            )
        );
    }
    
    private static final void initialize9()
    {
        embeddedValuesByType.put(
            org.openuss.security.acl.PermissionImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.security.acl.PermissionImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("aclObjectIdentity", org.openuss.security.acl.ObjectIdentityImpl.class), 
                    new AssociationType("recipient", org.openuss.security.AuthorityImpl.class)
                }
            )
        );
    }
    
    private static final void initialize10()
    {
        embeddedValuesByType.put(
            org.openuss.security.AuthorityImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.security.AuthorityImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("groups", org.openuss.security.GroupImpl.class)
                }
            )
        );
    }
    
    private static final void initialize11()
    {
        embeddedValuesByType.put(
            org.openuss.security.MembershipImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.security.MembershipImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("groups", org.openuss.security.GroupImpl.class), 
                    new AssociationType("members", org.openuss.security.UserImpl.class), 
                    new AssociationType("aspirants", org.openuss.security.UserImpl.class)
                }
            )
        );
    }
    
    private static final void initialize12()
    {
        embeddedValuesByType.put(
            org.openuss.security.ldap.RoleAttributeKeyImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.security.ldap.RoleAttributeKeyImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("attributeMappings", org.openuss.security.ldap.AttributeMappingImpl.class)
                }
            )
        );
    }
    
    private static final void initialize13()
    {
        embeddedValuesByType.put(
            org.openuss.security.ldap.AttributeMappingImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.security.ldap.AttributeMappingImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("roleAttributeKeys", org.openuss.security.ldap.RoleAttributeKeyImpl.class), 
                    new AssociationType("authenticationDomains", org.openuss.security.ldap.AuthenticationDomainImpl.class)
                }
            )
        );
    }
    
    private static final void initialize14()
    {
        embeddedValuesByType.put(
            org.openuss.security.ldap.AuthenticationDomainImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.security.ldap.AuthenticationDomainImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("ldapServers", org.openuss.security.ldap.LdapServerImpl.class), 
                    new AssociationType("attributeMapping", org.openuss.security.ldap.AttributeMappingImpl.class)
                }
            )
        );
    }
    
    private static final void initialize15()
    {
        embeddedValuesByType.put(
            org.openuss.security.ldap.LdapServerImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.security.ldap.LdapServerImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("userDnPatterns", org.openuss.security.ldap.UserDnPatternImpl.class), 
                    new AssociationType("authenticationDomain", org.openuss.security.ldap.AuthenticationDomainImpl.class)
                }
            )
        );
    }
    
    private static final void initialize16()
    {
        embeddedValuesByType.put(
            org.openuss.security.ldap.UserDnPatternImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.security.ldap.UserDnPatternImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("ldapServers", org.openuss.security.ldap.LdapServerImpl.class)
                }
            )
        );
    }
    
    private static final void initialize17()
    {
        embeddedValuesByType.put(
            org.openuss.commands.CommandImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.commands.CommandImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize18()
    {
        embeddedValuesByType.put(
            org.openuss.commands.LastProcessedCommandImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.commands.LastProcessedCommandImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("last", org.openuss.commands.CommandImpl.class)
                }
            )
        );
    }
    
    private static final void initialize19()
    {
        embeddedValuesByType.put(
            org.openuss.braincontest.AnswerImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.braincontest.AnswerImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize20()
    {
        embeddedValuesByType.put(
            org.openuss.braincontest.BrainContestImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.braincontest.BrainContestImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("answers", org.openuss.braincontest.AnswerImpl.class)
                }
            )
        );
    }
    
    private static final void initialize21()
    {
        embeddedValuesByType.put(
            org.openuss.repository.RepositoryFileImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.repository.RepositoryFileImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize22()
    {
        embeddedValuesByType.put(
            org.openuss.lecture.InstituteImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.lecture.InstituteImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("department", org.openuss.lecture.DepartmentImpl.class), 
                    new AssociationType("courseTypes", org.openuss.lecture.CourseTypeImpl.class), 
                    new AssociationType("applications", org.openuss.lecture.ApplicationImpl.class), 
                    new AssociationType("membership", org.openuss.security.MembershipImpl.class)
                }
            )
        );
    }
    
    private static final void initialize23()
    {
        embeddedValuesByType.put(
            org.openuss.lecture.CourseTypeImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.lecture.CourseTypeImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("courses", org.openuss.lecture.CourseImpl.class), 
                    new AssociationType("institute", org.openuss.lecture.InstituteImpl.class)
                }
            )
        );
    }
    
    private static final void initialize24()
    {
        embeddedValuesByType.put(
            org.openuss.lecture.PeriodImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.lecture.PeriodImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("courses", org.openuss.lecture.CourseImpl.class), 
                    new AssociationType("university", org.openuss.lecture.UniversityImpl.class)
                }
            )
        );
    }
    
    private static final void initialize25()
    {
        embeddedValuesByType.put(
            org.openuss.lecture.CourseImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.lecture.CourseImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("courseType", org.openuss.lecture.CourseTypeImpl.class), 
                    new AssociationType("period", org.openuss.lecture.PeriodImpl.class), 
                    new AssociationType("groups", org.openuss.security.GroupImpl.class)
                }
            )
        );
    }
    
    private static final void initialize26()
    {
        embeddedValuesByType.put(
            org.openuss.lecture.CourseMemberImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.lecture.CourseMemberImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize27()
    {
        embeddedValuesByType.put(
            org.openuss.lecture.OrganisationImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.lecture.OrganisationImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("membership", org.openuss.security.MembershipImpl.class)
                }
            )
        );
    }
    
    private static final void initialize28()
    {
        embeddedValuesByType.put(
            org.openuss.lecture.UniversityImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.lecture.UniversityImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("departments", org.openuss.lecture.DepartmentImpl.class), 
                    new AssociationType("periods", org.openuss.lecture.PeriodImpl.class), 
                    new AssociationType("membership", org.openuss.security.MembershipImpl.class)
                }
            )
        );
    }
    
    private static final void initialize29()
    {
        embeddedValuesByType.put(
            org.openuss.lecture.DepartmentImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.lecture.DepartmentImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("university", org.openuss.lecture.UniversityImpl.class), 
                    new AssociationType("institutes", org.openuss.lecture.InstituteImpl.class), 
                    new AssociationType("applications", org.openuss.lecture.ApplicationImpl.class), 
                    new AssociationType("membership", org.openuss.security.MembershipImpl.class)
                }
            )
        );
    }
    
    private static final void initialize30()
    {
        embeddedValuesByType.put(
            org.openuss.lecture.ApplicationImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.lecture.ApplicationImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("department", org.openuss.lecture.DepartmentImpl.class), 
                    new AssociationType("institute", org.openuss.lecture.InstituteImpl.class), 
                    new AssociationType("applyingUser", org.openuss.security.UserImpl.class), 
                    new AssociationType("confirmingUser", org.openuss.security.UserImpl.class)
                }
            )
        );
    }
    
    private static final void initialize31()
    {
        embeddedValuesByType.put(
            org.openuss.messaging.RecipientImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.messaging.RecipientImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("job", org.openuss.messaging.MessageJobImpl.class)
                }
            )
        );
    }
    
    private static final void initialize32()
    {
        embeddedValuesByType.put(
            org.openuss.messaging.MessageJobImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.messaging.MessageJobImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("recipients", org.openuss.messaging.RecipientImpl.class), 
                    new AssociationType("message", org.openuss.messaging.MessageImpl.class)
                }
            )
        );
    }
    
    private static final void initialize33()
    {
        embeddedValuesByType.put(
            org.openuss.messaging.TemplateParameterImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.messaging.TemplateParameterImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("template", org.openuss.messaging.TemplateMessageImpl.class)
                }
            )
        );
    }
    
    private static final void initialize34()
    {
        embeddedValuesByType.put(
            org.openuss.messaging.TextMessageImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.messaging.TextMessageImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize35()
    {
        embeddedValuesByType.put(
            org.openuss.messaging.MessageImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.messaging.MessageImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize36()
    {
        embeddedValuesByType.put(
            org.openuss.messaging.TemplateMessageImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.messaging.TemplateMessageImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("parameters", org.openuss.messaging.TemplateParameterImpl.class)
                }
            )
        );
    }
    
    private static final void initialize37()
    {
        embeddedValuesByType.put(
            org.openuss.system.SystemPropertyImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.system.SystemPropertyImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize38()
    {
        embeddedValuesByType.put(
            org.openuss.registration.UserActivationCodeImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.registration.UserActivationCodeImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("user", org.openuss.security.UserImpl.class)
                }
            )
        );
    }
    
    private static final void initialize39()
    {
        embeddedValuesByType.put(
            org.openuss.registration.ActivationCodeImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.registration.ActivationCodeImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize40()
    {
        embeddedValuesByType.put(
            org.openuss.registration.InstituteActivationCodeImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.registration.InstituteActivationCodeImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("institute", org.openuss.lecture.InstituteImpl.class)
                }
            )
        );
    }
    
    private static final void initialize41()
    {
        embeddedValuesByType.put(
            org.openuss.registration.UserDeleteCodeImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.registration.UserDeleteCodeImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("user", org.openuss.security.UserImpl.class)
                }
            )
        );
    }
    
    private static final void initialize42()
    {
        embeddedValuesByType.put(
            org.openuss.discussion.TopicImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.discussion.TopicImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("submitter", org.openuss.security.UserImpl.class), 
                    new AssociationType("posts", org.openuss.discussion.PostImpl.class), 
                    new AssociationType("first", org.openuss.discussion.PostImpl.class), 
                    new AssociationType("last", org.openuss.discussion.PostImpl.class), 
                    new AssociationType("forum", org.openuss.discussion.ForumImpl.class)
                }
            )
        );
    }
    
    private static final void initialize43()
    {
        embeddedValuesByType.put(
            org.openuss.discussion.DiscussionWatchImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.discussion.DiscussionWatchImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize44()
    {
        embeddedValuesByType.put(
            org.openuss.discussion.FormulaImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.discussion.FormulaImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize45()
    {
        embeddedValuesByType.put(
            org.openuss.discussion.PostImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.discussion.PostImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("submitter", org.openuss.security.UserImpl.class), 
                    new AssociationType("topic", org.openuss.discussion.TopicImpl.class), 
                    new AssociationType("formula", org.openuss.discussion.FormulaImpl.class), 
                    new AssociationType("editor", org.openuss.security.UserImpl.class)
                }
            )
        );
    }
    
    private static final void initialize46()
    {
        embeddedValuesByType.put(
            org.openuss.discussion.ForumWatchImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.discussion.ForumWatchImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize47()
    {
        embeddedValuesByType.put(
            org.openuss.discussion.ForumImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.discussion.ForumImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("topics", org.openuss.discussion.TopicImpl.class)
                }
            )
        );
    }
    
    private static final void initialize48()
    {
        embeddedValuesByType.put(
            org.openuss.newsletter.NewsletterImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.newsletter.NewsletterImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("subscribers", org.openuss.newsletter.SubscriberImpl.class), 
                    new AssociationType("mailings", org.openuss.newsletter.MailImpl.class)
                }
            )
        );
    }
    
    private static final void initialize49()
    {
        embeddedValuesByType.put(
            org.openuss.newsletter.SubscriberImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.newsletter.SubscriberImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize50()
    {
        embeddedValuesByType.put(
            org.openuss.newsletter.MailImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.newsletter.MailImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("newsletter", org.openuss.newsletter.NewsletterImpl.class)
                }
            )
        );
    }
    
    private static final void initialize51()
    {
        embeddedValuesByType.put(
            org.openuss.desktop.DesktopImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.desktop.DesktopImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("user", org.openuss.security.UserImpl.class), 
                    new AssociationType("institutes", org.openuss.lecture.InstituteImpl.class), 
                    new AssociationType("courses", org.openuss.lecture.CourseImpl.class), 
                    new AssociationType("courseTypes", org.openuss.lecture.CourseTypeImpl.class), 
                    new AssociationType("universities", org.openuss.lecture.UniversityImpl.class), 
                    new AssociationType("departments", org.openuss.lecture.DepartmentImpl.class)
                }
            )
        );
    }
    
    private static final void initialize52()
    {
        embeddedValuesByType.put(
            org.openuss.wiki.WikiSiteImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.wiki.WikiSiteImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("wikiSiteVersions", org.openuss.wiki.WikiSiteVersionImpl.class)
                }
            )
        );
    }
    
    private static final void initialize53()
    {
        embeddedValuesByType.put(
            org.openuss.wiki.WikiSiteVersionImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.wiki.WikiSiteVersionImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("wikiSite", org.openuss.wiki.WikiSiteImpl.class), 
                    new AssociationType("author", org.openuss.security.UserImpl.class)
                }
            )
        );
    }
    
    private static final void initialize54()
    {
        embeddedValuesByType.put(
            org.openuss.statistics.OnlineSessionImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.statistics.OnlineSessionImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("user", org.openuss.security.UserImpl.class)
                }
            )
        );
    }
    
    private static final void initialize55()
    {
        embeddedValuesByType.put(
            org.openuss.statistics.SystemStatisticImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.statistics.SystemStatisticImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize56()
    {
        embeddedValuesByType.put(
            org.openuss.chat.ChatRoomImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.chat.ChatRoomImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("messages", org.openuss.chat.ChatMessageImpl.class), 
                    new AssociationType("chatUsers", org.openuss.chat.ChatUserImpl.class), 
                    new AssociationType("owner", org.openuss.chat.ChatUserImpl.class)
                }
            )
        );
    }
    
    private static final void initialize57()
    {
        embeddedValuesByType.put(
            org.openuss.chat.ChatMessageImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.chat.ChatMessageImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("room", org.openuss.chat.ChatRoomImpl.class), 
                    new AssociationType("sender", org.openuss.chat.ChatUserImpl.class)
                }
            )
        );
    }
    
    private static final void initialize58()
    {
        embeddedValuesByType.put(
            org.openuss.chat.ChatUserImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.chat.ChatUserImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {

                }
            )
        );
    }
    
    private static final void initialize59()
    {
        embeddedValuesByType.put(
            org.openuss.collaboration.WorkspaceImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.collaboration.WorkspaceImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("user", org.openuss.security.UserImpl.class)
                }
            )
        );
    }
    
    private static final void initialize60()
    {
        embeddedValuesByType.put(
            org.openuss.paperSubmission.ExamImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.paperSubmission.ExamImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("papersubmissions", org.openuss.paperSubmission.PaperSubmissionImpl.class)
                }
            )
        );
    }
    
    private static final void initialize61()
    {
        embeddedValuesByType.put(
            org.openuss.paperSubmission.PaperSubmissionImpl.class,
            null);
        navigableAssociationEndsByType.put(
            org.openuss.paperSubmission.PaperSubmissionImpl.class,
            java.util.Arrays.asList(
                new AssociationType[] 
                {
                    new AssociationType("exam", org.openuss.paperSubmission.ExamImpl.class), 
                    new AssociationType("sender", org.openuss.security.UserImpl.class)
                }
            )
        );
    }
    
    /**
     * Attempts to get the embedded value list for the given type (or returns null
     * if one doesn't exist).
     * 
     * @param type the type of which to retrieve the value.
     * @return the collection of embedded value names.
     */
    public static java.util.Collection getEmbeddedValues(final Class type)
    {
        return (java.util.Collection)embeddedValuesByType.get(type);
    }
    
    /**
     * Gets the type of the navigable association end given the <code>ownerType</code>
     * and <code>name</code>
     *
     * @param ownerType the owner of the association.
     * @param name the name of the association end to find.
     * @return the type of the association end.
     */
    public static Class getNavigableAssociationEndType(final Class ownerType, final String name)
    {
        final java.util.Collection ends = (java.util.Collection)navigableAssociationEndsByType.get(ownerType);
        final AssociationType type = (AssociationType)org.apache.commons.collections.CollectionUtils.find(
            ends,
            new org.apache.commons.collections.Predicate()
            {
                public boolean evaluate(final Object object)
                {
                    return ((AssociationType)object).name.equals(name);
                }
            });
        return type != null ? type.type : null;
    }

    /**
     * A private class storing the association name and type.
     */    
    protected static final class AssociationType
    {
        protected AssociationType(final String name, final Class type)
        {
            this.name = name;
            this.type = type;
        }
        protected String name;
        protected Class type;
    }
}
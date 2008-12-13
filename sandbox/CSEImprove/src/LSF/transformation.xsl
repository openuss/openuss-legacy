<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:lsf="http://cse.campussource.de/lsf/schema/LSFSynchronizationMessage"
    xmlns:cse="http://cse.campussource.de/lsf/schema/SynchronizationMessage"
    xmlns="http://cse.campussource.de/lsf/schema/SynchronizationMessage">
    <xsl:output method="xml" media-type="text/xml" indent="yes" encoding="UTF-8" omit-xml-declaration="no"/>
	<!-- Leave empty to disable output of debug information-->
    <xsl:variable name="DEBUG"></xsl:variable>
	<!-- Course related (NOT workgroup related) roles get this prefix, so that they are distinguishable later on -->
    <xsl:variable name="COURSE_ROLE_PREFIX" select="'course:'"/>
	<!-- Relation types -->
    <xsl:variable name="RELATION_TYPE_COURSE_TO_WORKGROUPS" select="'course_to_workgroups'"/>
    <xsl:variable name="RELATION_TYPE_COURSETYPE_TO_COURSES" select="'coursetype_to_coures'"/>
    <xsl:variable name="RELATION_TYPE_CATEGORY_TO_CATEGORIES" select="'category_to_categories'"/>
    <xsl:variable name="RELATION_TYPE_CATEGORY_TO_COURSES" select="'category_to_courses'"/>
	<!-- ################################################################################################################## -->
	<!-- [SYNCHRONIZATIONMESSAGE] -->
    <xsl:template match="/lsf:synchronizationMessage">
        <xsl:element name="synchronizationMessage">
            <xsl:apply-templates select="lsf:isSyncMessage"/>
            <xsl:apply-templates select="lsf:isDebug"/>
            <xsl:element name="semesterName">
                <xsl:value-of select="lsf:semester/lsf:data/lsf:shortName"/>
            </xsl:element>
            <xsl:element name="semesterLsfId">
                <xsl:value-of select="lsf:semester/lsf:data/lsf:lsfId"/>
            </xsl:element>
            <xsl:element name="rootCategoryId">
                <xsl:if test="lsf:vvz/@rootKategorieId">
                    <xsl:text>category:</xsl:text>
                    <xsl:value-of select="lsf:vvz/@rootKategorieId"/>
                </xsl:if>
            </xsl:element>
			<!-- Generate "accounts" -->
			<!-- [ACCOUNTS] -->
			<!-- Create unified "accounts" by copying original "accounts" && merging "accounts2" -->
            <xsl:if test="$DEBUG">
                <xsl:comment>##################################################################</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:comment>#   Generating "accounts" (merging "accounts" and "accounts2")   #</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:comment>##################################################################</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:text>&#10;</xsl:text>
            </xsl:if>
            <xsl:element name="accounts">
                <xsl:for-each select="/lsf:synchronizationMessage/child::*[self::lsf:accounts or self::lsf:accounts2]/lsf:account">
                    <xsl:element name="account">
                        <xsl:element name="clientId">
                            <xsl:text>account:</xsl:text>
                            <xsl:value-of select="lsf:lsfId"/>
                        </xsl:element>
                        <xsl:element name="globalRole">
                            <xsl:choose>
                                <xsl:when test="name(..) = 'accounts2'">
                                    <xsl:text>User</xsl:text>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:text>Tutor</xsl:text>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:element>
                        <xsl:for-each select="./*">
                            <xsl:apply-templates select="."/>
                            <xsl:text>&#10;</xsl:text>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
			<!-- Generate CourseTypes -->
            <xsl:if test="$DEBUG">
                <xsl:comment>##################################################################</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:comment>#                    Generating "courseType"                    #</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:comment>##################################################################</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:text>&#10;</xsl:text>
            </xsl:if>
            <xsl:element name="courseTypes">
                <xsl:for-each select="/lsf:synchronizationMessage/lsf:lectureTypes/*">
                    <xsl:element name="courseType">
                        <xsl:element name="clientId">
                            <xsl:text>coursetype:</xsl:text>
                            <xsl:value-of select="lsfId"/>
                        </xsl:element>
                        <xsl:for-each select="./*">
                            <xsl:apply-templates select="."/>
                            <xsl:text>&#10;</xsl:text>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
			<!-- Generate "courses" -->
            <xsl:if test="$DEBUG">
                <xsl:comment>##################################################################</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:comment>#                       Generating "courses"                    #</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:comment>##################################################################</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:text>&#10;</xsl:text>
            </xsl:if>
            <xsl:element name="courses">
                <xsl:for-each select="/lsf:synchronizationMessage/lsf:lectures/*">
                    <xsl:element name="course">
						<!-- Copy everything except "groups", "accounts", "categories"-elements (these will be transformed to relations) -->
                        <xsl:for-each select="*">
                            <xsl:if test="not(name() = 'groups') and not(name() = 'accounts') and not(name() = 'categories') and not(name() = 'lectureTypeId')">
                                <xsl:apply-templates select="."/>
                                <xsl:text>&#10;</xsl:text>
                            </xsl:if>
                        </xsl:for-each>
                        <!-- CourseTypeId -->
                        <xsl:element name="courseTypeId">
                            <xsl:value-of select="./lsf:lectureTypeId"/>
                        </xsl:element>
						<!-- Generate clientId -->
                        <xsl:element name="clientId">
                            <xsl:text>course:</xsl:text>
                            <xsl:value-of select="lsf:lsfId"/>
                        </xsl:element>
						<!-- Generate semesterId -->
                        <xsl:element name="semesterId">
                            <xsl:value-of select="/lsf:synchronizationMessage/lsf:semester/lsf:data/lsf:lsfId"/>
                        </xsl:element>
						<!-- Get start date from 'semester'-element -->
                        <xsl:element name="startDate">
                            <xsl:value-of select="/lsf:synchronizationMessage/lsf:semester/lsf:data/lsf:lectureStart"/>
                        </xsl:element>
						<!-- Get end date from 'semester'-element -->
                        <xsl:element name="endDate">
                            <xsl:value-of select="/lsf:synchronizationMessage/lsf:semester/lsf:data/lsf:lectureEnd"/>
                        </xsl:element>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
			<!-- Generate "workgroups" -->
            <xsl:if test="$DEBUG">
                <xsl:comment>##################################################################</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:comment>#                     Generating "workgroups"                    #</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:comment>##################################################################</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:text>&#10;</xsl:text>
            </xsl:if>
            <xsl:element name="workgroups">
                <xsl:for-each select="lsf:lectures/*">
                    <xsl:if test="$DEBUG">
                        <xsl:comment>### Processing groups from (
                            <xsl:value-of select="title"/>) ###
                        </xsl:comment>
                        <xsl:text>&#10;</xsl:text>
                    </xsl:if>
                    <xsl:for-each select="lsf:groups/lsf:group">
                        <xsl:sort select="lsf:lsfId" data-type="number"/>
                        <xsl:if test="position() = 1">
                            <xsl:call-template name="generate-workgroup">
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:element>
			<!-- Generate "categories" -->
            <xsl:if test="$DEBUG">
                <xsl:comment>##################################################################</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:comment>#                     Generating "categories"                    #</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:comment>##################################################################</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:text>&#10;</xsl:text>
            </xsl:if>
            <xsl:element name="categories">
                <xsl:for-each select="/lsf:synchronizationMessage/lsf:vvz/*">
                    <xsl:element name="category">
						<!-- Generate clientId -->
                        <xsl:element name="clientId">
                            <xsl:text>category:</xsl:text>
                            <xsl:value-of select="lsf:lsfId"/>
                        </xsl:element>
						<!-- Generate semesterId -->
                        <xsl:element name="semesterId">
                            <xsl:value-of select="/lsf:synchronizationMessage/lsf:semester/lsf:data/lsf:lsfId"/>
                        </xsl:element>
						<!-- Copy everything except "children" and 'sort' -->
                        <xsl:for-each select="*">
                            <xsl:if test="not(name() = 'children') and not(name() = 'sort')">
                                <xsl:apply-templates select="."/>
                                <xsl:text>&#10;</xsl:text>
                            </xsl:if>
                        </xsl:for-each>
						<!-- Check, if sort was set; if not, set it to MAX_INT -->
                        <xsl:choose>
                            <xsl:when test="normalize-space(lsf:sort)">
                                <xsl:apply-templates select="sort"/>
                                <xsl:text>&#10;</xsl:text>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:comment>### Setting 'sort' to MAX_INT ###</xsl:comment>
                                <xsl:text>&#10;</xsl:text>
                                <xsl:element name="sort">
									<!-- Set to MAX_INT -->
                                    <xsl:text>2147483647</xsl:text>
                                </xsl:element>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
			<!--  Generate relations -->
            <xsl:element name="relations">
				<!-- Generate lecture -> workgroup relations -->
                <xsl:if test="$DEBUG">
                    <xsl:comment>##################################################################</xsl:comment>
                    <xsl:text>&#10;</xsl:text>
                    <xsl:comment>#            Generating lecture -> workgroup relations           #</xsl:comment>
                    <xsl:text>&#10;</xsl:text>
                    <xsl:comment>##################################################################</xsl:comment>
                    <xsl:text>&#10;</xsl:text>
                    <xsl:text>&#10;</xsl:text>
                </xsl:if>
                <xsl:for-each select="lsf:lectures/*">
                    <xsl:if test="$DEBUG">
                        <xsl:comment>### Processing lecture (
                            <xsl:value-of select="lsf:title"/>) ###
                        </xsl:comment>
                        <xsl:text>&#10;</xsl:text>
                    </xsl:if>
                    <xsl:for-each select="lsf:groups/lsf:group">
                        <xsl:sort select="lsf:lsfId" data-type="number"/>
                        <xsl:if test="position() = 1">
                            <xsl:call-template name="generate-lectureToWorkgroupRelation">
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:for-each>
				<!-- Generate category -> lecture relations -->
                <xsl:if test="$DEBUG">
                    <xsl:comment>##################################################################</xsl:comment>
                    <xsl:text>&#10;</xsl:text>
                    <xsl:comment>#            Generating category -> lecture relations          #</xsl:comment>
                    <xsl:text>&#10;</xsl:text>
                    <xsl:comment>##################################################################</xsl:comment>
                    <xsl:text>&#10;</xsl:text>
                    <xsl:text>&#10;</xsl:text>
                </xsl:if>
                <xsl:for-each select="lsf:lectures/*">
                    <xsl:if test="$DEBUG">
                        <xsl:comment>### Processing lecture (
                            <xsl:value-of select="lsf:title"/>) ###
                        </xsl:comment>
                        <xsl:text>&#10;</xsl:text>
                    </xsl:if>
                    <xsl:for-each select="lsf:categories/*">
                        <xsl:variable name="clientParentId">
                            <xsl:text>category:</xsl:text>
                            <xsl:value-of select="lsf:lsfId"/>
                        </xsl:variable>
                        <xsl:variable name="clientChildId">
                            <xsl:text>course:</xsl:text>
                            <xsl:value-of select="../../lsf:lsfId"/>
                        </xsl:variable>
                        <xsl:call-template name="generate-relation">
                            <xsl:with-param name="type" select="$RELATION_TYPE_CATEGORY_TO_COURSES"/>
                            <xsl:with-param name="clientChildId" select="$clientChildId"/>
                            <xsl:with-param name="clientParentId" select="$clientParentId"/>
                            <xsl:with-param name="sort" select="lsf:sort"/>
                        </xsl:call-template>
                    </xsl:for-each>
                </xsl:for-each>
				<!-- Generate lectureType -> lectures relations -->
                <xsl:if test="$DEBUG">
                    <xsl:comment>##################################################################</xsl:comment>
                    <xsl:text>&#10;</xsl:text>
                    <xsl:comment>#            Generating lectureType -> lecture relations         #</xsl:comment>
                    <xsl:text>&#10;</xsl:text>
                    <xsl:comment>##################################################################</xsl:comment>
                    <xsl:text>&#10;</xsl:text>
                    <xsl:text>&#10;</xsl:text>
                </xsl:if>
                <xsl:for-each select="lectures/*">
                    <xsl:if test="$DEBUG">
                        <xsl:comment>### Processing lecture (
                            <xsl:value-of select="lsf:title"/>) ###
                        </xsl:comment>
                        <xsl:text>&#10;</xsl:text>
                    </xsl:if>
                    <xsl:variable name="clientChildId">
                        <xsl:text>course:</xsl:text>
                        <xsl:value-of select="lsf:lsfId"/>
                    </xsl:variable>
                    <xsl:variable name="clientParentId">
                        <xsl:text>coursetype:</xsl:text>
                        <xsl:value-of select="lsf:lectureTypeId"/>
                    </xsl:variable>
                    <xsl:call-template name="generate-relation">
                        <xsl:with-param name="type" select="$RELATION_TYPE_COURSETYPE_TO_COURSES"/>
                        <xsl:with-param name="clientChildId" select="$clientChildId"/>
                        <xsl:with-param name="clientParentId" select="$clientParentId"/>
                    </xsl:call-template>
                </xsl:for-each>
				<!-- Generate lectureType -> lectures relations -->
                <xsl:if test="$DEBUG">
                    <xsl:comment>##################################################################</xsl:comment>
                    <xsl:text>&#10;</xsl:text>
                    <xsl:comment>#            Generating Category -> category relations           #</xsl:comment>
                    <xsl:text>&#10;</xsl:text>
                    <xsl:comment>##################################################################</xsl:comment>
                    <xsl:text>&#10;</xsl:text>
                    <xsl:text>&#10;</xsl:text>
                </xsl:if>
                <xsl:for-each select="vvz/*">
                    <xsl:if test="$DEBUG">
                        <xsl:comment>### Processing children of category (
                            <xsl:value-of select="lsf:name"/>) ###
                        </xsl:comment>
                        <xsl:text>&#10;</xsl:text>
                    </xsl:if>
                    <xsl:for-each select="lsf:children/*">
                        <xsl:variable name="clientChildId">
                            <xsl:text>category:</xsl:text>
                            <xsl:value-of select="."/>
                        </xsl:variable>
                        <xsl:variable name="clientParentId">
                            <xsl:text>category:</xsl:text>
                            <xsl:value-of select="../../lsf:lsfId"/>
                        </xsl:variable>
                        <xsl:call-template name="generate-relation">
                            <xsl:with-param name="type" select="$RELATION_TYPE_CATEGORY_TO_CATEGORIES"/>
                            <xsl:with-param name="clientChildId" select="$clientChildId"/>
                            <xsl:with-param name="clientParentId" select="$clientParentId"/>
                        </xsl:call-template>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:element>
			<!-- Generate "roles" -->
            <xsl:if test="$DEBUG">
                <xsl:comment>##################################################################</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:comment>#                       Generating "roles"                       #</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:comment>##################################################################</xsl:comment>
                <xsl:text>&#10;</xsl:text>
                <xsl:text>&#10;</xsl:text>
            </xsl:if>
            <xsl:element name="roles">
                <xsl:for-each select="lsf:lectures/*">
                    <xsl:if test="$DEBUG">
                        <xsl:comment>### Processing roles defined in lecture (
                            <xsl:value-of select="lsf:title"/>) ###
                        </xsl:comment>
                        <xsl:text>&#10;</xsl:text>
                    </xsl:if>
					<!-- Generate default roles from "accounts"-tag of "lecture" 	-->
					<!-- (only if there are no groups)								-->
                    <xsl:if test="$DEBUG">
                        <xsl:comment>### --> lecture roles  ###</xsl:comment>
                        <xsl:text>&#10;</xsl:text>
                    </xsl:if>
                    <xsl:if test="count(lsf:groups/lsf:group) = 0">
                        <xsl:for-each select="lsf:accounts/lsf:account">
                            <xsl:call-template name="generate-role">
                                <xsl:with-param name="name" select="concat($COURSE_ROLE_PREFIX, lsf:roleName)"/>
                                <xsl:with-param name="clientObjectId" select="concat('course:',	../../lsf:lsfId)"/>
                                <xsl:with-param name="clientAccountId" select="concat('account:',	lsf:lsfId)"/>
                            </xsl:call-template>
                        </xsl:for-each>
                    </xsl:if>
					<!-- Generate roles from "groups"-tag of "lecture" -->
                    <xsl:if test="$DEBUG">
                        <xsl:comment>### --> group roles  ###</xsl:comment>
                        <xsl:text>&#10;</xsl:text>
                    </xsl:if>
                    <xsl:for-each select="lsf:groups/lsf:group">
                        <xsl:variable name="clientObjectId">
                            <xsl:text>workgroup:</xsl:text>
                            <xsl:value-of select="../../lsf:lsfId"/>
                            <xsl:text>:</xsl:text>
                            <xsl:value-of select="lsf:lsfId"/>
                        </xsl:variable>
                        <xsl:for-each select="lsf:accounts/lsf:account">
                            <xsl:variable name="clientAccountId">
                                <xsl:text>account:</xsl:text>
                                <xsl:value-of select="lsf:lsfId"/>
                            </xsl:variable>
                            <xsl:call-template name="generate-role">
                                <xsl:with-param name="name" select="roleName"/>
                                <xsl:with-param name="clientObjectId" select="$clientObjectId"/>
                                <xsl:with-param name="clientAccountId" select="$clientAccountId"/>
                            </xsl:call-template>
                        </xsl:for-each>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:element>
			<!-- Copy / adapt "coursesToDelete" -->
            <xsl:element name="coursesToDelete">
                <xsl:for-each select="lsf:lecturesToDelete/*">
                    <xsl:element name="lsf:lsfId">
                        <xsl:text>course:</xsl:text>
                        <xsl:value-of select="lsf:lsfId"/>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
			<!-- Copy / adapt "accountsToDelete" -->
            <xsl:element name="accountsToDelete">
                <xsl:for-each select="lsf:accountsToDelete/*">
                    <xsl:element name="lsfId">
                        <xsl:text>account:</xsl:text>
                        <xsl:value-of select="lsf:lsfId"/>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
			<!-- Copy / adapt "courseTypesToDelete" -->
            <xsl:element name="courseTypesToDelete">
                <xsl:for-each select="lsf:lectureTypesToDelete/*">
                    <xsl:element name="lsfId">
                        <xsl:text>coursetype:</xsl:text>
                        <xsl:value-of select="lsf:lsfId"/>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>
    </xsl:template>
	<!-- ################################################################################################################## -->
	<!-- [GENERATE WORKGROUP] -->
    <xsl:template name="generate-workgroup">
        <xsl:variable name="currentLsfId" select="lsfId"/>
        <xsl:element name="workgroup">
            <xsl:apply-templates select="lsf:lsfId|lsf:name|lsf:room"/>
			<!-- Generate clientId -->
            <xsl:element name="clientId">
                <xsl:text>workgroup:</xsl:text>
                <xsl:value-of select="../../lsf:lsfId"/>:
                <xsl:value-of select="lsf:lsfId"/>
            </xsl:element>
			<!-- Generate semesterId -->
            <xsl:element name="semesterId">
                <xsl:value-of select="/lsf:synchronizationMessage/lsf:semester/lsf:data/lsf:lsfId"/>
            </xsl:element>
			<!-- Get start date from 'semester'-element -->
            <xsl:element name="startDate">
                <xsl:value-of select="/lsf:synchronizationMessage/lsf:semester/lsf:data/lsf:lectureStart"/>
            </xsl:element>
			<!-- Get end date from 'semester'-element -->
            <xsl:element name="endDate">
                <xsl:value-of select="/lsf:synchronizationMessage/lsf:semester/lsf:data/lsf:lectureEnd"/>
            </xsl:element>
            <xsl:text>&#10;</xsl:text>
        </xsl:element>
        <xsl:for-each select="../child::group[number(child::lsfId) > number($currentLsfId)]">
            <xsl:sort select="lsf:lsfId" data-type="number"/>
            <xsl:if test="position() = 1">
                <xsl:call-template name="generate-workgroup">
                </xsl:call-template>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
	<!-- ################################################################################################################## -->
	<!-- [GENERATE LECTURETOWORKGROUPRELATION] -->
    <xsl:template name="generate-lectureToWorkgroupRelation">
        <xsl:variable name="workgroupLsfId" select="lsf:lsfId"/>
        <xsl:variable name="courseLsfId" select="../../lsf:lsfId"/>
        <xsl:variable name="clientChildId">
            <xsl:text>workgroup:</xsl:text>
            <xsl:value-of select="$courseLsfId"/>
            <xsl:text>:</xsl:text>
            <xsl:value-of select="$workgroupLsfId"/>
        </xsl:variable>
        <xsl:variable name="clientParentId">
            <xsl:text>course:</xsl:text>
            <xsl:value-of select="$courseLsfId"/>
        </xsl:variable>
        <xsl:element name="relation">
            <xsl:element name="clientId">
                <xsl:text>relation:</xsl:text>
                <xsl:value-of select="$clientParentId"/>
                <xsl:text>:</xsl:text>
                <xsl:value-of select="$clientChildId"/>
            </xsl:element>
            <xsl:element name="relationType">
                <xsl:value-of select="$RELATION_TYPE_COURSE_TO_WORKGROUPS"/>
            </xsl:element>
            <xsl:element name="clientParentId">
                <xsl:value-of select="$clientParentId"/>
            </xsl:element>
            <xsl:element name="clientChildId">
                <xsl:value-of select="$clientChildId"/>
            </xsl:element>
        </xsl:element>
        <xsl:for-each select="../child::lsf:group[number(child::lsf:lsfId) > number($workgroupLsfId)]">
            <xsl:sort select="lsfId" data-type="number"/>
            <xsl:if test="position() = 1">
                <xsl:call-template name="generate-lectureToWorkgroupRelation">
                </xsl:call-template>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
	<!-- ################################################################################################################## -->
    <xsl:template name="generate-relation">
        <xsl:param name="type"/>
        <xsl:param name="clientChildId"/>
        <xsl:param name="clientParentId"/>
        <xsl:param name="sort"/>
        <xsl:element name="relation">
            <xsl:element name="clientId">
                <xsl:text>relation:</xsl:text>
                <xsl:value-of select="$clientParentId"/>
                <xsl:text>:</xsl:text>
                <xsl:value-of select="$clientChildId"/>
            </xsl:element>
            <xsl:element name="relationType">
                <xsl:value-of select="$type"/>
            </xsl:element>
            <xsl:element name="clientParentId">
                <xsl:value-of select="$clientParentId"/>
            </xsl:element>
            <xsl:element name="clientChildId">
                <xsl:value-of select="$clientChildId"/>
            </xsl:element>
            <xsl:if test="$sort">
                <xsl:element name="childSort">
                    <xsl:value-of select="$sort"/>
                </xsl:element>
            </xsl:if>
        </xsl:element>
    </xsl:template>
	<!-- ################################################################################################################## -->
	<!-- [GENERATE ROLE] -->
    <xsl:template name="generate-role">
        <xsl:param name="clientAccountId"/>
        <xsl:param name="clientObjectId"/>
        <xsl:param name="name"/>
        <xsl:element name="role">
            <xsl:element name="clientId">
                <xsl:text>role:</xsl:text>
                <xsl:value-of select="$clientAccountId"/>
                <xsl:text>:</xsl:text>
                <xsl:value-of select="$clientObjectId"/>
            </xsl:element>
            <xsl:element name="name">
                <xsl:value-of select="$name"/>
            </xsl:element>
            <xsl:element name="clientAccountId">
                <xsl:value-of select="$clientAccountId"/>
            </xsl:element>
            <xsl:element name="clientObjectId">
                <xsl:value-of select="$clientObjectId"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>


    <xsl:template match="lsf:*">
        <xsl:element name="{local-name()}">
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>

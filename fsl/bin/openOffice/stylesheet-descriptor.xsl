<?xml version="1.0" encoding="UTF-8"?>
<!-- Version: 1.0 - Author: Edith Schewe - Datum: 24.09.2003-->
<xsl:stylesheet exclude-result-prefixes="draw fo office style table text" version="1.0"
    xmlns:draw="http://openoffice.org/2000/drawing" xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:office="http://openoffice.org/2000/office" xmlns:style="http://openoffice.org/2000/style"
    xmlns:table="http://openoffice.org/2000/table" xmlns:text="http://openoffice.org/2000/text" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!--     generiert TextStudy contents.xml -->
    <xsl:template match="//office:body">
        <textStudyDescriptor>
            <xsl:for-each select="*">
                <xsl:variable name="position">
                    <xsl:value-of select="position()"/>
                </xsl:variable>
                <xsl:if test="name(self::node())='text:h'">
                    <xsl:call-template name="descriptor">
                        <xsl:with-param name="inhalt">
                            <xsl:value-of select="self::node()"/>
                        </xsl:with-param>
                        <xsl:with-param name="position">
                            <xsl:value-of select="position()"/>
                        </xsl:with-param>
                        <xsl:with-param name="folder">
                            <xsl:if test="name(//office:body/*[$position+1])='text:h'">1 </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:for-each>
            <!-- erstellt fuer jede Fussnote ein viewElement-Element -->
            <xsl:if test="*[text:footnote]">
                <viewElement folder="true" id="e14101977" parentId="none" title="Fussnoten" type="folder"/>
            </xsl:if>
            <xsl:for-each select="*/text:footnote">
                <viewElement folder="false" parentId="e14101977" type="text">
                    <xsl:attribute name="htmlFileName">
                        <xsl:text>fussnote_</xsl:text>
                        <xsl:value-of select="text:footnote-citation"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    <xsl:attribute name="title">
                        <xsl:text>Fussnote </xsl:text>
                        <xsl:value-of select="text:footnote-citation"/>
                    </xsl:attribute>
                    <xsl:attribute name="id">efoot<xsl:value-of select="text:footnote-citation"/>
                    </xsl:attribute>
                </viewElement>
            </xsl:for-each>
        </textStudyDescriptor>
    </xsl:template>
    <xsl:template name="descriptor">
        <xsl:param name="inhalt"/>
        <xsl:param name="position"/>
        <xsl:param name="folder"/>
        <!--     generiert fuer jedes HTML-File und jeden Folder viewElements mit entsprechenden Attributen-->
        <viewElement>
            <xsl:if test="$folder=1">
                <xsl:attribute name="folder">
                    <xsl:text>true</xsl:text>
                </xsl:attribute>
                <xsl:attribute name="type">
                    <xsl:text>folder</xsl:text>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="not($folder=1)">
                <xsl:attribute name="folder">
                    <xsl:text>false</xsl:text>
                </xsl:attribute>
                <xsl:attribute name="type">
                    <xsl:text>text</xsl:text>
                </xsl:attribute>
                <xsl:attribute name="htmlFileName">
                    <xsl:text>text_</xsl:text>
                    <xsl:value-of select="$position"/>
                    <xsl:text>.html</xsl:text>
                </xsl:attribute>
            </xsl:if>
            <xsl:attribute name="title">
                <xsl:value-of select="$inhalt"/>
            </xsl:attribute>
            <xsl:attribute name="id">
                <xsl:text>e</xsl:text>
                <xsl:value-of select="$position"/>
            </xsl:attribute>
            <xsl:choose>
                <!-- ruft Template zur >Erstellung des Attributs parentId -->
                <xsl:when test="self::node()[@text:style-name='Heading 1'] or self::node()[@text:level='1']">
                    <xsl:attribute name="parentId">none</xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:if test="self::node()[@text:style-name]">
                        <xsl:variable name="ebene">
                            <xsl:value-of select="substring-after(@text:style-name, 'Heading ')"/>
                        </xsl:variable>
                        <xsl:variable name="sollebene">
                            <xsl:value-of select="concat('Heading ' , $ebene - 1)"/>
                        </xsl:variable>
                        <xsl:call-template name="parent_ID">
                            <xsl:with-param name="position">
                                <xsl:value-of select="$position - 1"/>
                            </xsl:with-param>
                            <xsl:with-param name="sollebene">
                                <xsl:value-of select="$sollebene"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="self::node()[@text:level]">
                        <xsl:variable name="ebene">
                            <xsl:value-of select="@text:level"/>
                        </xsl:variable>
                        <xsl:variable name="sollebene">
                            <xsl:value-of select="$ebene - 1"/>
                        </xsl:variable>
                        <xsl:call-template name="parent_ID">
                            <xsl:with-param name="position">
                                <xsl:value-of select="$position - 1"/>
                            </xsl:with-param>
                            <xsl:with-param name="sollebene">
                                <xsl:value-of select="$sollebene"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:otherwise>
            </xsl:choose>
            <!--erstellt fuer jede Fussnote des zugehörigen Kapitels ein Kindelement viewElementLink
-->
            <xsl:if test="not($folder=1)">
                <xsl:call-template name="element_Link">
                    <xsl:with-param name="position">
                        <xsl:value-of select="position()"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </viewElement>
        <!-- template name="descriptor" -->
    </xsl:template>
    <xsl:template name="element_Link">
        <xsl:param name="position"/>
        <xsl:if test="//office:body/*[not(name(self::node())='text:h') and position() = $position+1]">
            <xsl:for-each select="//office:body/*[position() = $position+1]/text:footnote">
                <viewElementLink>
                    <xsl:attribute name="id">
                        <xsl:text>l</xsl:text>
                        <xsl:value-of select="text:footnote-citation"/>
                    </xsl:attribute>
                    <!-- wird beim Einlesen des Dokuments durch die richtige ID ersetzt -->
                    <viewElementLinkTarget targetLearningUnitId="testA" targetViewManagerId="freestyleLearningGroup-Text-Study">
                        <xsl:attribute name="targetViewElementId">
                            <xsl:text>efoot</xsl:text>
                            <xsl:value-of select="text:footnote-citation"/>
                        </xsl:attribute>
                    </viewElementLinkTarget>
                </viewElementLink>
            </xsl:for-each>
            <xsl:call-template name="element_Link">
                <xsl:with-param name="position">
                    <xsl:value-of select="$position + 1"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <!-- template name="element_Link" -->
    </xsl:template>
    <xsl:template name="parent_ID">
        <xsl:param name="sollebene"/>
        <xsl:param name="position"/>
        <xsl:for-each select="//office:body/*[position() = $position]">
            <xsl:choose>
                <xsl:when test="self::node()[@text:style-name=$sollebene]">
                    <xsl:attribute name="parentId">
                        <xsl:value-of select="concat('e',$position)"/>
                    </xsl:attribute>
                </xsl:when>
                <xsl:when test="self::node()[@text:level=$sollebene]">
                    <xsl:attribute name="parentId">
                        <xsl:value-of select="concat('e',$position)"/>
                    </xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="parent_ID">
                        <xsl:with-param name="sollebene">
                            <xsl:value-of select="$sollebene"/>
                        </xsl:with-param>
                        <xsl:with-param name="position">
                            <xsl:value-of select="$position -1"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>
        <!-- template name="parent_ID" -->
    </xsl:template>
</xsl:stylesheet>

<?xml version="1.0" encoding="UTF-8"?>
<!-- Version: 1.0 - Author: Edith Schewe - Datum: 24.09.2003-->
<xsl:stylesheet exclude-result-prefixes="draw fo office style table text xlink" version="1.0"
    xmlns:draw="http://openoffice.org/2000/drawing" xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:office="http://openoffice.org/2000/office" xmlns:style="http://openoffice.org/2000/style"
    xmlns:table="http://openoffice.org/2000/table" xmlns:text="http://openoffice.org/2000/text"
    xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="//office:body">
        <htmlfiles>
            <!-- generiert für jede html-Datei jeweils ein Kindelement "file"-->
            <xsl:for-each select="*">
                <xsl:if test="name(self::node())='text:h'">
                    <xsl:variable name="position">
                        <xsl:value-of select="position()"/>
                    </xsl:variable>
                    <xsl:call-template name="file_Generierung">
                        <xsl:with-param name="inhalt">
                            <xsl:value-of select="self::node()"/>
                        </xsl:with-param>
                        <xsl:with-param name="position">
                            <xsl:value-of select="position()"/>
                        </xsl:with-param>
                        <xsl:with-param name="folder">
                            <xsl:if test="name(//office:body/*[$position+1])='text:h'">1</xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:for-each>
            <xsl:for-each select="*/text:footnote">
                <!-- generiert für jede Fussnote ein HTML-File -->
                <file>
                    <xsl:attribute name="htmlFileName">
                        <xsl:text>fussnote_</xsl:text>
                        <xsl:value-of select="text:footnote-citation"/>
                        <xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    <html>
                        <!-- <head><xsl:text/></head>-->
                        <body>
                            <p>
                                <xsl:text>[</xsl:text>
                                <xsl:value-of select="text:footnote-citation"/>
                                <xsl:text>] </xsl:text>
                                <xsl:value-of select="text:footnote-body/text:p/text()"/>
                                <xsl:for-each select="text:footnote-body/text:p/text:bibliography-mark">
                                    <xsl:if test="self::node()[@text:author]">
                                        <xsl:value-of select="@text:author"/>
                                    </xsl:if>
                                    <xsl:choose>
                                        <xsl:when test="self::node()[@text:booktitle]">
                                            <xsl:text> - </xsl:text>
                                            <xsl:value-of select="@text:booktitle"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:text> - </xsl:text>
                                            <xsl:value-of select="@text:title"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                    <xsl:if test="self::node()[@text:publisher]">
                                        <xsl:text> - </xsl:text>
                                        <xsl:value-of select="@text:publisher"/>
                                    </xsl:if>
                                    <xsl:if test="self::node()[@text:editor]">
                                        <xsl:text> - </xsl:text>
                                        <xsl:value-of select="@text:editor"/>
                                    </xsl:if>
                                    <xsl:if test="self::node()[@text:year]">
                                        <xsl:text> - </xsl:text>
                                        <xsl:value-of select="@text:year"/>
                                    </xsl:if>
                                    <xsl:if test="self::node()[@text:isbn]">
                                        <xsl:text> - </xsl:text>
                                        <xsl:value-of select="@text:isbn"/>
                                    </xsl:if>
                                    <xsl:if test="self::node()[@text:url]">
                                        <xsl:text> - </xsl:text>
                                        <xsl:text>&quot;</xsl:text>
                                        <xsl:value-of select="@text:url"/>
                                        <xsl:text>&quot;</xsl:text>
                                    </xsl:if>
                                </xsl:for-each>
                            </p>
                        </body>
                    </html>
                </file>
            </xsl:for-each>
        </htmlfiles>
    </xsl:template>
    <xsl:template name="file_Generierung">
        <xsl:param name="inhalt"/>
        <xsl:param name="position"/>
        <xsl:param name="folder"/>
        <xsl:if test="not($folder = 1)">
            <file>
                <xsl:attribute name="htmlFileName">
                    <xsl:text>text_</xsl:text>
                    <xsl:value-of select="$position"/>
                    <xsl:text>.html</xsl:text>
                </xsl:attribute>
                <xsl:comment>
                    <xsl:text>Titel: </xsl:text>
                    <xsl:value-of select="$inhalt"/>
                </xsl:comment>
                <!-- ab hier HTML-Gerüst -->
                <html>
                    <!-- <head><xsl:text/></head> FSL kann mit <head>Tag den HTML-Inhalt nicht anzeigen, wegen <META>Tag? -->
                    <body>
                        <xsl:call-template name="paragraph">
                            <xsl:with-param name="position">
                                <xsl:value-of select="$position+1"/>
                            </xsl:with-param>
                            <xsl:with-param name="inhalt">
                                <xsl:value-of select="$inhalt"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </body>
                </html>
                <!-- bis hier HTML-Gerüst -->
            </file>
        </xsl:if>
        <xsl:if test="$folder = 1">
            <xsl:comment>
                <xsl:text>Folder-Titel: </xsl:text>
                <xsl:value-of select="$inhalt"/>
            </xsl:comment>
        </xsl:if>
        <!-- template name="file_Generierung" -->
    </xsl:template>
    <xsl:template name="paragraph">
        <xsl:param name="inhalt"/>
        <xsl:param name="position"/>
        <xsl:for-each select="//office:body/*[not(name(self::node())='text:h') and position() = $position]">
            <!-- wählt Kindknoten von office:body, die keine Überschriften sind und entsprechende Position haben-->
            <xsl:choose>
                <!-- liest Bilder im Rahmen aus -->
                <xsl:when test="name(self::node()/draw:text-box/text:p/*)='draw:image'">
                    <xsl:variable name="bildname">
                        <xsl:value-of select="self::node()/draw:text-box/text:p/draw:image/@xlink:href"/>
                    </xsl:variable>
                    <img>
                        <xsl:attribute name="src">
                            <xsl:value-of select="substring-after($bildname, '/')"/>
                        </xsl:attribute>
                        <!-- <xsl:attribute name="width">
                            <xsl:text>100%</xsl:text>
                        </xsl:attribute>
                        <xsl:attribute name="height">
                            <xsl:text>100%</xsl:text>
                        </xsl:attribute> -->
                    </img>
                    <p>
                        <xsl:value-of select="self::node()"/>
                    </p>
                </xsl:when>
                <!-- liest Bilder aus -->
                <xsl:when test="name(self::node()/*)='draw:image'">
                    <xsl:variable name="bildname">
                        <xsl:value-of select="self::node()/draw:image/@xlink:href"/>
                    </xsl:variable>
                    <img>
                        <xsl:attribute name="src">
                            <xsl:value-of select="substring-after($bildname, '/')"/>
                        </xsl:attribute>
                        <!-- <xsl:attribute name="width">
                            <xsl:text>100%</xsl:text>
                        </xsl:attribute>
                        <xsl:attribute name="height">
                            <xsl:text>100%</xsl:text>
                        </xsl:attribute> -->
                    </img>
                    <p>
                        <xsl:value-of select="self::node()"/>
                    </p>
                </xsl:when>
                <xsl:when test="name(self::node())='table:table'">
                    <!-- liest Tabellen aus -->
                    <table border="1" bordercolor="#000000">
                        <xsl:for-each select="table:table-row">
                            <tr>
                                <xsl:for-each select="table:table-cell">
                                    <td>
                                        <xsl:value-of select="text:p"/>
                                        <!-- liest Bilder in Tabellen aus -->
                                        <xsl:if test="text:p[draw:image]">
                                            <xsl:variable name="bildname">
                                                <xsl:value-of select="text:p/draw:image/@xlink:href"/>
                                            </xsl:variable>
                                            <img>
                                                <xsl:attribute name="src">
                                                  <xsl:value-of select="substring-after($bildname, '/')"/>
                                                </xsl:attribute>
                                            </img>
                                        </xsl:if>
                                    </td>
                                </xsl:for-each>
                            </tr>
                        </xsl:for-each>
                    </table>
                </xsl:when>
                <xsl:when test="name(.)='text:unordered-list'">
                    <!-- liest nicht nummerierte Aufzählungen aus (max. 3fach verschachtelt) -->
                    <ul>
                        <xsl:for-each select="text:list-item">
                            <xsl:choose>
                                <xsl:when test="self::node()[text:unordered-list]">
                                    <li>
                                        <xsl:value-of select="text:p"/>
                                    </li>
                                    <ul>
                                        <xsl:for-each select="text:unordered-list/text:list-item">
                                            <xsl:choose>
                                                <xsl:when test="self::node()[text:unordered-list]">
                                                  <li>
                                                  <xsl:value-of select="text:p"/>
                                                  </li>
                                                  <ul>
                                                  <xsl:for-each select="text:unordered-list/text:list-item">
                                                  <xsl:choose>
                                                  <xsl:when test="self::node()[text:unordered-list]">
                                                  <li>
                                                  <xsl:value-of select="text:p"/>
                                                  </li>
                                                  <ul>
                                                  <xsl:for-each select="text:unordered-list/text:list-item">
                                                  <li>
                                                  <xsl:value-of select="text:p"/>
                                                  </li>
                                                  </xsl:for-each>
                                                  </ul>
                                                  </xsl:when>
                                                  <xsl:otherwise>
                                                  <li>
                                                  <xsl:value-of select="text:p"/>
                                                  </li>
                                                  </xsl:otherwise>
                                                  </xsl:choose>
                                                  </xsl:for-each>
                                                  </ul>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                  <li>
                                                  <xsl:value-of select="text:p"/>
                                                  </li>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </xsl:for-each>
                                    </ul>
                                </xsl:when>
                                <xsl:otherwise>
                                    <li>
                                        <xsl:value-of select="text:p"/>
                                    </li>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:for-each>
                    </ul>
                </xsl:when>
                <xsl:when test="name(.)='text:ordered-list'">
                    <!-- liest  nummerierte Aufzählungen aus (max. 3fach verschachtelt) -->
                    <ol>
                        <xsl:for-each select="text:list-item">
                            <xsl:choose>
                                <xsl:when test="self::node()[text:ordered-list]">
                                    <li>
                                        <xsl:value-of select="text:p"/>
                                    </li>
                                    <ol>
                                        <xsl:for-each select="text:ordered-list/text:list-item">
                                            <xsl:choose>
                                                <xsl:when test="self::node()[text:ordered-list]">
                                                  <li>
                                                  <xsl:value-of select="text:p"/>
                                                  </li>
                                                  <ol>
                                                  <xsl:for-each select="text:ordered-list/text:list-item">
                                                  <xsl:choose>
                                                  <xsl:when test="self::node()[text:ordered-list]">
                                                  <li>
                                                  <xsl:value-of select="text:p"/>
                                                  </li>
                                                  <ol>
                                                  <xsl:for-each select="text:ordered-list/text:list-item">
                                                  <li>
                                                  <xsl:value-of select="text:p"/>
                                                  </li>
                                                  </xsl:for-each>
                                                  </ol>
                                                  </xsl:when>
                                                  <xsl:otherwise>
                                                  <li>
                                                  <xsl:value-of select="text:p"/>
                                                  </li>
                                                  </xsl:otherwise>
                                                  </xsl:choose>
                                                  </xsl:for-each>
                                                  </ol>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                  <li>
                                                  <xsl:value-of select="text:p"/>
                                                  </li>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </xsl:for-each>
                                    </ol>
                                </xsl:when>
                                <xsl:otherwise>
                                    <li>
                                        <xsl:value-of select="text:p"/>
                                    </li>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:for-each>
                    </ol>
                </xsl:when>
                <!-- liest Fussnotennummern aus und setzt Links -->
                <xsl:when test="name(./*)='text:footnote'">
                    <p>
                        <xsl:value-of select="text()"/>
                        <xsl:text/>
                        <xsl:call-template name="fussnote">
                            <xsl:with-param name="position">1</xsl:with-param>
                        </xsl:call-template>
                    </p>
                </xsl:when>
                <!-- liest Bereich-Formatierungen: fett, unterstrichen und kursiv aus-->                
                <xsl:when test="self::node()[text:span]">
                    <p>
                        <xsl:value-of select="text()"/>
                        <xsl:variable name="puffer">
                            <xsl:value-of select="self::node()"/>
                        </xsl:variable>
                        <xsl:for-each select="text:span">
                            <xsl:call-template name="span">
                                <xsl:with-param name="span_name">
                                    <xsl:value-of select="@text:style-name"/>
                                </xsl:with-param>
                                <xsl:with-param name="position">
                                    <xsl:value-of select="$position"/>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:for-each>
                        <xsl:variable name="letzter_span">
                            <xsl:value-of select="text:span[last()]"/>
                        </xsl:variable>
                        <xsl:value-of select="substring-after($puffer, $letzter_span)"/>
                    </p>
                </xsl:when>
                <!-- liest Absatz-Formatierungen: fett, unterstrichen und kursiv aus-->                                
                <xsl:when test="starts-with(@text:style-name, 'P')">
                    <p>
                        <xsl:call-template name="style_name">
                            <xsl:with-param name="style_name">
                                <xsl:value-of select="@text:style-name"/>
                            </xsl:with-param>
                            <xsl:with-param name="position">
                                <xsl:value-of select="$position"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </p>
                </xsl:when>
                <xsl:when test="name(self::node())='text:p'">
                    <p>
                        <xsl:value-of select="self::node()"/>
                    </p>
                </xsl:when>
                <xsl:otherwise>
                    <p>
                        <xsl:value-of select="self::node()"/>
                    </p>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="not(name(following-sibling::*[$position])='text:h')">
                <!-- rekursiver Aufruf von paragraph-Template, um alle Knoten auszulesen, die zwischen zwei Überschriften auftauchen 
                   (wenn folgender Knoten keine Überschrift, dann auslesen) -->
                <xsl:call-template name="paragraph">
                    <xsl:with-param name="position">
                        <xsl:value-of select="$position+1"/>
                    </xsl:with-param>
                    <xsl:with-param name="inhalt">
                        <xsl:value-of select="$inhalt"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </xsl:for-each>
        <!-- template name="paragraph" -->
    </xsl:template>
    <xsl:template name="style_name">
        <xsl:param name="style_name"/>
        <xsl:param name="position"/>
        <xsl:for-each select="//office:automatic-styles/style:style[@style:name=$style_name]">
            <xsl:choose>
                <xsl:when test="style:properties/@style:text-underline='single' and style:properties/@fo:font-style='italic' and style:properties/@fo:font-weight='bold'">
                    <b>
                        <u>
                            <i>
                                <xsl:value-of select="//office:body/*[position()=$position and @text:style-name=$style_name]"/>
                            </i>
                        </u>
                    </b>
                </xsl:when>
                <xsl:when test="style:properties/@style:text-underline='single' and style:properties/@fo:font-style='italic'">
                    <u>
                        <i>
                            <xsl:value-of select="//office:body/*[position()=$position and @text:style-name=$style_name]"/>
                        </i>
                    </u>
                </xsl:when>
                <xsl:when test="style:properties/@style:text-underline='single' and style:properties/@fo:font-weight='bold'">
                    <u>
                        <b>
                            <xsl:value-of select="//office:body/*[position()=$position and @text:style-name=$style_name]"/>
                        </b>
                    </u>
                </xsl:when>
                <xsl:when test="style:properties/@fo:font-style='italic' and style:properties/@fo:font-weight='bold'">
                    <b>
                        <i>
                            <xsl:value-of select="//office:body/*[position()=$position and @text:style-name=$style_name]"/>
                        </i>
                    </b>
                </xsl:when>
                <xsl:when test="style:properties/@fo:font-weight='bold'">
                    <b>
                        <xsl:value-of select="//office:body/*[position()=$position and @text:style-name=$style_name]"/>
                    </b>
                </xsl:when>
                <xsl:when test="style:properties/@fo:font-style='italic'">
                    <i>
                        <xsl:value-of select="//office:body/*[position()=$position and @text:style-name=$style_name]"/>
                    </i>
                </xsl:when>
                <xsl:when test="style:properties/@style:text-underline='single'">
                    <u>
                        <xsl:value-of select="//office:body/*[position()=$position and @text:style-name=$style_name]"/>
                    </u>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="//office:body/*[position()=$position]"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>
        <!-- template name="style_name" -->
    </xsl:template>
    <xsl:template name="span">
        <xsl:param name="span_name"/>
        <xsl:param name="position"/>
        <xsl:for-each select="//office:automatic-styles/style:style[@style:name=$span_name]">
            <xsl:choose>
                <xsl:when test="style:properties/@style:text-underline='single' and style:properties/@fo:font-style='italic' and style:properties/@fo:font-weight='bold'">
                    <b>
                        <u>
                            <i>
                                <xsl:value-of select="//office:body/*[position()=$position]//text:span[normalize-space(@text:style-name)=$span_name]"/>
                            </i>
                        </u>
                    </b>
                </xsl:when>
                <xsl:when test="style:properties/@style:text-underline='single' and style:properties/@fo:font-style='italic'">
                    <u>
                        <i>
                            <xsl:value-of select="//office:body/*[position()=$position]//text:span[normalize-space(@text:style-name)=$span_name]"/>
                        </i>
                    </u>
                </xsl:when>
                <xsl:when test="style:properties/@style:text-underline='single' and style:properties/@fo:font-weight='bold'">
                    <u>
                        <b>
                            <xsl:value-of select="//office:body/*[position()=$position]//text:span[normalize-space(@text:style-name)=$span_name]"/>
                        </b>
                    </u>
                </xsl:when>
                <xsl:when test="style:properties/@fo:font-style='italic' and style:properties/@fo:font-weight='bold'">
                    <b>
                        <i>
                            <xsl:value-of select="//office:body/*[position()=$position]//text:span[normalize-space(@text:style-name)=$span_name]"/>
                        </i>
                    </b>
                </xsl:when>
                <xsl:when test="style:properties/@fo:font-weight='bold'">
                    <b>
                        <xsl:value-of select="//office:body/*[position()=$position]//text:span[normalize-space(@text:style-name)=$span_name]"/>
                    </b>
                </xsl:when>
                <xsl:when test="style:properties/@fo:font-style='italic'">
                    <i>
                        <xsl:value-of select="//office:body/*[position()=$position]//text:span[normalize-space(@text:style-name)=$span_name]"/>
                    </i>
                </xsl:when>
                <xsl:when test="style:properties/@style:text-underline='single'">
                    <u>
                        <xsl:value-of select="//office:body/*[position()=$position]//text:span[normalize-space(@text:style-name)=$span_name]"/>
                    </u>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="//office:body/*[position()=$position]//text:span"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>
        <!-- template name="span" -->
    </xsl:template>
    <xsl:template name="fussnote">
        <xsl:param name="position"/>
        <xsl:variable name="puffer">
            <xsl:value-of select="self::node()"/>
        </xsl:variable>
        <xsl:variable name="fussnr">
            <xsl:value-of select="text:footnote[position()=$position]/text:footnote-citation"/>
        </xsl:variable>
        <a>
            <xsl:attribute name="href">l<xsl:value-of select="$fussnr"/>
            </xsl:attribute>
            <xsl:text>[</xsl:text>
            <xsl:value-of select="$fussnr"/>
            <xsl:text>]</xsl:text>
        </a>
        <xsl:variable name="fussinhalt">
            <xsl:value-of select="text:footnote[position()=$position]/text:footnote-body/text:p"/>
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="text:footnote[position()=$position+1]">
                <xsl:variable name="puffer2">
                    <xsl:value-of select="substring-after($puffer, $fussinhalt)"/>
                </xsl:variable>
                <xsl:variable name="fussinhalt2">
                    <xsl:value-of select="text:footnote[position()=$position+1]/text:footnote-citation"/>
                </xsl:variable>
                <xsl:value-of select="substring-before($puffer2, $fussinhalt2)"/>
                <xsl:call-template name="fussnote">
                    <xsl:with-param name="position">
                        <xsl:value-of select="$position+1"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="fussinhalt3">
                    <xsl:value-of select="text:footnote[position()=$position]/text:footnote-body/text:p"/>
                </xsl:variable>
                <xsl:value-of select="substring-after($puffer, $fussinhalt3)"/>
            </xsl:otherwise>
        </xsl:choose>
        <!-- template name="fussnote" -->
    </xsl:template>
</xsl:stylesheet>

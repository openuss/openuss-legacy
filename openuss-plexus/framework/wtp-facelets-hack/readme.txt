Hack for Eclipse Web Tools Platform to support Facelets:

Facelets Classpath first searches for all META-INF directory entries within the classpath.
Then it checks all files within the founded META-INF directories if they match to "taglib.xml".
Finally if the name match it looks up ALL resources with the given name.

The problem is that Eclipse WTP doesn't provides the directory entries within the automatically build jar files.
So facelets doesn't find the folders and the containing  taglib files.

Just provide an additional jar file  that contains  an empty taglib.xml file.
The empty taglib.xml must have the same name as the needed original taglib file but a different namespace.

i.e.:
in news-ui.jar : /META-INF/news.taglib.xml

in wtp-hack.jar: /META-INF/news.taglib.xml

<?xml version="1.0"?>
<!DOCTYPE facelet-taglib PUBLIC "-//Sun Microsystems, Inc.//DTD Facelet Taglib 1.0//EN" "http://java.sun.com/dtd/facelet-taglib_1_0.dtd">
<facelet-taglib>
   <namespace>http://www.openuss.org/news/jsf/WTP-FACELET-HACK</namespace>
   <tag><tag-name>empty</tag-name><source>empty.xhtml</source></tag>
</facelet-taglib>

This jar must not packaged with eclipse and must have the directory entries respectively.
Finally the hack jar file should be deployed as "j2ee module dependency" by WTP and facelets will find all needed taglibs :-) .

This package should contain dummy files for needed taglibs with openuss-plexus.


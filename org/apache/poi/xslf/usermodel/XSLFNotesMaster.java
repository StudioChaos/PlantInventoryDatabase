/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */
package org.apache.poi.xslf.usermodel;

import static org.apache.poi.POIXMLTypeLoader.DEFAULT_XML_OPTIONS;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.POIXMLException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.sl.usermodel.MasterSheet;
import org.apache.poi.util.Beta;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.drawingml.x2006.main.CTColorMapping;
import org.openxmlformats.schemas.presentationml.x2006.main.CTNotesMaster;
import org.openxmlformats.schemas.presentationml.x2006.main.NotesMasterDocument;

/**
* Notes master object associated with this layout.
* <p>
*  Within a notes master slide are contained all elements
* that describe the objects and their corresponding formatting
* for within a presentation slide.
* </p>
* <p>
* Within a nodes master slide are two main elements.
* The cSld element specifies the common slide elements such as shapes and
* their attached text bodies. Then the notesStyles element specifies the
* formatting for the text within each of these shapes.
* </p>
 *
 * @author Yegor Kozlov
*/
@Beta
 public class XSLFNotesMaster extends XSLFSheet
     implements MasterSheet<XSLFShape,XSLFTextParagraph> {
	 private CTNotesMaster _slide;
     private XSLFTheme _theme;

    XSLFNotesMaster() {
        super();
        _slide = prototype();
    }

    /**
     * @since POI 3.14-Beta1
     */
    protected XSLFNotesMaster(PackagePart part) throws IOException, XmlException {
        super(part);
        NotesMasterDocument doc =
            NotesMasterDocument.Factory.parse(getPackagePart().getInputStream(), DEFAULT_XML_OPTIONS);
        _slide = doc.getNotesMaster();
        setCommonSlideData(_slide.getCSld());
    }

    /**
     * @deprecated in POI 3.14, scheduled for removal in POI 3.16
     */
    @Deprecated
    protected XSLFNotesMaster(PackagePart part, PackageRelationship rel) throws IOException, XmlException {
        this(part);
    }
    
    private static CTNotesMaster prototype() {
        InputStream is = XSLFNotesMaster.class.getResourceAsStream("notesMaster.xml");
        if (is == null) {
            throw new POIXMLException("Missing resource 'notesMaster.xml'");
        }        

        try {
            try {
                NotesMasterDocument doc = NotesMasterDocument.Factory.parse(is, DEFAULT_XML_OPTIONS);
                CTNotesMaster slide =  doc.getNotesMaster();
                return slide;
            } finally {
                is.close();
            }            
        } catch (Exception e) {
            throw new POIXMLException("Can't initialize NotesMaster", e);
        }
    }
    
    @Override
    public CTNotesMaster getXmlObject() {
       return _slide;
    }

    @Override
    protected String getRootElementName(){
        return "notesMaster";
    }

    @Override
    public MasterSheet<XSLFShape,XSLFTextParagraph> getMasterSheet() {
        return null;
    }
    
    @Override
    public XSLFTheme getTheme() {
        if (_theme == null) {
            for (POIXMLDocumentPart p : getRelations()) {
                if (p instanceof XSLFTheme) {
                    _theme = (XSLFTheme) p;
                    CTColorMapping cmap = _slide.getClrMap();
                    if (cmap != null) {
                        _theme.initColorMap(cmap);
                    }
                    break;
                }
            }
        }
        return _theme;
    }    
}
package com.example.lib.xml;

/*
 *
 * +--------------------------------------------------------------------------
 * |
 * | WARNING: REMOVING THIS COPYRIGHT HEADER IS EXPRESSLY FORBIDDEN
 * |
 * | RECIPE APPLICATION
 * | ========================================
 * | by ENCLAVE, INC.
 * | (c) 2012-2013 ENCLAVEIT.COM - All right reserved
 * | Website: http://www.enclaveit.com [^]
 * | Email : engineering@enclave.vn
 * | ========================================
 * |
 * | WARNING //--------------------------
 * |
 * | Selling the code for this program without prior written consent is expressly
 * |forbidden.
 * | This computer program is protected by copyright law.
 * | Unauthorized reproduction or distribution of this program, or any portion of
 * | if, may result in severe civil and criminal penalties and will be prosecuted
 * |to the maximum extent possible under the law.
 * +--------------------------------------------------------------------------
 */

import android.content.Context;

import com.example.lib.utils.Constant;
import com.example.lib.localstorage.entities.RecipeType;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by lorence on 27/November/2020.
 */
public class XmlParserUtils {

    private Context mContext;

    /*
     * This is constructor of AppModule. Use it constructor to pass argument Context
     * @param context
     */
    public XmlParserUtils(Context context) {
        mContext = context;
    }

    /*
     * @param resIds
     * @return a XmlPullParser that can be used to convert file recipetypes.xml into XmlPullParser.
     */
    public XmlPullParser getXmlPullParser(int resIds) {
        try {
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = mContext.getResources().openRawResource(resIds);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            return parser;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * @param xmlPullParser
     * @return List<RecipeType> from file recipetypes.xml
     */
    public ArrayList<RecipeType> processParsing(XmlPullParser xmlPullParser) {
        ArrayList<RecipeType> recipeTypes = new ArrayList<>();
        int eventType = 0;
        try {
            eventType = xmlPullParser.getEventType();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        RecipeType currentRecipeType = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName;
            if (eventType == XmlPullParser.START_TAG) {
                eltName = xmlPullParser.getName();
                if (Constant.Recipe.equals(eltName)) {
                    currentRecipeType = new RecipeType();
                    currentRecipeType.setId(Integer.parseInt(xmlPullParser.getAttributeValue(0)));
                    recipeTypes.add(currentRecipeType);
                }
                if (recipeTypes.size() != 0 && Constant.Name.equals(eltName)) {
                    try {
                        recipeTypes.get(recipeTypes.size()-1).setName(xmlPullParser.nextText());
                    } catch (IOException | XmlPullParserException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                eventType = xmlPullParser.next();
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
        }
        return recipeTypes;
    }

}
